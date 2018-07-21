require.config({
    'baseUrl': '/js/',
    'shim': {
        'layui': {
            'deps': ['jquery','echarts'],
            'exports': 'layui'
        }
    },'paths': {
        'jquery': './jquery-3.3.1.min',
        'request': 'request',
        'layui': '../layui/layui',
        'echarts': './echarts',
        'carousel': './module/carousel',
        'expendCharts': './module/expendCharts',// 消费图表
        'console': './module/console'
    },
    'urlArgs': 'r=' + (new Date()).getTime()
});
require(['jquery', 'layui','carousel','echarts','expendCharts','console'], function (
    $,
    layui,
    carousel,
    echarts,
    expendCharts,
    console) {

});
