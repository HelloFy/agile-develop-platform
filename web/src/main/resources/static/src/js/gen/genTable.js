// require('../../css/gen/gen.css');
// require('../jqPagination/jqPaginator.js');
let util = require('../utils/util.js');
let totalPages = 1;
function get_tb_info(tableName, tableComments, is_modify) {
    let url = 'gen/getTableInfo';
    if (is_modify) {
        url = 'gen/modifyGenTable';
    }
    url += '?tableName=' + tableName + '&tableComments=' + tableComments;
    require.ensure(["whatwg-fetch"], function () {
        fetch(url, {
            method: 'get',
            credentials: 'include'
        }).then(function (response) {
            console.log(response);
            response.json().then(function (data) {
                let isSuccess = data.result;
                if (isSuccess == 'SUCCESS') {
                    next_step();
                    let message = data.message;
                    $("#id").val(message.id);
                    $("input[name='tableName']").val(message.tableName);
                    $("input[name='className']").val(message.className);
                    $("input[name='tableComments']").val(message.tableComments);
                    let queryTypes = ['=', '!=', '<', '>', '>=', '<=', 'LIKE', 'BETWEEN'];
                    let html;
                    $.each(message.columnList, function (index, content) {
                        html += "<tr>";
                        html += "<td >"
                                + "<input type=\"hidden\" name=\"columnList[" + index
                                + "].id\" value=\"" + content.id + "\">"
                                + "<input type=\"hidden\" name=\"columnList[" + index
                                + "].genTableId\" value=\"" + content.genTableId + "\">"
                                + "<input type=\"hidden\" name=\"columnList[" + index
                                + "].name\" value=\"" + content.name + "\">"
                                + content.name +
                                "</td>" +
                                "<td>"
                                + "<input type=\"hidden\" name=\"columnList[" + index
                                + "].comments\" value=\"" + content.comments + "\">"
                                + content.comments +
                                "</td>" +
                                "<td>" +
                                "<input type=\"hidden\" name=\"columnList[" + index
                                + "].jdbcType\" value=\"" + content.jdbcType + "\">"
                                + content.jdbcType +
                                "<input type=\"hidden\" name=\"columnList[" + index
                                + "].javaType\" value=\"" + content.javaType + "\">" +
                                "</td>" +
                                "<td>" +
                                "<input type=\"text\" name=\"columnList[" + index
                                + "].javaField\" value=\"" + content.javaField
                                + "\" maxlength=\"15\">" +
                                "</td>" +
                                "<td>" +
                                "<input type=\"checkbox\" name=\"columnList[" + index
                                + "].isPk\" value=\"1\"";
                        console.log(html);
                        if (content.isPk == '1') {
                            html += " checked >";
                        }
                        else {
                            html += ">";
                        }
                        html += "</td>" +
                                "<td>" +
                                "<input type=\"checkbox\" name=\"columnList[" + index
                                + "].isNull\" value=\"1\"";
                        console.log(html);

                        if (content.isNull == '1') {
                            html += " checked >";
                        }
                        else {
                            html += ">";
                        }
                        html += "</td>" +
                                "<td>" +
                                "<input type=\"checkbox\" name=\"columnList[" + index
                                + "].isInsert\" value=\"1\"";
                        console.log(html);
                        if (content.isInsert == '1') {
                            html += " checked >";
                        }
                        else {
                            html += ">";
                        }
                        html += "</td>" +
                                "<td>" +
                                "<input type=\"checkbox\" name=\"columnList[" + index
                                + "].isEdit\" value=\"1\"";
                        console.log(html);
                        if (content.isEdit == '1') {
                            html += " checked >";
                        }
                        else {
                            html += ">";
                        }
                        html += "</td>" +
                                "<td>" +
                                "<input type=\"checkbox\" name=\"columnList[" + index
                                + "].isList\" value=\"1\"";
                        if (content.isList == '1') {
                            html += " checked >";
                        }
                        else {
                            html += ">";
                        }
                        html += "</td>" +
                                "<td>" +
                                "<input type=\"checkbox\" name=\"columnList[" + index
                                + "].isQuery\" value=\"1\"";
                        if (content.isQuery == '1') {
                            html += " checked >";
                        }
                        else {
                            html += ">";
                        }
                        html += "</td>" +
                                "<td>" +
                                "<select name=\"columnList[" + index
                                + "].queryType\" class=\"ui search dropdown\">";
                        if (content.queryType != null && content.queryType != '') {
                            html +=
                                "<option value=\"" + $('<div>').text(content.queryType).html()
                                + "\" >" + $('<div>').text(content.queryType).html() + "</option>";

                        }
                        $.each(queryTypes, function (index, content) {
                            if (content.queryType != content) {
                                html +=
                                    "<option value=\"" + $('<div>').text(content).html() + "\">"
                                    + $('<div>').text(content).html() + "</option>";
                            }

                        });
                        html += "</tr>";
                    });
                    // console.log(html);
                    $('#table_info').html(html);
                }
                else {
                    swal(data.message, data.message, "error");
                }
                $('#first_step_form').removeClass('loading');
                return;
            })
        }).catch(function (err) {
            swal("错误", "服务器繁忙", "error");
            $('#first_step_form').removeClass('loading');
        })
    });

}

