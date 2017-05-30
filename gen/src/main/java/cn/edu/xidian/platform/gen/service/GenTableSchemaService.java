package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import cn.edu.xidian.platform.commons.config.Global;
import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenTableColumnDao;
import cn.edu.xidian.platform.gen.dao.IGenTableDao;
import cn.edu.xidian.platform.gen.entity.GenConfig;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenTable;
import cn.edu.xidian.platform.gen.entity.GenTableColumn;
import cn.edu.xidian.platform.gen.entity.GenTemplate;
import cn.edu.xidian.platform.gen.utils.GenUtils;

/**
 * Created by 李婧 on 2017-3-20.
 */
@Service
public class GenTableSchemaService extends BaseGenSchemaSevice {

    @Autowired
    private IGenTableDao iGenTableDao;

    @Autowired
    private IGenTableColumnDao iGenTableColumnDao;

    @Transactional
    @Override
    public String saveOrUpdateAndGen(GenScheme genScheme) {
        saveOrUpdate(genScheme);
        return generateCode(genScheme);
    }

    @Override
    public String generateCode(GenScheme genScheme) {

        StringBuilder result = new StringBuilder();
        GenTable genTable = new GenTable();
        genTable.setId(genScheme.getRefId());
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

        // 生成主表模板代码
        Map<String, Object> model = GenUtils.getDataModel(genScheme,genTable);
        for (GenTemplate tpl : templateList){
            result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile()), "\\"));
        }
        return result.toString();
    }

    @Override
    public GenScheme initDefaultGenScheme(String name) {
        GenScheme defaultGenScheme = new GenScheme();
        defaultGenScheme.setName(name);
        defaultGenScheme.setCategory("curd");
        defaultGenScheme.setPackageName("cn.edu.xidian.platform");
        defaultGenScheme.setModuleName("gen");
        defaultGenScheme.setFunctionAuthor(Global.getAuthorName());
        defaultGenScheme.setFunctionName("curd " + name);
        defaultGenScheme.setRefType(GenScheme.GEN_TABLE_TYPE);
        return defaultGenScheme;
    }
}
