require('./semantic/semantic.min.js')
require('../template/index.html')
require('../css/index.css')

$('.ui.accordion')
    .accordion();

$(function () {
    $('#gen_table_item').click(function () {
        console.log('debug');
        $.get('gen/tableForm',function(data){
            $("#main_frame").html(data);
        })
    });
    // swal("Good job!", "You clicked the button!", "success")
});


