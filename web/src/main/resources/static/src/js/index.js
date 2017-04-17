require('./semantic/semantic.min.js');
require('../template/index.html');
require('../css/index.css');

$(function () {
    $('.ui.accordion')
        .accordion();

    $('#gen_table_item').click(function () {
        require.ensure(["whatwg-fetch","./gen/genTable.js","../css/gen/gen.css"],function () {
            var func = require('./gen/genTable.js');
            fetch('gen/tableForm',{
                method:'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.text().then(function (data) {
                    console.log(data);
                    $('#main_frame').html(data);
                    func.load();
                })
            }).catch(function (err) {
                swal("错误","服务器繁忙","error");
            })
        })

        });
    $('#gen_schema_item').click(function () {
        require.ensure(["whatwg-fetch", "./gen/genSchema.js","../css/gen/gen.css"], function () {
            var func = require('./gen/genSchema.js');
            fetch('gen/schemaForm',{
                method:'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.text().then(function (data) {
                    console.log(data);
                    $('#main_frame').html(data);
                    func.load();
                })
            }).catch(function (err) {
                swal("错误","服务器繁忙","error");
            })
        })
    });
    $('#list_doc_item').click(function () {
        require.ensure(["whatwg-fetch", "./gen/docList.js"], function () {
            var func = require('./gen/docList.js');
            fetch('gen/docTplView', {
                method: 'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.text().then(function (data) {
                    console.log(data);
                    $('#main_frame').html(data);
                    func.load();
                })
            }).catch(function (err) {
                swal("错误", "服务器繁忙", "error");
            })
        })
    });
    $('#code_tpl_item').click(function () {
        require.ensure(["whatwg-fetch", "./gen/genCodeTemplate.js"], function () {
            var func = require('./gen/genCodeTemplate.js');
            fetch('gen/genCodeTemplateForm', {
                method: 'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.text().then(function (data) {
                    console.log(data);
                    $('#main_frame').html(data);
                    func.load();
                })
            }).catch(function (err) {
                swal("错误", "服务器繁忙", "error");
            })
        })
    });
    $('#uml_gen_item').click(function () {
        require.ensure(["whatwg-fetch", "./gen/genUML.js"], function () {
            var func = require('./gen/genUML.js');
            fetch('gen/genUmlClassDiagramForm', {
                method: 'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.text().then(function (data) {
                    console.log(data);
                    $('#main_frame').html(data);
                    func.load();
                })
            }).catch(function (err) {
                swal("错误", "服务器繁忙", "error");
            })
        })
    })

});


