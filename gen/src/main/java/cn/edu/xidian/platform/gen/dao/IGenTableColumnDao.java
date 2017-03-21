package cn.edu.xidian.platform.gen.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import cn.edu.xidian.platform.gen.entity.GenTable;
import cn.edu.xidian.platform.gen.entity.GenTableColumn;

/**
 * Created by 费玥 on 2017/3/19.
 */
@Mapper
public interface IGenTableColumnDao {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO gen_table_column(\n" +
            "gen_table_id, \n" +
            "name, \n" +
            "comments, \n" +
            "jdbc_type, \n" +
            "java_type, \n" +
            "java_field, \n" +
            "is_pk, \n" +
            "is_null, \n" +
            "is_insert, \n" +
            "is_edit, \n" +
            "is_list, \n" +
            "is_query, \n" +
            "query_type, \n" +
            "show_type, \n" +
            "dict_type \n" +
            "\t\t) VALUES (\n" +
            "#{genTableId}, \n" +
            "#{name}, \n" +
            "#{comments}, \n" +
            "#{jdbcType}, \n" +
            "#{javaType}, \n" +
            "#{javaField}, \n" +
            "#{isPk}, \n" +
            "#{isNull}, \n" +
            "#{isInsert}, \n" +
            "#{isEdit}, \n" +
            "#{isList}, \n" +
            "#{isQuery}, \n" +
            "#{queryType}, \n" +
            "#{showType}, \n" +
            "#{dictType}) \n")
    long save(GenTableColumn genTableColumn);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Update("UPDATE gen_table_column SET \n" +
            "comments = #{comments}, \n" +
            "jdbc_type = #{jdbcType}, \n" +
            "java_type = #{javaType}, \n" +
            "java_field = #{javaField}, \n" +
            "is_pk = #{isPk}, \n" +
            "is_null = #{isNull}, \n" +
            "is_insert = #{isInsert}, \n" +
            "is_edit = #{isEdit}, \n" +
            "is_list = #{isList}, \n" +
            "is_query = #{isQuery}, \n" +
            "query_type = #{queryType}, \n" +
            "show_type = #{showType}, \n" +
            "dict_type = #{dictType} \n" +
            "\t\tWHERE id = #{id}")
    long update(GenTableColumn genTableColumn);

    @Delete("DELETE FROM gen_table_column \n" +
            "WHERE gen_table_id = #{id}")
    void delByGenTableId(GenTable genTable);

    @Select("SELECT a.* FROM gen_table_column a WHERE gen_table_id = #{id}")
    List<GenTableColumn> findListByTbId(GenTable genTable);

}