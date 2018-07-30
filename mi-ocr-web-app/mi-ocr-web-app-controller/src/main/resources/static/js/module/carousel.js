define('carousel',['jquery','layui'],function ($,layui) {
    $(function () {
        layui.use(['carousel', 'form'], function(){
            layui.carousel.render({
                elem: '#banner'
                ,width: '100%'
                ,height: '550px'
                ,interval: 300000
            });
        });
    });
})