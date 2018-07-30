define('console',['jquery'],function ($) {
    $(function () {
        /*new dialog begin*/
        var dialog = document.querySelector('dialog');
        $('#starks-panel a').click(function () {
            dialog.showModal();
        });
        dialog.querySelector('.close').addEventListener('click', function() {
            dialog.close();
        });
        /*new dialog end*/

        /*computer balance begin*/
        parseFloat($('.balance').text()) <= 0 ?
            $('.balance').parent().parent().parent().parent().parent().removeClass('mi-border-color-green').addClass('mi-border-color-red') :
            $('.balance').parent().parent().parent().parent().parent().removeClass('mi-border-color-red').addClass('mi-border-color-green')
    })
})