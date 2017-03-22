package cn.edu.xidian.platform.gen.entity;


import com.google.common.collect.Sets;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.edu.xidian.platform.commons.entity.Page;
import cn.edu.xidian.platform.commons.utils.StringUtils;

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

    /**
     * 获取导入依赖包字符串
     */
    @JSONField(serialize = false)
    public List<String> getImportList() {
        Set<String> importList = Sets.newHashSet(); // 引用列表
        for (GenTableColumn column : getColumnList()) {
            if (column.getIsNotBaseField() || ("1".equals(column.getIsQuery()) && "between".equals(column.getQueryType()))) {
                // 导入类型依赖包， 如果类型中包含“.”，则需要导入引用。
                if (StringUtils.indexOf(column.getJavaType(), ".") != -1) {
                    importList.add(column.getJavaType());
                }
            }
        }
        return new ArrayList<>(importList);
    }
}