function del_gentb(id, selector) {
    fetch('gen/deleteGenTable', {
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

function modify_gentb(name, comments) {
    $('.tabular.menu #get_business_tables').tab('change tab', 'getPhysicalTables');
    next_step();
    get_tb_info(name, comments, true);
}

function next_step() {
    $('#first_step_form').addClass('hidden');
    $('#next_step_form').removeClass('hidden');
    $('#first_step').removeClass('active');
    $('#first_step').addClass('completed');
    $('#final_step').removeClass('disabled');
    $('#final_step').addClass('active');
}

function last_step() {
    $('#final_step').removeClass('active');
    $('#final_step').addClass('disabled');
    $('#first_step').removeClass('completed');
    $('#first_step').addClass('active');
    $('#next_step_form').addClass('hidden');
    $('#first_step_form').removeClass('hidden');
    $('#first_step_form').removeClass('loading');
}

export function load() {
    $('.tabular.menu #get_business_tables')
        .tab({
                 onLoad: function () {
                     util.createPage('.pagination', 1, 5, 1, function (num) {
                         require.ensure(["whatwg-fetch"], function () {
                             fetch('gen/getBusinessTables?page=' + num, {
                                 method: 'get',
                                 credentials: 'include'
                             }).then(function (response) {
                                 console.log(response);
                                 response.json().then(function (data) {
                                     let html = '';
                                     let isSuccess = data.result == 'SUCCESS';
                                     let message = data.message;
                                     if (isSuccess) {
                                         $.each(message.list, function (index, content) {
                                             html += "<tr>";
                                             html +=
                                                 "<td>"
                                                 + content.tableName
                                                 + "</td>";
                                             html +=
                                                 "<td>"
                                                 + content.tableComments
                                                 + "</td>";
                                             html +=
                                                 "<td>"
                                                 + content.className
                                                 + "</td>";
                                             html +=
                                                 "<td>"
                                                 + "<a class=\"modify\" href=\"javascript:void(0)\" modify_name=\""
                                                 + content.tableName + "\" modify_comments=\""
                                                 + content.tableComments + "\">修改</a>"
                                                 + "<a class=\"del\" href=\"javascript:void(0)\" del_id=\""
                                                 + content.id + "\">删除</a>"
                                                 + "<a class=\"generate\" href=\"javascript:void(0)\" gen_id=\""
                                                 + content.id + "\">生成代码</a>"
                                                 + "</td>";
                                             html += "</tr>";
                                         });
                                         $('#business_tb').html(html);
                                         $('.del').click(function () {
                                             del_gentb($(this).attr('del_id'), $(this));
                                         });
                                         $('.generate').click(function () {
                                             util.generate_code($(this).attr('gen_id'),
                                                                $('#tb_list'), $('#schema_form'));
                                         });
                                         $('.modify').click(function () {
                                             modify_gentb($(this).attr('modify_name'),
                                                          $(this).attr('modify_comments'));
                                         });
                                         $('.pagination').jqPaginator('option', {
                                             totalPages: message.pages == 0 ? 1 : message.pages,
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
                     });
                 }
             });
    $('.tabular.menu #get_phy_tables')
        .tab({
                 onLoad: function () {
                     if ($('#final_step').hasClass('active')) {
                         return;
                     }
                     require.ensure(["whatwg-fetch"], function () {
                         fetch('gen/getPhysicalTables', {
                             method: 'get',
                             credentials: 'include'
                         }).then(function (response) {
                             console.log(response);
                             response.json().then(function (data) {
                                 let isSuccess = data.result == 'SUCCESS' ? true : false;
                                 if (isSuccess) {
                                     let message = data.message;
                                     console.log(message);
                                     let html = '';
                                     $.each(message, function (index, content) {
                                         html +=
                                             "<div class='item' data-value=\"" + content.tableName
                                             + ":" + content.tableComments + "\">"
                                             + content.tableName + ":" + content.tableComments
                                             + "</div>";
                                     });
                                     $(".ui.dropdown .menu").html(html);
                                     $('.ui.dropdown').dropdown('clear');
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

    $('.tabular.menu #get_business_tables').tab('change tab', 'getBusinessTables');
    //阻止form提交
    $('#next_step_form').submit(function () {
        return false;
    });

    $('#next_step').click(function () {
        $('#first_step_form').addClass('loading');
        let tableNameAndComments = $('#phy_tb_name').dropdown('get value').split(":");
        let tableName = tableNameAndComments[0];
        let tableComments = tableNameAndComments[1];
        get_tb_info(tableName, tableComments, false);
    });

    $('#save_gen_code').click(function () {
        require.ensure(["whatwg-fetch", "./genSchema.js"], function () {
            let func = require('./genSchema.js');
            func.save_and_gen('gen/genCodeByTable', $('#schema_form'), $('#tb_list'));
        });
    });

    $('#last_step').click(function () {
        last_step();
    });

    $('#saveTable').click(function () {
        require.ensure(["whatwg-fetch"], function () {
            $('#next_step_form').addClass('loading');
            fetch('gen/saveGenTable', {
                method: 'put',
                credentials: 'include',
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: $('#next_step_form').serialize(),
            }).then(function (response) {
                console.log(response);
                response.json().then(function (data) {
                    $('#next_step_form').removeClass('loading');
                    let isSuccess = data.result == 'SUCCESS' ? true : false;
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
                                     $('.tabular.menu .item')
                                         .tab('change tab', 'getPhysicalTables');
                                     last_step();
                                     $('#table_info').html('');
                                 }
                                 else {
                                     $('.tabular.menu .item')
                                         .tab('change tab', 'getBusinessTables');
                                     last_step();
                                     $('#table_info').html('');
                                 }
                             });

                    }
                    else {
                        swal('添加失败', '添加业务表失败' + data.errorMsg, 'error');
                    }
                })
            }).catch(function (err) {
                swal("错误", "服务器繁忙", "error");
            })
        });
    });

    $('#query_btn').click(function () {
        var tableName = $('#tableName').val();
        var tableComments = $('#tableComments').val();
        var className = $('#className').val();
        util.createPage('.pagination', 1, 5, 1, function (num) {
            require.ensure(["whatwg-fetch"], function () {
                var url = 'gen/getBusinessTables?page=' + num;
                if (tableName.trim() != '') {
                    url += '&tableName=' + tableName.trim();
                }
                if (tableComments.trim() != '') {
                    url += '&tableComments=' + tableComments.trim();
                }
                if (className.trim() != '') {
                    url += '&className=' + className.trim();
                }
                fetch(url, {
                    method: 'get',
                    credentials: 'include'
                }).then(function (response) {
                    console.log(response);
                    response.json().then(function (data) {
                        let html = '';
                        let isSuccess = data.result == 'SUCCESS';
                        let message = data.message;
                        if (isSuccess) {
                            $.each(message.list, function (index, content) {
                                html += "<tr>";
                                html +=
                                    "<td>"
                                    + content.tableName
                                    + "</td>";
                                html +=
                                    "<td>"
                                    + content.tableComments
                                    + "</td>";
                                html +=
                                    "<td>"
                                    + content.className
                                    + "</td>";
                                html +=
                                    "<td>"
                                    + "<a class=\"modify\" href=\"javascript:void(0)\" modify_name=\""
                                    + content.tableName + "\" modify_comments=\""
                                    + content.tableComments + "\">修改</a>"
                                    + "<a class=\"del\" href=\"javascript:void(0)\" del_id=\""
                                    + content.id + "\">删除</a>"
                                    + "<a class=\"generate\" href=\"javascript:void(0)\" gen_id=\""
                                    + content.id + "\">生成代码</a>"
                                    + "</td>";
                                html += "</tr>";
                            });
                            $('#business_tb').html(html);
                            $('.del').click(function () {
                                del_gentb($(this).attr('del_id'), $(this));
                            });
                            $('.generate').click(function () {
                                util.generate_code($(this).attr('gen_id'), $('#tb_list'),
                                                   $('#schema_form'));
                            });
                            $('.modify').click(function () {
                                modify_gentb($(this).attr('modify_name'),
                                             $(this).attr('modify_comments'));
                            });
                            $('.pagination').jqPaginator('option', {
                                totalPages: message.pages == 0 ? 1 : message.pages,
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
        });
    })
};
