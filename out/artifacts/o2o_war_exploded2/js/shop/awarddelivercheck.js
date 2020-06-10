$(function() {
	var shopId = 1;
	var awardName = '';

	function getList() {
		var listUrl = '/shop/listuserawardmapsbyshop?pageIndex=1&pageSize=9999&shopId='
				+ shopId + '&awardName=' + awardName;
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var userAwardMapList = data.userAwardMapList;
				var tempHtml = '';
				userAwardMapList.map(function(item, index) {
					tempHtml += '' + '<div class="card" data-award-id="'
                        + item.userAwardId + '" data-point="'+item.point
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
                        +'<div class="card-footer">'
                        +'<p class="color-gray">'+new Date(item.createTime).Format("yyyy-MM-dd")+'领取</p>';
                    if(item.usedStatus != 0){
                        //若用户有积分，则显示领取按钮
                        tempHtml += '<span>未领取</span></div></div>'
                    }else {
                        tempHtml += '<span>已领取</span></div></div>'
                    }
                });
				$('.awarddeliver-wrap').html(tempHtml);
			}
		});
	}

    $('.awarddeliver-wrap').on('click','.card',function (e) {
        $.prompt('请输入兑换码', '兑换', function (value) {
            $.ajax({
                url:'/shop/changeawardbyshop',
                type:'POST',
                data:{
                    codeToChange:value,
                    awardId:e.currentTarget.dataset.awardId
                },
                dataType:'JSON',
                success:function(data) {
                    if (data.success) {
                        $.toast('兑换失败！');
                    } else {
                        $.toast('兑换成功！');
                        getList();
                    }
                }
            })
        });
    });

	$('#search').on('input', function(e) {
		awardName = e.target.value;
		$('.awarddeliver-wrap').empty();
		getList();
	});

	getList();
});