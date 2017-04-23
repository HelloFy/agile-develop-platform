/**
 * Created by 费玥 on 2017/4/15.
 */
require('imports?define=>false&exports=>false!blueimp-file-upload/js/vendor/jquery.ui.widget.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/jquery.iframe-transport.js');
require('imports?define=>false&exports=>false!blueimp-file-upload/js/jquery.fileupload.js');
let util = require('../utils/util.js');

function queryUMLList(uml_name) {
    util.createPage('.pagination', 1, 5, 1, function (num) {
        require.ensure(["whatwg-fetch"], function () {
            if (uml_name == undefined) {
                uml_name = '';
            }
            fetch('gen/getGenUmlClassDiagramList?page=' + num + '&classDiagramName=' + uml_name, {
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
                            html += "<td>" + content.classDiagramName + "</td>";
                            html += "<td>" + content.comments + "</td>";
                            html +=
                                "<td><a class=\"item\" href=\"gen/downLoad?id=" + content.id
                                + "\">下载</a>"
                                + "<a class=\"del item\" href=\"javascript:void(0)\" del_id=\""
                                + content.id + "\" >删除</a>"
                                + "<a class=\"generate item\" href=\"javascript:void(0)\" gen_id=\""
                                + content.id + "\" >生成代码</a></td>";
                            html += "</tr>";
                        });
                        $('#tb_uml_body').html(html);
                        $('.del.item').click(function () {
                            console.log('del_id:' + $(this).attr('del_id'));
                            deleteUML($(this).attr('del_id'), $(this));
                        });
                        $('.generate.item').click(function () {
                            util.generate_code($(this).attr('gen_id'), $('#uml_list'),
                                               $('#schema_form'));
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
        });
    });
}

function deleteUML(id, selector) {
    require.ensure(["whatwg-fetch"], function () {
        fetch('gen/deleteGenUmlClassDiagram', {
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

function generate_code(id) {
    $('#uml_list').addClass('hidden');
    require.ensure(["whatwg-fetch", "./genSchema.js"], function () {
        let func = require('./genSchema.js');
        func.getschema_refId(id, $('#schema_form'));
    });
}

export function load() {
    let uml_class_list = $('.tabular.menu #uml_class_list');
    let add_uml_class = $('.tabular.menu #add_uml_class');

    uml_class_list.tab(
        {
            onLoad: function () {
                queryUMLList();
            }
        }
    );
    add_uml_class.tab();
    uml_class_list.tab('change tab', 'uml_class_list');

    $('#query_uml_btn').click(function () {
        queryUMLList($('#uml_class_name').val());
    });

    $('.ui.form').submit(function () {
        return false;
    });

    $('#save_gen_code').click(function () {
        require.ensure(["whatwg-fetch", "./genSchema.js"], function () {
            let func = require('./genSchema.js');
            func.save_and_gen('gen/genCodeByUML', $('#schema_form'), $('#uml_list'));
        });
    });

    $('#fileupload').fileupload({
                                    dataType: 'json',
                                    autoUpload: false,
                                    acceptFileTypes: /(\.|\/)mdj$/,
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
        data.formData =
            {
                classDiagramName: $("#up_uml_name").val(),
                comments: $('#uml_comments').val(),
                _method: 'PUT'
            };  //如果需要额外添加参数可以在这里添加
    }).on('fileuploaddone', function (e, data) {
        swal('上传成功', '上传成功', 'success');
    }).on('fileuploadfail', function (e, data) {
        swal('上传失败', '上传失败', 'error');
    })
}