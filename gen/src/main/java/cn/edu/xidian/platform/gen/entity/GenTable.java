package cn.edu.xidian.platform.gen.entity;


import java.io.Serializable;
import java.util.List;

import cn.edu.xidian.platform.commons.entity.Page;

/**
 * Created by 费玥 on 2017-3-17.
 */

public class GenTable extends Page<GenTable> implements Serializable {

    private long id;

    private String tableName;

    private String tableComments;

    private String className;		// 实体类名称

    private List<GenTableColumn> columnList;

    private List<String> pkList; // 当前表主键列表


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComments() {
        return tableComments;
    }

    public void setTableComments(String tableComments) {
        this.tableComments = tableComments;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<GenTableColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<GenTableColumn> columnList) {
        this.columnList = columnList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getPkList() {
        return pkList;
    }

    public void setPkList(List<String> pkList) {
        this.pkList = pkList;
    }
}
