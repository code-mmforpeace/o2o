$(function () {
    var loading = false;
    var maxItems = 999;
    var pageSize = 10;
    //获取奖品列表的url
    var listUrl = '/frontend/getuserawardlist?pageIndex=1&pageSize=9999';
    //奖品兑换的url
    var pageNum = 1;
    //从地址栏中获取shopId
    //预先加载
    addItems(pageSize,pageNum);
    //
    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl;
        loading = true;
        $.getJSON(url, function(data) {
            if (data.success) {
                maxItems = data.count;
                var html = '';
                data.awardList.map(function(item, index) {
                    html += '' + '<div class="card" data-award-id="'
                        + item.awardId + '" data-point="'+item.point
                        + '">'+ '<div class="card-header">'
                        +item.awardName + '<span class="pull-right">消耗积分'+item.point+'</span>'
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
                        +'<div class="card-footer">';
                    if(item.usedStatus != 0){
                        //若用户有积分，则显示领取按钮
                        html += '<p class="color-gray">'+new Date(item.createTime).Format("yyyy-MM-dd")+'兑换</p><span>未使用</span></div></div>'
                    }else {
                        html += '<p class="color-gray">'+new Date(item.expireTime).Format("yyyy-MM-dd")+'使用</p><span>已使用</span></div></div>'
                    }
                });
                $('.list-div').append(html);
                if(data.count != undefined){
                    //若用户在该店铺有积分就显示积分数量
                    canProceed = true;
                    $('.title').text('已兑换'+data.count+'次');
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