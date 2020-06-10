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
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    window.location.href = '/shopAdmin/shopList';
                } else {
                    $.toast(data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

    $('#back').click(function() {
        window.location.href = '/shopAdmin/shopList';
    });
});
