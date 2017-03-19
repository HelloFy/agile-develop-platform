package cn.edu.xidian.platform.commons.entity;

import java.util.List;

/**
 * Created by 费玥 on 2017/3/19.
 */
public class Page<T> {

    private int pageSize = 10;
    private int index ;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
