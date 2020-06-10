$(function () {
    var registerUrl = '/userController/register';

    $('#submit').click(function () {
        var username = $('#userName').val();
        var password = $('#password').val();
        var dpassword = $('#dpassword').val();
        var birthday = $('#birthday').val();
        var phone = $('#phone').val();
        var email = $('#email').val();
        var name = $('#name').val();
        var needVerify = false;
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
        }
        if(password===dpassword){
            $.ajax({
                url:registerUrl,
                async : false,
                cache : false,
                type : "post",
                dataType : 'json',
                data:{
                    username : username,
                    password : password,
                    birthday : birthday,
                    phone : phone,
                    email : email,
                    name : name,
                    needVerify : needVerify,
                    verifyCodeActual : verifyCodeActual
                },
                success : function (data) {
                    if(data.success){
                        $.confirm(
                            "注册成功,现在跳转到登录界面...",function(){
                                window.location.href = 'user/loginByUser';
                            }
                        )
                    }else {
                        $.confirm({
                            title : '注册失败',
                            content : data.errMsg,
                        })
                    }
                }
            })
        }else {
            $.toast('两次密码不一致！');
            return;
        }
    })
})