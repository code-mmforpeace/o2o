$(function () {
    getList();
    function getList(e) {
        $.ajax(
            {
                url:"/award/awardlist",
                type:"get",
                dataType:"json",
                success:function (data) {
                    handleList(data.awardList);
                }
            }
        );
    }
    function handleList(data) {
        var html='';
        data.map(function (item, index) {
            var textOp = "下架";
            var contraryStatus = 0;
            if (item.enableStatus == 0) {
                textOp = "上架";
                contraryStatus = 1;
            } else {
                contraryStatus = 0;
            }
            html += '' + '<div class="row row-award">'
                + '<div class="col-40">'
                + item.awardName
                + '</div>'
                + '<div class="col-20">'
                + item.priority
                + '</div>'
                + '<div class="col-40">'
                + '<a href="#" class="edit" data-id="'
                + item.awardId
                + '" data-status="'
                + item.enableStatus
                + '"> 编辑 </a>'
                + '<a href="#" class="delete" data-id="'
                + item.awardId
                + '" data-status="'
                +  contraryStatus
                + '">'
                + textOp
                + '</a>'
                + '<a href="#" class="preview" data-id="'
                + item.awardId
                + '" data-status="'
                + item.enableStatus
                + '"> 预览 </a>'
                + '</div>'
                + '</div>';

        });
        $('.award-wrap').html(html);
    }
    $('.award-wrap')
        .on(
            'click',
            'a',
            function(e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    window.location.href = '/shopAdmin/createAward?awardId='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('delete')) {
                    changeItem(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                } else if (target.hasClass('preview')) {
                    window.location.href = '/frontend/awarddetail?awardId='
                        + e.currentTarget.dataset.id;
                }
            });
    function changeItem(awardId, enableStatus) {
        var award = {};
        award.awardId = awardId;
        award.enableStatus = enableStatus;
        $.confirm('确定么?', function() {
            $.ajax({
                url : '/award/modifyaward',
                type : 'POST',
                data : {
                    awardStr : JSON.stringify(award),
                    statusChange : true
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }
});