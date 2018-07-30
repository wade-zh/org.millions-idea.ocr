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
        'echarts': './echarts',
        'carousel': './module/carousel',
        'expendCharts': './module/expendCharts',// 消费图表
        'console': './module/console',
        'user': './module/user'
    },
    'urlArgs': 'r=' + (new Date()).getTime()
});
require(['jquery','layui'], function (
    $) {

});
