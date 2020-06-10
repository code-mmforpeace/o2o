$(function () {
    var loading = false;
    var maxItems = 999;
    var pageSize = 10;
    //获取奖品列表的url
    var listUrl = '/frontend/listawardsbyshop';
    //奖品兑换的url
    var exchangeUrl='/frontend/adduserawardmap';
    var pageNum = 1;
    //从地址栏中获取shopId
    var shopId = getQueryString('shopId');
    var awardNmae= '';
    var canProceed = false;
    var totalPoint = 0;
    //预先加载
    addItems(pageSize,pageNum);
    //
    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&shopId=' + shopId + '&awardName=' + awardNmae;
        loading = true;
        $.getJSON(url, function(data) {
            if (data.success) {
                maxItems = data.count;
                var html = '';
                data.awardList.map(function(item, index) {
                    html += '' + '<div class="card" data-award-id="'
                        + item.awardId + '" data-point="'+item.point
                        + '">'+ '<div class="card-header">'
                        +item.awardName + '<span class="pull-right">需要积分'+item.point+'</span>'
                        +'</div>'
                        +'<div class="card-content">'
                        +'<div class="list-block media-list">'+'<ul>'
                        +'<li class="item-content">'
                        +'<div class="item-media">' + '<img src="'
                        +item.awardImg
                        +'" width="44">'
                        +'</div>'
                        +'<div class="item-inner">' +'<div class="item-subtitle">'+item.awardDesc
                        +'</div>'
                        +'</div>'
                        +'</li>'
                        +'</ul>'
                        +'</div>'
                        +'</div>'
                        +'<div class="card-footer">'
                        +'<p class="color-gray">'+new Date(item.lastEditTime).Format("yyyy-MM-dd")+'更新</p>';
                        if(data.totalPoint != undefined){
                            //若用户有积分，则显示领取按钮
                            html += '<span>点击领取</span></div></div>'
                        }else {
                            html += '</div></div>'
                        }
                });
                $('.award-list').append(html);
                if(data.totalPoint != undefined){
                    //若用户在该店铺有积分就显示积分数量
                    canProceed = true;
                    $('.title').text('当前积分'+data.totalPoint);
                    totalPoint = data.totalPoint;
                }
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
            }else {
                $.confirm(data.errMsg,function () {
                    window.location.href ="/user/loginByUser";
                })
            }
        });
    }
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
    //点击对应的奖品进行兑换
    $('.award-list').on('click','.card',function (e) {
        //用户点击的时候需要当前积分大于所需的积分
        if(canProceed && (totalPoint > e.currentTarget.dataset.point)){
            //弹出对话框
            $.confirm('需要消耗'+e.currentTarget.dataset.point+'积分，确定吗？',function () {
                //访问后台，兑换奖品
                $.ajax({
                    url:exchangeUrl,
                    type:'POST',
                    data:{
                        awardId:e.currentTarget.dataset.awardId
                    },
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success) {
                            $.toast('操作失败！');
                        }else {
                            $.toast('操作成功！');
                            totalPoint = totalPoint - e.currentTarget.dataset.point;
                            $('.title').text('当前积分' + totalPoint);
                        }
                    }
                })
            })
        }else {
            $.toast('积分不足或无权限操作！');
        }
    });
    $('#search').on('change',function (e) {
        awardNmae = e.target.value;
        $('.award-list').empty();
        pageNum = 1;
        addItems(pageSize,pageNum);
    });
    $('#me').click(function () {
        $.openPanel('#panel-left-demo')
    })
});