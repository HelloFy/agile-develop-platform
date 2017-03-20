require('../../css/gen/gen.css');
require('../jqPagination/jqPaginator.js');

export function load() {
    $('.tabular.menu #list_schema').tab(
        {
            onLoad: function () {
                require.ensure(["whatwg-fetch"], function () {
                    fetch('gen/getSchemaList', {
                        method: 'post',
                        credentials: 'include'
                    }).then(function (response) {
                        response.json().then(function (data) {
                            var isSuccess = data.result == 'SUCCESS' ? true : false;
                            if (isSuccess) {
                                var html = '';
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
    $('.tabular.menu #add_schema').tab(
        {
            onLoad: function () {
                require.ensure(["whatwg-fetch"], function () {
                    fetch('gen/getPhysicalTables', {
                        method: 'POST',
                        credentials: 'include'
                    }).then(function (response) {
                        console.log(response);
                        response.json().then(function (data) {
                            var isSuccess = data.result == 'SUCCESS' ? true : false;
                            if (isSuccess) {
                                var message = data.message;
                                console.log(message);
                                var html = '';
                                $.each(message, function (index, content) {
                                    html +=
                                        "<div class='item' data-value=\"" + content.id + "\">"
                                        + content.tableName + ":" + content.tableComments
                                        + "</div>";
                                });
                                $("#genTableId").html(html);
                                $('#genTableId').dropdown('clear');
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

        });
    $('.ui.dropdown').dropdown();

    $('.tabular.menu #list_schema').tab('change tab', 'schema_list');

    $('#save_gen_code').click(function () {

    });

    $('#save_scheme').click(function () {
        fetch('gen/saveScheme', {
            method: 'post',
            credentials: 'include',
            body: {
                name: $("input[name='name']").val(),
                category: $('#category').dropdown('get value'),
                packageName: $("input[name='packageName']").val(),
                moduleName: $("input[name='moduleName']").val(),
                subModuleName: $("input[name='subModuleName']").val(),
                functionName: $("input[name='functionName']").val(),
                functionNameSimple: $("input[name='functionNameSimple']").val(),
                functionAuthor: $("input[name='functionAuthor']").val(),
                genTableId: $('#category').dropdown('get value'),
                replaceFile: $("input[name='replaceFile']").val()
            }
        }).then(function (response) {
            response.json().then(function (data) {
                var isSuccess = data.result == 'SUCCESS' ? true : false;
                if (isSuccess) {

                }
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
        })
    })
}