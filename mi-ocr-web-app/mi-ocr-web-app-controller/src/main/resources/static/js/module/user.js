define('user',['jquery', 'request','alert'],function ($, request, alert) {
    var UserService = {
        /**
         * 发送登录请求
         * @param username
         * @param password
         * @param vcode
         */
        login: function (username, password, vcode) {
            var body = {
                "username": username,
                "password": password
            };
            request.post({
                url: "/api/asyncLogin",
                data: body,
                success: function (data) {
                    if(data.error == 0){
                        location.href = "/user"
                    }else{
                        alert.mdl.footerShortMessage('用户名或密码错误');
                    }
                }
            });
        },
        /**
         * 发送注册请求
         * @param username
         * @param password
         * @param email
         */
        register: function (username, password, email, callback) {
            var body = {
                "username": username,
                "password": password,
                "email": email
            };
            request.post({
                url: "/api/register",
                data: body,
                success: function (data) {
                    if(data.error == 0){
                        location.href = "/signup"
                    }else{
                        alert.mdl.footerShortMessage('注册失败');
                    }
                    callback();
                }
            });
        },
        verifyPassword: function ($password,$password2) {
            if($password2.val() != $password.val()){
                $password2.parent().addClass("is-invalid");
            }
        }
    }
    $(function () {

        /*按钮事件*/
        var $loginBtn = $("#user-login-btn"),
            $signInBtn = $("#user-create-btn"),
            $registerBtn = $("#btn-register");

        /*页面元素*/
        var $username = $("#username"),
            $password = $("#password"),
            $password2 = $("#password2"),
            $email = $("#email");

        /*动态添加事件*/
        $loginBtn.click(function () {
            UserService.login($username.val(), $password.val(), null);
        })

        $signInBtn.click(function () {
            location.href = "/signin"
        })

        $registerBtn.click(function () {
            $registerBtn.attr("disabled",true);
            UserService.register($username.val(), $password.val(), $email.val(), function () {
                $registerBtn.removeAttr("disabled");
            });
        })

        $password.keyup(function (event) {
            if(event.keyCode == 13) {
                $loginBtn.click();
            }
        });

        $password2.blur(function () {
            UserService.verifyPassword($password,$password2);
        });


    })
})