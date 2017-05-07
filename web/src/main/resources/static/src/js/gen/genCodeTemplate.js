/**
 * Created by 费玥 on 2017/4/18.
 */
let util = require('../utils/util.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/vendor/jquery.ui.widget.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/jquery.iframe-transport.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/jquery.fileupload.js');

function deleteCode(id, selector) {
    require.ensure(["whatwg-fetch"], function () {
        fetch('gen/deleteGenCodeTemplate', {
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

function queryCodeList(name, func) {
    if (name == undefined) {
        name = '';
    }
    if (func == undefined) {
        func = '';
    }
    util.createPage('.pagination', 1, 5, 1, function (num, type) {
        fetch('gen/getGenCodeTemplateList?page=' + num + '&name=' + name + '&func=' + func, {
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
                        html += "<td>" + content.name + "</td>";
                        html += "<td>" + content.function + "</td>";
                        html += "<td>" + content.comments + "</td>";
                        html +=
                            "<td><a class=\"item\" href=\"gen/downloadCode?id="
                            + content.id
                            + "\">下载</a>"
                            + "<a class=\"del item\" href=\"javascript:void(0)\" del_id=\""
                            + content.id + "\">删除</a></td>";
                        html += "</tr>";
                    });
                    $('#tb_code_body').html(html);
                    $('.pagination').jqPaginator('option', {
                        totalPages: message.pages == 0 ? 1 : message.pages
                    });
                    $('.del.item').click(function () {
                        deleteCode($(this).attr('del_id'), $(this));
                        $('.tabular.menu #list_genCodeTemplate_tpl')
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
    let list_code_tpl = $('.tabular.menu #list_genCodeTemplate_tpl');
    let add_code_tpl = $('.tabular.menu #add_genCodeTemplate_tpl');

    list_code_tpl.tab(
        {
            onLoad: function () {
                require.ensure(["whatwg-fetch"], function () {
                    queryCodeList();
                })
            }
        }
    );

    add_code_tpl.tab();
    list_code_tpl.tab('change tab', 'genCodeTemplate_tpl_list');

    $('#query_code_btn').click(function () {
        queryCodeList($('#code_name').val(), $('#function').val());
    });

    $('.ui.form').submit(function () {
        return false;
    });

    $('#fileupload').fileupload({
                                    dataType: 'json',
                                    autoUpload: false,
                                    maxFileSize: 50000000, // 50 MB
                                    // Enable image resizing, except for Android and Opera,
                                    // which actually support image resizing, but fail to
                                    // send Blob objects via XHR requests:
                                    previewMaxWidth: 100,
                                    previewMaxHeight: 100,
                                    previewCrop: true,
                                    singleFileUploads: false
                                }).on('fileuploadadd', function (e, data) {
        $.each(data.files, function (index, file) {
            $('#up_code_name').val(file.name);
        });
        $('#upload').unbind('click');
        $('#upload').click(function () {
            data.submit();
        })
    }).on('fileuploadsubmit', function (e, data) {
        data.formData =
            {
                name: $("#up_code_name").val(),
                function: $('#up_code_func').val(),
                comments: $('#up_code_comments').val(),
                _method: 'PUT'
            };  //如果需要额外添加参数可以在这里添加
    }).on('fileuploaddone', function (e, data) {
        swal('上传成功', '上传成功', 'success');
    }).on('fileuploadfail', function (e, data) {
        swal('上传失败', '上传失败', 'error');
    })
}