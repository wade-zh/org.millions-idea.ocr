define('user',['jquery', 'request'],function ($, request) {
    var UserService = {
        login: function (username, password, vcode) {
            var body = {
                "username": username,
                "password": password
            };
            request.post({
                url: "/authentication/form",
                data: body,
                success: function (data) {
                    console.log(data);
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