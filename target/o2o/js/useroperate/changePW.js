$(function() {
    var url = '/userController/changePW';
    $('#submit').click(function() {
        var userName = $('#username').val();
        var password = $('#oldpsw').val();
        var newPassword = $('#newpsw').val();
        var newDPassword = $('#newDpsw').val();
        var formData = new FormData();
        formData.append('username', userName);
        formData.append('oldpsw', password);
        formData.append('newpsw', newPassword);
        formData.append('newDpsw', newDPassword);
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url : url,
            type : 'POST',
            data : formData,
            async : false,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.confirm("修改成功，即将跳转首页...",function () {

                        $.ajax({
                            url: "/userController/loginout",
                            type: "post",
                            contentType: false,
                            processData: false,
                            cache: false,
                            success: function (data) {
                                if (data.success) {
                                    window.location.href = '/Page/MainPage';
                                }
                            },
                        });
                    });
                } else {
                    $.toast(data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

    // $('#back').click(function() {
    //     window.location.href = '/shopAdmin/shopList';
    // });
});
