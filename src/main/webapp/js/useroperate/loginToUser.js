$(function() {
    var loginUrl = '/userController/login';
    var loginCount = 0;

    $('#submit').click(function() {
        var userName = $('#username').val();
        var password = $('#psw').val();
        var verifyCodeActual = $('#j_captcha').val();
        var needVerify = false;
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
            }
        }
        $.ajax({
            url : loginUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                userName : userName,
                password : password,
                verifyCodeActual : verifyCodeActual,
                needVerify : needVerify
            },
            success : function(data) {
                if (data.success) {
                    if(data.shopOwner) {
                        $.alert("违规：非商家账户登录！");
                    }else {
                        window.location.href = '/Page/MainPage';
                    }
                } else {
                    $.toast(data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        $('#verifyPart').show();
                    }
                }
            }
        });
    });

    $('#register').click(function() {
        window.location.href = '/Page/Register';
    });
});