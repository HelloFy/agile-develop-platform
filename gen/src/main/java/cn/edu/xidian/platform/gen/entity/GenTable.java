package cn.edu.xidian.platform.gen.entity;


import java.io.Serializable;

/**
 * Created by 费玥 on 2017-3-17.
 */

public class GenTable implements Serializable {

    private String tableName;

    private String tableComments;

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
}
