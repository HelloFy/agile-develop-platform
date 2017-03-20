require('./semantic/semantic.min.js')
require('../template/index.html')
require('../css/index.css')

$('.ui.accordion')
    .accordion();

$(function () {
    $('#gen_table_item').click(function () {
        require.ensure(["whatwg-fetch","./gen/genTable.js"],function () {
            hehe();
            fetch('gen/tableForm',{
                method:'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.text().then(function (data) {
                    console.log(data);
                    $('#main_frame').html(data);
                })
            }).catch(function (err) {
                swal("错误","服务器繁忙","error");
            })
        })

        });
    $('#gen_schema_item').click(function () {
        require.ensure(["whatwg-fetch"],function () {
            fetch('gen/schemaForm',{
                method:'get',
                credentials: 'include'
            }).then(function (response) {
                console.log(response);
                response.text().then(function (data) {
                    console.log(data);
                    $('#main_frame').html(data);
                })
            }).catch(function (err) {
                swal("错误","服务器繁忙","error");
            })
        })
    })
    // swal("Good job!", "You clicked the button!", "success")
});


