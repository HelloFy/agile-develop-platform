require('../semantic/semantic.min.js');
require('../jqPagination/jqPaginator.js');
require('../../template/gen/genSchemaForm.html');

$(function () {
    $('.tabular.menu #list_schema').tab(
        {
            onLoad: function () {
            }
        });
    $('.tabular.menu #add_schema').tab(
        {
            onLoad: function () {
            }
        });
    $('.ui.dropdown').dropdown();

    $('.pagination').jqPaginator(
        {
            totalPages: 10,
            visiblePages: 5,
            currentPage: 1,
            first: '<li class="icon item"><a href="javascript:void(0);"><i class="left chevron icon"></i><i class="left chevron icon"></i><\/a><\/li>',
            prev: '<li class="icon item"><a href="javascript:void(0);"><i class="left chevron icon"></i><\/a><\/li>',
            next: '<li class="icon item"><a href="javascript:void(0);"><i class="right chevron icon"></i><\/a><\/li>',
            last: '<li class="icon item"><a href="javascript:void(0);"><i class="right chevron icon"></i><i class="right chevron icon"></i><\/a><\/li>',
            page: '<li class="item"><a href="javascript:void(0);">{{page}}<\/a><\/li>'
        });

});