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
        'console': './module/console',
        'echarts': './echarts',
    },
    'urlArgs': 'r=' + (new Date()).getTime()
});

require(['jquery','request','layui','alert', 'echarts', 'console'],function ($, request, layui, alert, console) {

})