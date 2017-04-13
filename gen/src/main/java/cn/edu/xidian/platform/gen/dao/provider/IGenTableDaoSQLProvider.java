package cn.edu.xidian.platform.gen.dao.provider;

import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.entity.GenTable;

/**
 * Created by 费玥 on 2017/3/19.
 */
public class IGenTableDaoSQLProvider {

    public String findList(GenTable genTable){
        StringBuilder sql =  new StringBuilder();
        sql.append("SELECT a.* FROM gen_table a WHERE 1=1 ");
        if (!StringUtils.isEmpty(genTable.getTableName())){
            sql.append("AND a.table_name LIKE concat('%',#{tableName},'%') ");
        }
        if (!StringUtils.isEmpty(genTable.getTableComments())){
            sql.append("AND a.table_comments LIKE concat('%',#{tableComments},'%') ");
        }
        if (!StringUtils.isEmpty(genTable.getClassName())){
            sql.append("AND a.class_name LIKE concat('%',#{className},'%')");
        }
        return sql.toString();
    }
}
