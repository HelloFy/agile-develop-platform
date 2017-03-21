package cn.edu.xidian.platform.commons.entity;

/**
 * Created by 费玥 on 2017/3/19.
 */
public class Page<T> {

    private int pageSize = 10;
    private int page;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
