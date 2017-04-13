let util = require('../utils/util.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/vendor/jquery.ui.widget.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/jquery.iframe-transport.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/jquery.fileupload.js');

function deleteDoc(id, selector) {
    require.ensure(["whatwg-fetch"], function () {
        fetch('gen/delete', {
            method: 'post',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: jQuery.param({id: id, _method: 'DELETE'})
        }).then(function (response) {
            response.json().then(function (data) {
                let isSuccess = data.result == 'SUCCESS';
                if (isSuccess) {
                    selector.parents("tr").remove();
                    swal("成功", "删除成功", "success");
                }
                else {
                    swal(data.message, data.message, 'error');
                }
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
        })
    })
}

function getDocList(page) {
    console.log(page);
    require.ensure(["whatwg-fetch"], function () {
        fetch('gen/getGenDocList?page=' + page, {
            method: 'get',
            credentials: 'include'
        }).then(function (response) {
            response.json().then(function (data) {
                let isSuccess = data.result == 'SUCCESS';
                let message = data.message;
                if (isSuccess) {
                    let html = '';
                    $.each(message.list, function (index, content) {
                        html += "<tr>";
                        html += "<td>" + content.docName + "</td>";
                        html += "<td>" + content.docSize + "</td>";
                        html += "<td>" + content.uploadDate + "</td>";
                        html +=
                            "<td><a class=\"item\" href=\"gen/downLoadTpl?id=" + content.id
                            + "\">下载</a>"
                            + "<a class=\"del item\" href=\"javascript:void(0)\" del_id=\""
                            + content.id + "\" >删除</a></td>";
                        html += "</tr>";
                    });
                    $('#tb_doc_body').html(html);
                    $('.del.item').click(function () {
                        console.log('del_id:' + $(this).attr('del_id'));
                        deleteDoc($(this).attr('del_id'), $(this));
                    });
                    $('.pagination').jqPaginator('option', {
                        totalPages: message.pages == 0 ? 1 : message.pages,
                    });
                }
                else {
                    swal(data.message, data.message, 'error');
                }
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
        })
    })
}

function queryDocList(docName) {

    util.createPage('.pagination', 1, 5, 1, function (num, type) {
                        fetch('gen/getGenDocList?page=' + num + '&&docName=' + docName, {
                            method: 'get',
                            credentials: 'include'
                        }).then(function (response) {
                            response.json().then(function (data) {
                                let isSuccess = data.result == 'SUCCESS';
                                let message = data.message;
                                if (isSuccess) {
                                    let html = '';
                                    $.each(message.list, function (index, content) {
                                        html += "<tr>";
                                        html += "<td>" + content.docName + "</td>";
                                        html += "<td>" + content.docSize + "</td>";
                                        html += "<td>" + content.uploadDate + "</td>";
                                        html +=
                                            "<td><a class=\"item\" href=\"gen/downLoadTpl?id="
                                            + content.id
                                            + "\">下载</a>"
                                            + "<a class=\"del item\" href=\"javascript:void(0)\" del_id=\""
                                            + content.id + "\">删除</a></td>";
                                        html += "</tr>";
                                    });
                                    $('#tb_doc_body').html(html);
                                    $('.pagination').jqPaginator('option', {
                                        totalPages: message.pages == 0 ? 1 : message.pages
                                    });
                                    $('.del.item').click(function () {
                                        deleteDoc($(this).attr('del_id'), $(this));
                                        $('.tabular.menu #list_doc_tpl')
                                            .tab('change tab', 'doc_tpl_list');
                                    });
                                }
                                else {
                                    swal(data.message, data.message, 'error');
                                }
                            })
                        })
                    });
}

export function load() {
    let list_doc_tpl = $('.tabular.menu #list_doc_tpl');
    let add_doc_tpl = $('.tabular.menu #add_doc_tpl');

    list_doc_tpl.tab(
        {
            onLoad: function () {
                require.ensure(["whatwg-fetch"], function () {
                    util.createPage('.pagination',
                                    1, 5, 1,
                                    getDocList)
                })
            }
        }
    );

    add_doc_tpl.tab();
    list_doc_tpl.tab('change tab', 'doc_tpl_list');

    $('#query_doc_btn').click(function () {
        queryDocList($('#docName').val());
    });

    $('.ui.form').submit(function () {
        return false;
    });

    $('#fileupload').fileupload({
                                    dataType: 'json',
                                    autoUpload: false,
                                    acceptFileTypes: /(\.|\/)(doc|dot|docx)$/,
                                    maxFileSize: 50000000, // 50 MB
                                    // Enable image resizing, except for Android and Opera,
                                    // which actually support image resizing, but fail to
                                    // send Blob objects via XHR requests:
                                    previewMaxWidth: 100,
                                    previewMaxHeight: 100,
                                    previewCrop: true
                                }).on('fileuploadadd', function (e, data) {
        $.each(data.files, function (index, file) {
            $('.text').text(file.name);
        });
        $('#upload').click(function () {
            data.submit();
        })
    }).on('fileuploadsubmit', function (e, data) {
        data.formData = {docName: $("#up_doc_name").val()};  //如果需要额外添加参数可以在这里添加
    }).on('fileuploaddone', function (e, data) {
        swal('上传成功', '上传成功', 'success');
    }).on('fileuploadfail', function (e, data) {
        swal('上传失败', '上传失败', 'error');
    })
}