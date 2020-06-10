$(function () {
    //获取此店铺下的商品列表URL
    var listUrl = '/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
    //商品下架的URL
    var statusUrl = '/shopadmin/updateproduct';

    function getList() {
    //从后台获取此店铺的商品列表
        $.getJSON(listUrl,function (data) {
            if(data.success){
                var productList = data.productList;
                var tempHtml = '';
                //遍历每条商品信息，拼接成一行显示
                //显示信息包括 商品名称，优先级，上/下架（含productId），编辑按钮（含productId）
                //预览（含productId）
                productList.map(function (item,index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if(item.enableStatus == 0){
                        textOp = "上架";
                        contraryStatus = 1;
                    }else {
                        contraryStatus = 0;
                    }
                    //拼接每件商品的行信息
                    tempHtml += ''+ '<div class="row row-product">'
                        + '<div class="col-33">'
                        +item.productName
                        +'</div>'
                        +'<div class="col-20">'
                        +item.point
                        +'</div>'
                        +'<div class="col-40">'
                        +'<a href="#" class="edit" data-id="'
                        + item.productId
                        +'"data-status="'
                        +item.enableStatus
                        +'">编辑</a>'
                        +'<a href="#" class="status" data-id="'
                        +item.productId
                        +'"data-status="'
                        +contraryStatus
                        +'">'
                        +textOp
                        +'</a>'
                        +'<a href="#" class="preview" data-id="'
                        +item.productId
                        +'"data-status="'
                        +item.enableStatus
                        +'">预览</a>'
                        +'<a href="#" class="delete" data-id="'
                        +item.productId
                        +'"data-status="'
                        +item.enableStatus
                        +'">删除</a>'
                        +'</div>'
                        +'</div>';
                });
                //
                $('.product-wrap').html(tempHtml);
            }
        });
    }
    $('.product-wrap')
        .on(
            'click',
            'a',
            function(e) {
                var target = $(e.currentTarget);


                if (target.hasClass('edit')) {
                    window.location.href = '/shopAdmin/productOperation?productId='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('status')) {
                    changeItemStatus(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                } else if (target.hasClass('preview')) {
                    window.location.href = '/Page/ProductDetail?productId='
                        + e.currentTarget.dataset.id;
                }else if(target.hasClass('delete')){
                    deleteProductById(e.currentTarget.dataset.id);
                }
            });

    function deleteProductById(id) {

    }

    function changeItemStatus(id, enableStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function() {
            $.ajax({
                url : statusUrl,
                type : 'POST',
                data : {
                    productStr : JSON.stringify(product),
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
    getList();

    $('#new').click(function() {
        window.location.href = '/shopAdmin/productOperation';
    });
});