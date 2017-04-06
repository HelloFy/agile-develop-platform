let util = require('../utils/util.js');
require('../ajaxFileUpload/ajaxfileupload.js');

function deleteDoc(id) {
    require.ensure(["whatwg-fetch"], function () {
        fetch('gen/delete', {
            method: 'delete',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: jQuery.param({id: id})
        }).then(function (response) {
            response.json().then(function (data) {
                let isSuccess = data.result == 'SUCCESS';
                if (isSuccess) {
                    swal("成功", "删除成功", "success")
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
                        deleteDoc($(this).attr('del_id'));
                        $('.tabular.menu #list_doc_tpl').tab('change tab', 'doc_tpl_list');
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

    util.createPage('.pagination',
                    1, 5, 1,
                    function (num, type) {
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
                                        deleteDoc($(this).attr('del_id'));
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

    /*$.ajaxFileUpload
     (
     {
     url: '/gen/uploadDoc', //用于文件上传的服务器端请求地址
     secureuri: false, //是否需要安全协议，一般设置为false
     fileElementId: 'file', //文件上传域的ID
     dataType: 'json', //返回值类型 一般设置为json
     success: function (data, status)  //服务器成功响应处理函数
     {
     let isSuccess = data.result == 'SUCCESS';
     if(isSuccess){
     swal('上传成功','上传成功','success');
     }
     else {
     swal(data.message, data.message, 'error');
     }
     },
     error: function (data, status, e)//服务器响应失败处理函数
     {
     swal(data.message, data.message, 'error');
     }
     }
     )*/
}