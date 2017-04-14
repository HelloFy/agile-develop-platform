let util = require('../utils/util.js')

function del_schema(id, selector) {
    fetch('gen/deleteScheme', {
        method: 'post',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: jQuery.param({id: id, _method: 'delete'})
    }).then(function (response) {
        response.json().then(function (data) {
            let isSuccess = data.result == 'SUCCESS';
            let message = data.message;
            if (isSuccess) {
                selector.parents("tr").remove();
                console.log($(this).parent().parent());
                swal("成功", "删除成功", "success")
            }
            else {
                swal(data.message, data.message, 'error');
            }
        })
    }).catch(function (err) {
        swal("错误", "服务器繁忙", "error");
    })
}

function get_business_tb(callback) {
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
                    if (callback) {
                        callback(data);
                    }
                }
                else {
                    swal(data.message, data.message, "error");
                }
                return;
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
        })
    })
}

function getschema(id) {
    require.ensure(["whatwg-fetch"], function () {
        fetch('gen/getSchema?id=' + id, {
            method: 'get',
            credentials: 'include'
        }).then(function (response) {
            response.json().then(function (data) {
                let isSuccess = data.result == 'SUCCESS';
                if (isSuccess) {
                    let gen_schema = data.message;
                    $("input[name='name']").val(gen_schema.name)
                    $('#category').dropdown('set selected', gen_schema.category);
                    $("input[name='packageName']").val(gen_schema.packageName);
                    $("input[name='moduleName']").val(gen_schema.moduleName);
                    $("input[name='subModuleName']").val(gen_schema.subModuleName);
                    $("input[name='functionName']").val(gen_schema.functionName);
                    $("input[name='functionNameSimple']").val(gen_schema.functionNameSimpleIdle);
                    $("input[name='functionAuthor']").val(gen_schema.functionAuthor);
                    $('#genTableId').dropdown('set selected', gen_schema.genTableId);
                    if (gen_schema.replaceFile) {
                        $("input[name='replaceFile']").attr('checked', true);
                    }
                    $("input[name='id']").val(gen_schema.id);
                }
                else {
                    swal("错误", "服务器繁忙", "error");
                }
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
        })
    })
}

function modify_schema(id) {
    $('.tabular.menu #add_schema').tab('change tab', 'schema_add');
    get_business_tb(getschema(id));
}

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
                                                        "<td><a class=\"modify\" href=\"javascript:void(0)\" modify_id=\""
                                                        + content.id
                                                        + "\">修改</a><a class=\"del\" href=\"javascript:void(0)\" del_id=\""
                                                        + content.id + "\">删除</a></td>";
                                                    html += "</tr>";
                                                });
                                                $('#tb_schema_body').html(html);
                                                $('.del').click(function () {
                                                    var id = $(this).attr('del_id');
                                                    del_schema(id, $(this))
                                                });
                                                $('.modify').click(function () {
                                                    modify_schema($(this).attr('modify_id'));
                                                });
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
            }
        });
    add_scheme.tab(
        {
            onLoad: function () {
                get_business_tb();
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
            replaceFile = $("input[name='replaceFile']").val(),
            id = $("input[name='id']").val();
        fetch('gen/saveAndGenCode', {
            method: 'put',
            credentials: 'include',
            headers:{
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'id=' + id + '&name=' + name + '&category=' + category + '&packageName='
                  + packageName + '&moduleName=' + moduleName
                  + '&subModuleName=' + subModuleName + '&functionName=' + functionName + '&functionNameSimple=' + functionNameSimple
                  + '&functionAuthor=' + functionAuthor + '&genTableId=' + genTableId + '&replaceFile=' + replaceFile
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
            replaceFile = $("input[name='replaceFile']").val(),
            id = $("input[name='id']").val();
        fetch('gen/saveScheme', {
            method: 'put',
            credentials: 'include',
            headers:{
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'id=' + id + '&name=' + name + '&category=' + category + '&packageName='
                  + packageName + '&moduleName=' + moduleName
                  + '&subModuleName=' + subModuleName + '&functionName=' + functionName + '&functionNameSimple=' + functionNameSimple
                  + '&functionAuthor=' + functionAuthor + '&genTableId=' + genTableId + '&replaceFile=' + replaceFile
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

    $('#query_schema_btn').click(function () {
        var name = $('#name').val();
        util.createPage('.pagination',
                        1, 5, 1,
                        function (num) {
                            fetch('gen/getSchemeList?page=' + num + '&name=' + name, {
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
                                                "<td><a class=\"modify\" href=\"javascript:void(0)\" modify_id=\""
                                                + content.id
                                                + "\">修改</a><a class=\"del\" href=\"javascript:void(0)\" del_id=\""
                                                + content.id + "\">删除</a></td>";
                                            html += "</tr>";
                                        });
                                        $('#tb_schema_body').html(html);
                                        $('.del').click(function () {
                                            var id = $(this).attr('del_id');
                                            del_schema(id, $(this))
                                        });
                                        $('.modify').click(function () {
                                            modify_schema($(this).attr('modify_id'));
                                        });
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
    })
}