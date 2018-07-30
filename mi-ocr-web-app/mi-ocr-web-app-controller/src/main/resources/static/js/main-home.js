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
        'carousel': './module/carousel'
    },
    'urlArgs': 'r=' + (new Date()).getTime()
});

require(['jquery','request','layui','carousel'],function ($, request, layui, carousel) {

})