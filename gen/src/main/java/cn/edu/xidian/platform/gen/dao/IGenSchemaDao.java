package cn.edu.xidian.platform.gen.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import cn.edu.xidian.platform.gen.dao.provider.IGenSchemeDaoSQLProvider;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenTable;

/**
 * Created by 费玥 on 2017-3-20.
 */
@Mapper
public interface IGenSchemaDao {

    @Select("SELECT a.* FROM gen_scheme WHERE id =#{id}")
    GenScheme get(GenScheme genScheme);

    @SelectProvider(type = IGenSchemeDaoSQLProvider.class, method = "findList")
    List<GenScheme> findList(GenScheme genScheme);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO gen_scheme(\n" +
            "name, \n" +
            "category, \n" +
            "package_name, \n" +
            "module_name, \n" +
            "sub_module_name, \n" +
            "function_name, \n" +
            "function_name_simple, \n" +
            "function_author, \n" +
            "gen_table_id \n" +
            ") VALUES (\n" +
            "#{name}, \n" +
            "#{category},  \n" +
            "#{packageName},  \n" +
            "#{moduleName},  \n" +
            "#{subModuleName},  \n" +
            "#{functionName},  \n" +
            "#{functionNameSimple},  \n" +
            "#{functionAuthor},  \n" +
            "#{genTableId}  \n" +
            "\t\t)")
    long save(GenScheme genScheme);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Update("UPDATE gen_scheme SET \n" +
            "name = #{name}, \n" +
            "category = #{category},  \n" +
            "package_name = #{packageName},  \n" +
            "module_name = #{moduleName},  \n" +
            "sub_module_name = #{subModuleName},  \n" +
            "function_name = #{functionName},  \n" +
            "function_name_simple = #{functionNameSimple},  \n" +
            "function_author = #{functionAuthor},  \n" +
            "gen_table_id = #{genTableId}  \n" +
            "\t\tWHERE id = #{id}")
    long update(GenScheme genScheme);

    @Delete("DELETE FROM gen_scheme WHERE id = #{id}")
    void delete(GenScheme genScheme);

    @Delete("DELETE FROM gen_scheme WHERE gen_table_id = #{id}")
    void deleteByGenTable(GenTable genTable);
}
