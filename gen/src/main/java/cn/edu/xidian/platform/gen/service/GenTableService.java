package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenDataBaseDictDao;
import cn.edu.xidian.platform.gen.dao.IGenSchemaDao;
import cn.edu.xidian.platform.gen.dao.IGenTableColumnDao;
import cn.edu.xidian.platform.gen.dao.IGenTableDao;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenTable;
import cn.edu.xidian.platform.gen.entity.GenTableColumn;
import cn.edu.xidian.platform.gen.utils.GenUtils;

/**
 * Created by 李婧 on 2017-3-17.
 */
@Service
public class GenTableService {

    @Autowired
    private IGenTableDao iGenTableDao;

    @Autowired
    private IGenDataBaseDictDao iGenDataBaseDictDao;

    @Autowired
    private IGenTableColumnDao iGenTableColumnDao;

    @Autowired
    private IGenSchemaDao iGenSchemaDao;

    @Autowired
    private GenTableSchemaService genTableSchemaService;

    public GenTable get(GenTable genTable) {
        return iGenTableDao.get(genTable);
    }

    public GenTable findUnique(GenTable genTable) {
        GenTable tb = iGenTableDao.findUnique(genTable);
        tb.setColumnList(iGenTableColumnDao.findListByTbId(tb));
        return tb;
    }

    public List<GenTable> findAllTableList() {
        return iGenDataBaseDictDao.findAllTableList();
    }

    public List<GenTable> findAllBusinessTableList() {
        return iGenTableDao.findAllList();
    }

    public List<GenTable> findPagedBusinessTableList(GenTable genTable){
        return iGenTableDao.findList(genTable);
    }

    public boolean checkTableName(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return false;
        }
        GenTable genTable = new GenTable();
        genTable.setTableName(tableName);
        List<GenTable> list = iGenTableDao.findList(genTable);
        return list.size() == 0;
    }

    public GenTable getTableFromDB(GenTable genTable) {
        genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getTableName()));
        // 添加新列
        List<GenTableColumn> columnList = iGenDataBaseDictDao.findTableColumns(genTable);
        genTable.setColumnList(new ArrayList<>());
        for (GenTableColumn column : columnList) {
            boolean b = false;
            for (GenTableColumn e : genTable.getColumnList()) {
                if (e.getName().equals(column.getName())) {
                    b = true;
                }
            }
            if (!b) {
                genTable.getColumnList().add(column);
            }
        }

        genTable.setPkList(iGenDataBaseDictDao.findPkList(genTable));

        // 初始化列属性字段
        GenUtils.initColumnField(genTable);

        return genTable;

    }

    @Transactional
    public int saveOrUpdate(GenTable genTable) {
        int save = 1994;
        if (genTable.getId() != 0L) {
            iGenTableDao.update(genTable);
            save = 1995;
        } else {
            iGenTableDao.save(genTable);
            GenScheme defaultGenScheme = genTableSchemaService.initDefaultGenScheme(genTable.getTableName());
            defaultGenScheme.setRefId(genTable.getId());
            iGenSchemaDao.save(defaultGenScheme);
        }
        List<GenTableColumn> columns = genTable.getColumnList();
        for (GenTableColumn genTableColumn : columns) {
            genTableColumn.setQueryType(StringUtils.lowerCase(genTableColumn.getQueryType()));
            if (genTableColumn.getId() != 0L) {
                iGenTableColumnDao.update(genTableColumn);
            } else {
                genTableColumn.setGenTableId(genTable.getId());
                iGenTableColumnDao.save(genTableColumn);
            }
        }
        return save;
    }

    public void delete(GenTable genTable) {
        iGenTableDao.delete(genTable);
        iGenTableColumnDao.delByGenTable(genTable);
        iGenSchemaDao.deleteByRefIdAndRefType(genTable.getId(), GenScheme.GEN_TABLE_TYPE);
    }
}

