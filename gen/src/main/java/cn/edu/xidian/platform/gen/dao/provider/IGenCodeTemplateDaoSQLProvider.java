/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.dao.provider;

import cn.edu.xidian.platform.gen.entity.GenCodeTemplate;


/**
 * 代码模版 SQLProvider
 * @author 李婧
 * @version 2017-04-18
 */

public class IGenCodeTemplateDaoSQLProvider {

    public String findList(GenCodeTemplate genCodeTemplate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* FROM gen_code_template a WHERE 1=1 ");
        if (genCodeTemplate.getName() != null) {
            sql.append(" AND a.name LIKE concat('%',#{name},'%') ");
        }
        if (genCodeTemplate.getFunction() != null) {
            sql.append(" AND a.function LIKE concat('%',#{function},'%') ");
        }
        return sql.toString();
    }
}