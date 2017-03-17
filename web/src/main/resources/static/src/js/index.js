require('./semantic/semantic.min.js')
require('../css/semantic/dist/semantic.min.css')
require('../template/index.html')
require('../css/index.css')

function setIframeHeight(iframe) {
    if (iframe) {
        var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
        if (iframeWin.document.body) {
            iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
        }
    }
};

$('.ui.accordion')
    .accordion();



$(function () {
    $('#gen_table_item').click(function () {
        console.log('debug');
        $('#main_frame').attr('src','gen/tableForm');});

    setIframeHeight($('#main_frame'));

});
