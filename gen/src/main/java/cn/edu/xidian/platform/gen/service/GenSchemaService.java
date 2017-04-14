package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenSchemaDao;
import cn.edu.xidian.platform.gen.dao.IGenTableColumnDao;
import cn.edu.xidian.platform.gen.dao.IGenTableDao;
import cn.edu.xidian.platform.gen.entity.GenConfig;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenTable;
import cn.edu.xidian.platform.gen.entity.GenTableColumn;
import cn.edu.xidian.platform.gen.entity.GenTemplate;
import cn.edu.xidian.platform.gen.utils.GenUtils;

/**
 * Created by 费玥 on 2017-3-20.
 */
@Service
public class GenSchemaService {

    @Autowired
    private IGenSchemaDao iGenSchemaDao;

    @Autowired
    private IGenTableDao iGenTableDao;

    @Autowired
    private IGenTableColumnDao iGenTableColumnDao;


    public GenScheme get(GenScheme genScheme) {
        return iGenSchemaDao.get(genScheme);
    }

    @Transactional
    public void saveOrUpdate(GenScheme genScheme) {
        if (genScheme.getId() == 0L) {
            iGenSchemaDao.save(genScheme);
        } else {
            iGenSchemaDao.update(genScheme);
        }
    }

    @Transactional
    public String saveOrUpdateAndGen(GenScheme genScheme) {
        if (genScheme.getId() == 0L) {
            iGenSchemaDao.save(genScheme);
        } else {
            iGenSchemaDao.update(genScheme);
        }
        return generateCode(genScheme);
    }

    @Transactional
    public void delete(GenScheme genScheme) {
        iGenSchemaDao.delete(genScheme);
    }

    public List<GenScheme> findList(GenScheme genScheme) {
        return iGenSchemaDao.findList(genScheme);
    }

    private String generateCode(GenScheme genScheme){

        StringBuilder result = new StringBuilder();
        GenTable genTable = new GenTable();
        genTable.setId(genScheme.getGenTableId());
        // 查询主表及字段列
        genTable = iGenTableDao.get(genTable);
        List<GenTableColumn> genTableColumnList = iGenTableColumnDao.findListByTbId(genTable);
        for (GenTableColumn genTableColumn : genTableColumnList) {
            genTableColumn.setGenTable(genTable);
        }
        genTable.setColumnList(genTableColumnList);

        // 获取所有代码模板
        GenConfig config = GenUtils.getConfig();

        // 获取模板列表
        List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);
        List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, genScheme.getCategory(), true);

       /* // 如果有子表模板，则需要获取子表列表
        if (childTableTemplateList.size() > 0){
            GenTable parentTable = new GenTable();
            parentTable.setParentTable(genTable.getName());
            genTable.setChildList(genTableDao.findList(parentTable));
        }*/

        // 生成子表模板代码
        /*for (GenTable childTable : genTable.getChildList()){
            childTable.setParent(genTable);
            childTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(childTable.getId()))));
            genScheme.setGenTable(childTable);
            Map<String, Object> childTableModel = GenUtils.getDataModel(genScheme);
            for (GenTemplate tpl : childTableTemplateList){
                result.append(GenUtils.generateToFile(tpl, childTableModel, genScheme.getReplaceFile()));
            }
        }*/

        // 生成主表模板代码
        Map<String, Object> model = GenUtils.getDataModel(genScheme,genTable);
        for (GenTemplate tpl : templateList){
            result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile()), "\\"));
        }
        return result.toString();
    }
}
