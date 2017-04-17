/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.dao.provider;

import cn.edu.xidian.platform.gen.entity.GenUmlClassDiagram;


/**
 * 阿斯达斯的 SQLProvider
 * @author 费玥
 * @version 2017-04-17
 */

public class IGenUmlClassDiagramDaoSQLProvider {

    public String findList(GenUmlClassDiagram genUmlClassDiagram) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* FROM gen_uml_class_diagram a WHERE 1=1 ");
        if (genUmlClassDiagram.getClassDiagramName() != null) {
            sql.append(" AND a.class_diagram_name LIKE concat('%',#{classDiagramName},'%') ");
        }
        return sql.toString();
    }
}