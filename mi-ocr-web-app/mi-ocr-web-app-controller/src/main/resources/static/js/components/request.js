define('request', ['jquery','layui'], function ($) {
    return {
            post: function (param) {
                /*var header = $("meta[name='_csrf_header']").attr("content");
                var token =$("meta[name='_csrf']").attr("content");*/
                var index;
                $.ajax({
                    type: "POST",
                    url: param.url,
                    data: param.data,
                    dataType: "json",
                    success: param.success,
                    error: function () {
                        layer.close(index);
                        layer.msg('请求超时');
                    },
                    beforeSend: function (xhr) {
                        /*xhr.setRequestHeader(header, token);*/
                        index = layer.load(4, {
                            shade: [0.1,'#000']
                        });
                    },
                    complete: function () {
                        layer.close(index);
                    }
            });
        },
        POST: function (param) {
            $.ajax({
                type: "POST",
                url: param.url,
                data: param.data,
                dataType: param.dataType,
                success: param.success,
                error: param.error,
                beforeSend: param.beforeSend,
                complete: param.complete
            });
        },
        GET: function (param) {
            $.ajax({
                type: "GET",
                url: param.url,
                data: param.data,
                dataType: param.dataType,
                success: param.success,
                error: param.error,
                beforeSend: param.beforeSend,
                complete: param.complete
            });
        }
    }
})