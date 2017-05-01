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

import cn.edu.xidian.platform.gen.dao.provider.IGenCodeTemplateDaoSQLProvider;
import cn.edu.xidian.platform.gen.entity.GenCodeTemplate;


/**
 * 代码模版DAO接口
 * @author 费玥
 * @version 2017-04-18
 */
@Mapper
public interface IGenCodeTemplateDao {

    @Select("SELECT \n" +
            "a.*\n" +
            "FROM gen_code_template a\n" +
            "WHERE a.id = #{id}")
    GenCodeTemplate get(GenCodeTemplate genCodeTemplate);

    @Select("SELECT a.*\n" +
            "FROM gen_code_template a\n")
    List<GenCodeTemplate> findAllList();


    @SelectProvider(type = IGenCodeTemplateDaoSQLProvider.class, method = "findList")
    List<GenCodeTemplate> findList(GenCodeTemplate genCodeTemplate);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO gen_code_template( \n" +
            " name,\n" +
            " function,\n" +
            " comments,\n" +
            " path,\n" +
            " real_name\n" +
            " ) VALUES ( \n" +
            "#{name},\n" +
            "#{function},\n" +
            "#{comments},\n" +
            "#{path},\n" +
            "#{realName} \n" +
            ")")
    long save(GenCodeTemplate genCodeTemplate);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Update("UPDATE gen_code_template SET \n" +
            " name = #{name},\n" +
            " function = #{function},\n" +
            " comments = #{comments} \n" +
            " WHERE id = #{id}")
    long update(GenCodeTemplate genCodeTemplate);


    @Delete("DELETE FROM gen_code_template\n" +
            "WHERE id = #{id}")
    void delete(GenCodeTemplate genCodeTemplate);

}