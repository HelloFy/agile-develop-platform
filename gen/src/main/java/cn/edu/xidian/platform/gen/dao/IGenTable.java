package cn.edu.xidian.platform.gen.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import cn.edu.xidian.platform.gen.entity.GenTable;

/**
 * Created by 费玥 on 2017-3-17.
 */
@Mapper
public interface IGenTable {

    @Select("SELECT t.table_name AS tableName,t.TABLE_COMMENT AS tableComments \n" +
            "FROM information_schema.`TABLES` t \n" +
            "WHERE t.TABLE_SCHEMA = (select database())\n" +
            " ORDER BY t.TABLE_NAME")
    List<GenTable> findAllTableList();

}
