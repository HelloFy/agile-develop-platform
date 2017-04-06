let util = require('../utils/util.js')

export function load() {
    let list_scheme = $('.tabular.menu #list_schema');
    let add_scheme = $('.tabular.menu #add_schema');
    list_scheme.tab(
        {
            onLoad: function () {
                util.createPage('.pagination',
                                1, 5, 1,
                                function (num) {
                                    fetch('gen/getSchemeList?page=' + num, {
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
                                                    html += "<td>" + content.packageName + "</td>";
                                                    html += "<td>" + content.moduleName + "</td>";
                                                    html += "<td>" + content.functionName + "</td>";
                                                    html +=
                                                        "<td>" + content.functionAuthor + "</td>";
                                                    html +=
                                                        "<td><a class=\"\" href=\"\">修改</a><a >删除</a></td>";
                                                    html += "</tr>";
                                                });
                                                $('#tb_schema_body').html(html);
                                                $('.pagination').jqPaginator('option', {
                                                    totalPages: message.pages == 0 ? 1
                                                        : message.pages,
                                                });
                                            }
                                            else {
                                                swal(data.message, data.message, 'error');
                                            }
                                            return;
                                        })
                                    }).catch(function (err) {
                                        swal("错误", "服务器繁忙", "error");
                                    })

                                })
                require.ensure(["whatwg-fetch"], function () {
                    fetch('gen/getSchemeList?page=1', {
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
                                    html += "<td>" + content.packageName + "</td>";
                                    html += "<td>" + content.moduleName + "</td>";
                                    html += "<td>" + content.functionName + "</td>";
                                    html += "<td>" + content.functionAuthor + "</td>";
                                    html += "<td><a class=\"\" href=\"\">修改</a><a >删除</a></td>";
                                    html += "</tr>";
                                });
                                $('#tb_schema_body').html(html);
                            }
                            else {
                                swal(data.message, data.message, 'error');
                            }
                            return;
                        })
                    }).catch(function (err) {
                        swal("错误", "服务器繁忙", "error");
                    })
                })
            }
        });
    add_scheme.tab(
        {
            onLoad: function () {
                require.ensure(["whatwg-fetch"], function () {
                    fetch('gen/getBusinessTables?page=0', {
                        method: 'GET',
                        credentials: 'include'
                    }).then(function (response) {
                        console.log(response);
                        response.json().then(function (data) {
                            let isSuccess = data.result == 'SUCCESS';
                            if (isSuccess) {
                                let message = data.message;
                                console.log(message);
                                let html = '';
                                $.each(message, function (index, content) {
                                    html +=
                                        "<div class='item' data-value=\"" + content.id + "\">"
                                        + content.tableName + ":" + content.tableComments
                                        + "</div>";
                                });
                                $("#genTableId .menu").html(html);
                                $('#genTableId').dropdown('clear');
                            }
                            else {
                                swal(data.message, data.message, "error");
                            }
                            return ;
                        })
                    }).catch(function (err) {
                        swal("错误", "服务器繁忙", "error");
                    })
                })
            }

        });
    $('.ui.dropdown').dropdown();

    $('.ui.form').submit(function () {
        return false;
    });

    list_scheme.tab('change tab', 'schema_list');

    $('#save_gen_code').click(function () {
        let name = $("input[name='name']").val(),
            category = $('#category').dropdown('get value'),
            packageName = $("input[name='packageName']").val(),
            moduleName= $("input[name='moduleName']").val(),
            subModuleName= $("input[name='subModuleName']").val(),
            functionName= $("input[name='functionName']").val(),
            functionNameSimple= $("input[name='functionNameSimple']").val(),
            functionAuthor= $("input[name='functionAuthor']").val(),
            genTableId= $('#genTableId').dropdown('get value'),
            replaceFile= $("input[name='replaceFile']").val();
        fetch('gen/saveAndGenCode', {
            method: 'put',
            credentials: 'include',
            headers:{
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'name='+name+'&category='+category+'&packageName='+packageName+'&moduleName='+moduleName
                  +'&subModuleName='+subModuleName+'&functionName='+functionName+'&functionNameSimple='+functionNameSimple
                  +'&functionAuthor='+functionAuthor+'&genTableId='+genTableId+'&replaceFile='+replaceFile
        }).then(function (response) {
            response.json().then(function (data) {
                let isSuccess = data.result == 'SUCCESS' ;
                if (isSuccess) {
                    let allFileGen = data.message;
                    swal({
                             title: "添加并生成成功",
                             text: allFileGen + "是否继续添加?",
                             type: "success",
                             html: true,
                             showCancelButton: true,
                             confirmButtonText: "是的",
                             cancelButtonText: "不了",
                             closeOnConfirm: true,
                             closeOnCancel: true
                         },
                         function (isConfirm) {
                             if (isConfirm) {
                                 $('.ui.form').form('clear');
                             }
                             else {
                                 list_scheme
                                     .tab('change tab', 'schema_list');
                             }
                         });
                }
                else {
                    swal(data.message,data.message, "error");
                }
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
        })
    });

    $('#save_scheme').click(function () {
        let name = $("input[name='name']").val(),
            category = $('#category').dropdown('get value'),
            packageName = $("input[name='packageName']").val(),
            moduleName= $("input[name='moduleName']").val(),
            subModuleName= $("input[name='subModuleName']").val(),
            functionName= $("input[name='functionName']").val(),
            functionNameSimple= $("input[name='functionNameSimple']").val(),
            functionAuthor= $("input[name='functionAuthor']").val(),
            genTableId= $('#genTableId').dropdown('get value'),
            replaceFile= $("input[name='replaceFile']").val();
        fetch('gen/saveScheme', {
            method: 'put',
            credentials: 'include',
            headers:{
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'name='+name+'&category='+category+'&packageName='+packageName+'&moduleName='+moduleName
                    +'&subModuleName='+subModuleName+'&functionName='+functionName+'&functionNameSimple='+functionNameSimple
                    +'&functionAuthor='+functionAuthor+'&genTableId='+genTableId+'&replaceFile='+replaceFile
        }).then(function (response) {
            response.json().then(function (data) {
                let isSuccess = data.result == 'SUCCESS' ;
                if (isSuccess) {
                    swal({
                             title: "添加成功",
                             text: "是否继续添加?",
                             type: "success",
                             showCancelButton: true,
                             confirmButtonText: "是的",
                             cancelButtonText: "不了",
                             closeOnConfirm: true,
                             closeOnCancel: true
                         },
                         function (isConfirm) {
                             if (isConfirm) {
                                $('.ui.form').form('clear');
                             }
                             else {
                                 list_scheme
                                     .tab('change tab', 'schema_list');
                             }
                         });
                }
                else {
                    swal(data.message,data.message, "error");
                }
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
        })
    })
}