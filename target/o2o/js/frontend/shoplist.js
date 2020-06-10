$(function() {

	var loading = false;
	//
	var maxItems = 999;
	//一页允许返回的最大条数
	var pageSize = 5;
	//获取店铺列表的url
	var listUrl = '/frontend/shoplists';
	//获取店铺类别列表以及区域列表的URL
	var searchDivUrl = '/frontend/listshopspageinfo';
	//初始页码
	var pageNum = 1;
	//从地址栏尝试获取parentId
	var parentId = getQueryString('parentId');
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';
	var loginOrNot = null;

	//每次加载添加的多少条目
	var itemsPerLoad = 2;


	function getSearchDivData() {
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shopCategoryList = data.shopCategoryList;
								var html = '';
								html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
								shopCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-category-id='
													+ item.shopCategoryId
													+ '>'
													+ item.shopCategoryName
													+ '</a>';
										});
								$('#shoplist-search-div').html(html);
								var selectOptions = '<option value="">全部街道</option>';
								var areaList = data.areaList;
								areaList.map(function(item, index) {
									selectOptions += '<option value="'
											+ item.areaId + '">'
											+ item.areaName + '</option>';
								});
								$('#area-search').html(selectOptions);
							}
						});
	}
	//渲染出店铺类别以及区域信息
	getSearchDivData();

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
				+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				loginOrNot = data.user;
				var html = '';
				data.shopList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
							+ item.shopId + '">' + '<div class="card-header">'
							+ item.shopName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.shopImg + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.shopDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					// $.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').hide();
				}else {
                    $('.infinite-scroll-preloader').show();
                    pageNum += 1;
                    loading = false;
                    $.refreshScroller();
				}
				if(loginOrNot) {
					//点击我的显示侧栏
					$('#me').click(function () {
						$.openPanel('#panel-left-demo-yes');
					});
				}else {
					//点击我的显示侧栏
					$('#me').click(function () {
						$.openPanel('#panel-left-demo-no');
					});
				}
			}
		});
	}
	// 预先加载5条
	addItems(pageSize, pageNum);

    $.init();

	//下滑屏幕自动搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading) {
            return;
        }else {
            loading = true;
            addItems(pageSize, pageNum);
            //模拟1s的加载过程
            setTimeout(function () {
                //重置状态
                loading = false;
                //
                addItems(pageSize, pageNum);
            }, 1000);
        }
	});

	//点击对应店铺的时候进入店铺的详情页
	$('.shop-list').on('click', '.card', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/Page/ShopDetail?shopId=' + shopId;
	});

	//如果选择了店铺一级类别，重置页面，清空原先有的，按照新的类别再去查询
	$('#shoplist-search-div').on(
			'click',
			'.button',
			function(e) {
				if (parentId) {// 如果传递过来的是一个父类下的子类
					shopCategoryId = e.target.dataset.categoryId;
					//消除已选择的效果
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						shopCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					//由于查询条件发生了变化，清空店铺列表再进行查询
					$('.list-div').empty();
					//重置页码
					pageNum = 1;
					addItems(pageSize, pageNum);
				} else {// 如果传递过来的父类为空，则按照父类查询
					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						parentId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
					parentId = '';
				}

			});

	$('#login-out').click(function () {
		$.ajax({
			url : "/userController/loginout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/Page/MainPage';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});

	$('#search').on('input', function(e) {
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	// $('#me').click(function () {
	// 	$.openPanel('#panel-left-demo-yes');
	// });
});
