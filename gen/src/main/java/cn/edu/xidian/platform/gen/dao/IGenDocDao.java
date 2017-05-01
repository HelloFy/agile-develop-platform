/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import cn.edu.xidian.platform.gen.dao.provider.IGenDocDaoSQLProvider;
import cn.edu.xidian.platform.gen.entity.GenDoc;


/**
 * 文档模版DAO接口
 * @author 费玥
 * @version 2017-04-06
 */
@Mapper
public interface IGenDocDao {

    @Select("SELECT \n" +
            "a.*\n" +
            "FROM gen_doc a\n" +
            "WHERE a.id = #{id}")
    GenDoc get(GenDoc genDoc);

    @Select("SELECT a.*\n" +
            "FROM gen_doc a\n")
    List<GenDoc> findAllList();


    @SelectProvider(type = IGenDocDaoSQLProvider.class, method = "findList")
    List<GenDoc> findList(GenDoc genDoc);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO gen_doc( \n" +
            " doc_name,\n" +
            " doc_size,\n" +
            " upload_date,\n" +
            " doc_path, \n" +
            " real_name \n" +
            " ) VALUES ( \n" +
            "#{docName},\n" +
            "#{docSize},\n" +
            "#{uploadDate},\n" +
            "#{docPath},\n" +
            "#{realName}\n" +
            ")")
    long save(GenDoc genDoc);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Update("UPDATE gen_doc SET \n" +
            " doc_name = #{docName},\n" +
            " doc_size = #{docSize},\n" +
            " upload_date = #{uploadDate},\n" +
            " doc_path = #{docPath} \n" +
            " WHERE id = #{id}")
    long update(GenDoc genDoc);


    @Delete("DELETE FROM gen_doc\n" +
            "WHERE id = #{id}")
    void delete(GenDoc genDoc);
}