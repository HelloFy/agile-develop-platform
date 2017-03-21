/**
 * Created by 费玥 on 2017-3-21.
 */
require('../jqPagination/jqPaginator.js');

export function createPage(selector,totalPages,visiblePages,currentPage,func) {
    $(selector).jqPaginator(
        {
            totalPages: totalPages,
            visiblePages: visiblePages,
            currentPage: currentPage,
            first: '<li class="icon item"><a href="javascript:void(0);"><i class="left chevron icon"></i><i class="left chevron icon"></i><\/a><\/li>',
            prev: '<li class="icon item"><a href="javascript:void(0);"><i class="left chevron icon"></i><\/a><\/li>',
            next: '<li class="icon item"><a href="javascript:void(0);"><i class="right chevron icon"></i><\/a><\/li>',
            last: '<li class="icon item"><a href="javascript:void(0);"><i class="right chevron icon"></i><i class="right chevron icon"></i><\/a><\/li>',
            page: '<li class="item"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            onPageChange: func
        });
}