package cn.edu.xidian.platform.gen.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import cn.edu.xidian.platform.gen.entity.GenTable;
import cn.edu.xidian.platform.gen.entity.GenTableColumn;

/**
 * Created by 李婧 on 2017/3/18.
 */
@Mapper
public interface IGenDataBaseDictDao {

    @Select("SELECT t.COLUMN_NAME AS name, (CASE WHEN t.IS_NULLABLE = 'YES' THEN '1' ELSE '0' END) AS isNull,\n" +
            " t.COLUMN_COMMENT AS comments,t.COLUMN_TYPE AS jdbcType \n" +
            "FROM information_schema.`COLUMNS` t \n" +
            "WHERE t.TABLE_SCHEMA = (select database())\n" +
            "AND t.TABLE_NAME = upper(#{tableName})\n" +
            "ORDER BY t.ORDINAL_POSITION")
    List<GenTableColumn> findTableColumns(GenTable genTable);


    @Select("SELECT t.table_name AS tableName,t.TABLE_COMMENT AS tableComments \n" +
            "FROM information_schema.`TABLES` t \n" +
            "WHERE t.TABLE_SCHEMA = (select database())\n" +
            " ORDER BY t.TABLE_NAME")
    List<GenTable> findAllTableList();



    @Select("SELECT t.table_name AS tableName,t.TABLE_COMMENT AS tableComments \n" +
            "FROM information_schema.`TABLES` t \n" +
            "WHERE t.TABLE_SCHEMA = (select database())\n" +
            "AND t.TABLE_NAME = upper(#{tableName})\n")
    List<GenTable> findTableByName(GenTable genTable);


    @Select("SELECT lower(au.COLUMN_NAME) AS columnName \n" +
            "FROM information_schema.`COLUMNS` au\n" +
            "WHERE au.TABLE_SCHEMA = (select database()) \n" +
            "AND au.COLUMN_KEY='PRI' AND au.TABLE_NAME = (#{tableName})")
    List<String> findPkList(GenTable genTable);



}
