// require('../../css/gen/gen.css');
// require('../jqPagination/jqPaginator.js');
let util = require('../utils/util.js');
let totalPages = 1;

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
                     require.ensure(["whatwg-fetch"],function () {
                         fetch('gen/getBusinessTables?page=1',{
                             method:'get',
                             credentials: 'include'
                         }).then(function (response) {
                             response.json().then(function (data) {
                                 let isSuccess = data.result == 'SUCCESS' ? true : false;
                                 if (isSuccess) {
                                     let message = data.message;
                                     totalPages = message.pages == 0 ? 1 :message.pages;
                                     console.log(totalPages);
                                     let html = '';
                                     $.each(message.list, function (index, content) {
                                         html += "<tr>";
                                         html += "<td>" + content.tableName + "</td>";
                                         html += "<td>" + content.tableComments + "</td>";
                                         html += "<td>" + content.className + "</td>";
                                         html += "<td><a class=\"\" href=\"\">修改</a><a >删除</a></td>";
                                         html += "</tr>";
                                     });
                                     $('#business_tb').html(html);
                                     util.createPage('.pagination',totalPages,5,1,function (num, type) {
                                         if(num == 1){
                                             return;
                                         }
                                         require.ensure(["whatwg-fetch"],function () {
                                             fetch('gen/getBusinessTables?page='+num,{
                                                 method:'get',
                                                 credentials: 'include'
                                             }).then(function (response) {
                                                 console.log(response);
                                                 response.json().then(function (data) {
                                                     let html = '';
                                                     $.each(message.list,
                                                            function (index, content) {
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
                                                                    "<td><a class=\"\" href=\"\">修改</a><a >删除</a></td>";
                                                                html += "</tr>";
                                                            });
                                                     $('#business_tb').html(html);
                                                 })
                                             }).catch(function (err) {
                                                 swal("错误","服务器繁忙","error");
                                             })
                                         })                                         });
/*                                     $('.pagination').jqPaginator(
                                         {
                                             totalPages: totalPages,
                                             visiblePages: 5,
                                             currentPage: 1,
                                             first: '<li class="icon item"><a href="javascript:void(0);"><i class="left chevron icon"></i><i class="left chevron icon"></i><\/a><\/li>',
                                             prev: '<li class="icon item"><a href="javascript:void(0);"><i class="left chevron icon"></i><\/a><\/li>',
                                             next: '<li class="icon item"><a href="javascript:void(0);"><i class="right chevron icon"></i><\/a><\/li>',
                                             last: '<li class="icon item"><a href="javascript:void(0);"><i class="right chevron icon"></i><i class="right chevron icon"></i><\/a><\/li>',
                                             page: '<li class="item"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                                             onPageChange: function (num, type) {
                                                 if(num == 1){
                                                     return;
                                                 }
                                                 require.ensure(["whatwg-fetch"],function () {
                                                     fetch('gen/getBusinessTables?page='+num,{
                                                         method:'get',
                                                         credentials: 'include'
                                                     }).then(function (response) {
                                                         console.log(response);
                                                         response.json().then(function (data) {
                                                             let html = '';
                                                             $.each(message.list,
                                                                    function (index, content) {
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
                                                                            "<td><a class=\"\" href=\"\">修改</a><a >删除</a></td>";
                                                                        html += "</tr>";
                                                                    });
                                                             $('#business_tb').html(html);
                                                         })
                                                     }).catch(function (err) {
                                                         swal("错误","服务器繁忙","error");
                                                     })
                                                 })                                         }
                                         });*/
                                 }
                                 else {
                                     swal(data.message, data.message, "error");
                                 }
                                 return;
                             })
                         }).catch(function (err) {
                             swal("错误","服务器繁忙","error");
                             return;
                         })
                     });
                 }
             });
    $('.tabular.menu #get_phy_tables')
        .tab({
                 onLoad: function () {
                     if($('#final_step').hasClass('active')){
                         return ;
                     }
                     require.ensure(["whatwg-fetch"],function () {
                         fetch('gen/getPhysicalTables',{
                             method:'get',
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
                                             "<div class='item' data-value=\"" + content.tableName + "\">"
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
                             swal("错误","服务器繁忙","error");
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
        let tableName =  $('#phy_tb_name').dropdown('get value');
        require.ensure(["whatwg-fetch"],function () {
            fetch('gen/getTableInfo?tableName='+tableName,{
                method:'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.json().then(function (data) {
                    let isSuccess = data.result;
                    if (isSuccess == 'SUCCESS') {
                        next_step();
                        let message = data.message;
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
                swal("错误","服务器繁忙","error");
                $('#first_step_form').removeClass('loading');
            })
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
    })
};
