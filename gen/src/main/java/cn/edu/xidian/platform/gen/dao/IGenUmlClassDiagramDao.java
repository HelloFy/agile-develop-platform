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

import cn.edu.xidian.platform.gen.dao.provider.IGenUmlClassDiagramDaoSQLProvider;
import cn.edu.xidian.platform.gen.entity.GenUmlClassDiagram;


/**
 * 阿斯达斯的DAO接口
 * @author 费玥
 * @version 2017-04-17
 */
@Mapper
public interface IGenUmlClassDiagramDao {

    @Select("SELECT \n" +
            "a.id," +
            "a.class_diagram_name," +
            "a.format_json_str," +
            "a.comments,\n" +
            "a.real_name \n" +
            "FROM gen_uml_class_diagram a\n" +
            "WHERE a.id = #{id}")
    GenUmlClassDiagram get(GenUmlClassDiagram genUmlClassDiagram);

    @Select("SELECT \n" +
            "a.id," +
            "a.class_diagram_name," +
            "a.comments \n" +
            "FROM gen_uml_class_diagram a\n")
    List<GenUmlClassDiagram> findAllList();


    @SelectProvider(type = IGenUmlClassDiagramDaoSQLProvider.class, method = "findList")
    List<GenUmlClassDiagram> findList(GenUmlClassDiagram genUmlClassDiagram);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO gen_uml_class_diagram( \n" +
            " class_diagram_name,\n" +
            " format_json_str,\n" +
            " comments,\n" +
            " path, \n" +
            "real_name \n" +
            " ) VALUES ( \n" +
            "#{classDiagramName},\n" +
            "#{formatJsonStr},\n" +
            "#{comments},\n" +
            "#{path}, \n" +
            "#{realName}\n " +
            ")")
    long save(GenUmlClassDiagram genUmlClassDiagram);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Update("UPDATE gen_uml_class_diagram SET \n" +
            " class_diagram_name = #{classDiagramName},\n" +
            " comments = #{comments},\n" +
            " path = #{path} \n" +
            " WHERE id = #{id}")
    long update(GenUmlClassDiagram genUmlClassDiagram);


    @Delete("DELETE FROM gen_uml_class_diagram\n" +
            "WHERE id = #{id}")
    void delete(GenUmlClassDiagram genUmlClassDiagram);

}