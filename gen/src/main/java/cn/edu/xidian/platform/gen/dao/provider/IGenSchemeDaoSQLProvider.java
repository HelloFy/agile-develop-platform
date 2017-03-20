package cn.edu.xidian.platform.gen.dao.provider;

import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.entity.GenScheme;

/**
 * Created by 费玥 on 2017/3/20.
 */
public class IGenSchemeDaoSQLProvider {

    public String findList(GenScheme genScheme) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* FROM gen_scheme WHERE 1=1 ");
        if (StringUtils.isNotEmpty(genScheme.getName())) {
            sql.append("AND a.name = #{name}");
        }
        return sql.toString();
    }
}
