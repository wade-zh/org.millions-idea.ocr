define('console',['jquery','alert'],function ($,alert) {
    var UserService = {
        getBalance: function (callback) {
            $.get("/api/getBalance", function (data) {
                callback(data);
            });
        }
    }

    $(function () {
        var $refreshBalance = $(".refresh"),
            $balance = $("#balance");


        /*弹窗提醒*/
        var dialog = document.querySelector('dialog');
        $('#starks-panel a').click(function () {
            dialog.showModal();
        });
        dialog.querySelector('.close').addEventListener('click', function() {
            dialog.close();
        });

        /*余额边框*/
        parseFloat($('.balance').text()) <= 0 ?
            $('.balance').parent().parent().parent().parent().parent().removeClass('mi-border-color-green').addClass('mi-border-color-red') :
            $('.balance').parent().parent().parent().parent().parent().removeClass('mi-border-color-red').addClass('mi-border-color-green')

        $refreshBalance.click(function () {
            $refreshBalance.attr("disabled",true);
            $balance.text("...");
            UserService.getBalance(function (data) {
                $refreshBalance.removeAttr("disabled");
                if(data == null || data.error == 1 || data.msg == null) {
                    alert.base.msg("刷新失败");
                    return;
                }
                $balance.text(data.msg);
            })
        })

        var referer_tips_index;
        $refreshBalance.hover(function () {
            referer_tips_index = layer.tips('刷新余额', '.refresh', {tips: 3});
        },function () {
            layer.close(referer_tips_index);
        })

    })
})