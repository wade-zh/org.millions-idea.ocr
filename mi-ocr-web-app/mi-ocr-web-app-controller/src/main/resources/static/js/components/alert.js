/**
 * 通用Web弹窗模块
 */
define("alert", ["jquery"] ,function ($) {
    'use strict';
    window['counter'] = 0;

    /**
     * mdl 服务类
     * @type {{tools: {containerName: string, getSelector: getSelector}, footerShortMessage: footerShortMessage}}
     */
    var mdlService = {
        /**
         * mdl 内部工具类
         * @type {{containerName: string, getSelector: getSelector}}
         */
        tools : {
            containerName: "mdl-short-message-container",
            getSelector: function () {
                return document.querySelector('#' + this.containerName);
            }
        },
        /**
         * 贴合页面底部的短消息提醒框
         * @param msg
         */
        footerShortMessage: function (msg, call) {
            var container = this.tools.getSelector();
            'use strict';
            if(call == null) {
                call = function () {

                }
            }
            var data = {
                message: msg,
                timeout: 2000,
                actionHandler: call,
                actionText: '知道了'
            };
            container.MaterialSnackbar.showSnackbar(data);
        }
    }

    var baseService = {
        msg: function (msg) {
            layer.msg(msg);
        }
    }
    return {
        mdl: mdlService,
        base: baseService
    };
})