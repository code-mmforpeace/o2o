$(function () {
    getList();
    function getList(e) {
        $.ajax({
            url:"/shopadmin/getshopheadline",
            type:"get",
            dataType:"json",
            success:function (data) {
                    handleList(data.headLineList);
                    },
            false:function (data) {
                $.alert(data.errMsg);
            }
        });
    }
    function handleList(data) {
        var html = '';
        data.map(function (item, index) {
            html += '<div class="row row-shop"><div class="col-40">'+ item.lineName +'</div><div class="col-40">'+ headLineStatus(item.enableStatus) +'</div><div class="col-20">'+ goHeadLine(item.enableStatus, item.lineId) +'</div></div>';

        });
        $('.headline-wrap').html(html);
    }
    function headLineStatus(status) {
        if (status == 0) {
            return '审核中';
        } else if (status == -1) {
            return '店铺非法';
        } else {
            return '审核通过';
        }
    }
    function goHeadLine(status,id) {
        if (status != 0 && status != -1) {
            return '<a href="/shopAdmin/createHeadLine?lineId='+ id +'">进入</a>';
        } else {
            return '';
        }
    }
});