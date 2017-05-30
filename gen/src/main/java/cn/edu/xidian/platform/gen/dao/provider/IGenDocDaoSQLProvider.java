package cn.edu.xidian.platform.gen.dao.provider;

import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.entity.GenDoc;

/**
 * Created by 李婧 on 2017/4/6.
 */
public class IGenDocDaoSQLProvider {

    public String findList(GenDoc genDoc) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* FROM gen_doc a WHERE 1=1 ");
        if (StringUtils.isNotEmpty(genDoc.getDocName())) {
            sql.append("AND a.doc_name LIKE concat('%',#{docName},'%') ");
        }
        return sql.toString();
    }
}
