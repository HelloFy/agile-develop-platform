package cn.edu.xidian.platform.gen.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import cn.edu.xidian.platform.gen.dao.provider.IGenTableDaoSQLProvider;
import cn.edu.xidian.platform.gen.entity.GenTable;

/**
 * Created by 费玥 on 2017-3-17.
 */
@Mapper
public interface IGenTableDao {


    @Select("SELECT \n" +
            "a.*\n"+
            "FROM gen_table a\n" +
            "WHERE a.id = #{id}")
    GenTable get(GenTable genTable);

    @Select("SELECT a.*\n" +
            "FROM gen_table a\n"+
            "ORDER BY a.table_name ASC")
    List<GenTable> findAllList();


    @SelectProvider(type = IGenTableDaoSQLProvider.class, method = "findList")
    List<GenTable> findList(GenTable table);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO gen_table(\n" +
            "table_name, \n" +
            "table_comments, \n" +
            "class_name \n" +
            ") VALUES (\n" +
            "#{tableName}, \n" +
            "#{tableComments}, \n" +
            "#{className}) \n")
    long save(GenTable genTable);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Update("UPDATE gen_table SET \n" +
            "table_name = #{tableName},  \n" +
            "comments = #{tableComments}, \n" +
            "class_name = #{className}, \n" +
            "WHERE id = #{id}")
    long update(GenTable genTable);


    @Delete("DELETE FROM gen_table\n" +
            "WHERE id = #{id}")
    void delete(@Param("id") long id);


}
