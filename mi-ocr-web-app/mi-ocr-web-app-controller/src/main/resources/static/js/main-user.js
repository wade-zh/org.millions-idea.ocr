require.config({
    'baseUrl': '/js/',
    'shim': {
        'layui': {
            'deps': ['jquery','echarts'],
            'exports': 'layui'
        }
    },'paths': {
        'jquery': './jquery-3.3.1.min',
        'request': './components/request',
        'layui': '../layui/layui',
        'alert': './components/alert',
        'user': './module/user'
    },
    'urlArgs': 'r=' + (new Date()).getTime()
});

require(['jquery','request','layui','alert','user'],function ($, request, layui, alert, user) {

})