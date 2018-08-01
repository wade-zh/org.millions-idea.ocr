define('console',['jquery','alert'],function ($,alert) {
    var UserService = {
        getBalance: function (callback) {
            $.get("/api/getBalance", function (data) {
                callback(data);
            });
        },
        getRecentOrders: function (uid, callback) {
            $.get("/order-api/getRecentOrders?uid=" + uid, function (data) {
                callback(data);
            });
        }
    }

    $(function () {
        var $refreshBalance = $(".refresh"),
            $balance = $("#balance"),
            uid = $("input[name='uid']").val();

        // 公告栏弹窗
        var dialog = document.querySelector('dialog');
        $('#starks-panel a').click(function () {
            dialog.showModal();
        });
        dialog.querySelector('.close').addEventListener('click', function() {
            dialog.close();
        });

        // 余额模块边框颜色
        parseFloat($('.balance').text()) <= 0 ?
            $('.balance').parent().parent().parent().parent().parent().removeClass('mi-border-color-green').addClass('mi-border-color-red') :
            $('.balance').parent().parent().parent().parent().parent().removeClass('mi-border-color-red').addClass('mi-border-color-green')

        // 刷新余额
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

        // 刷新余额ICON小提示
        var referer_tips_index;
        $refreshBalance.hover(function () {
            referer_tips_index = layer.tips('刷新余额', '.refresh', {tips: 3});
        },function () {
            layer.close(referer_tips_index);
        })

        // 统计成功率与昨天消耗资金总量
        UserService.getRecentOrders(uid, function (data) {
            var $success = $("#success-rate"),
                $consume = $("#consume"),
                $recentConsume = $("#recent_consume"),
                success = 0,
                recentConsume = '0.0000',
                consume = '0.0000';

            // 计算成功率
            success = getRate(data);

            // 计算昨日消耗资金
            recentConsume = getRecentConsume(data);
            consume = getConsume(data);

            if(isNaN(success) || success == null) success = "0";
            if(recentConsume == 0) recentConsume = "0.0000";
            if(consume == 0) consume = "0.0000";
            $success.text(success);
            $recentConsume.text(recentConsume);
            $consume.text(consume);
        })
    })

    /**
     * 计算成功率
     * @param data
     * @returns {number}
     */
    function getRate(data) {
        var successCount = 0,
            count = 0;
        data.forEach(function (item) {
           if(item.toDays == 1){
                if(item.ack == 1 || item.isAvailable == 1){
                    successCount += 1;
                }
           }
            count += 1;
        })
        return successCount / count * 100;
    }

    /**
     * 计算消耗总额
     * @param data
     * @returns {number}
     */
    function getRecentConsume(data) {
        var count = 0;
        data.forEach(function (item) {
            if(item.toDays == 0){
                if(item.ack == 1){
                    count += item.tradeAmount;
                }
            }
        })
        return count;
    }

    function getConsume(data) {
        var count = 0;
        data.forEach(function (item) {
            if(item.toDays == 1){
                if(item.ack == 1){
                    count += item.tradeAmount;
                }
            }
        })
        return count;
    }
})