define('user',['jquery', 'request','alert'],function ($, request,alert) {
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
                    console.log(data);
                    if(data.error == 0){
                        alert.mdl.footerShortMessage("登录成功");
                    }else{
                        alert.mdl.footerShortMessage('用户名或密码错误');
                    }
                }
            });
        }
    }
    $(function () {

        /*按钮事件*/
        var $loginBtn = $("#user-login-btn"),
            $signInBtn = $("#user-create-btn");

        /*页面元素*/
        var $username = $("#username"),
            $password = $("#password");

        /*动态添加事件*/
        $loginBtn.click(function () {
            UserService.login($username.val(), $password.val(), null);
        });

        $password.keyup(function (event) {
            if(event.keyCode == 13) {
                $loginBtn.click();
            }
        });



    })
})