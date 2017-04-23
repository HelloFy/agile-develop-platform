package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.edu.xidian.platform.commons.config.Global;
import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenUmlClassDiagramDao;
import cn.edu.xidian.platform.gen.entity.GenConfig;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenTemplate;
import cn.edu.xidian.platform.gen.entity.GenUmlClassDiagram;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLEnumeration;
import cn.edu.xidian.platform.gen.entity.uml.UMLFile;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLModel;
import cn.edu.xidian.platform.gen.entity.uml.UMLPackage;
import cn.edu.xidian.platform.gen.entity.uml.UMLProject;
import cn.edu.xidian.platform.gen.utils.GenUtils;
import cn.edu.xidian.platform.gen.utils.uml.parse.core.DefaultParse;

/**
 * @author 费玥
 * @since 2017/4/22 19:34
 */
@Service
public class GenUmlSchemaService extends BaseGenSchemaSevice implements IGenCodeService {

    @Autowired
    private IGenUmlClassDiagramDao iGenUmlClassDiagramDao;

    @Override
    public GenScheme initDefaultGenScheme(String name) {
        GenScheme defaultGenScheme = new GenScheme();
        defaultGenScheme.setName(name);
        defaultGenScheme.setCategory("uml");
        defaultGenScheme.setPackageName("cn.edu.xidian.platform");
        defaultGenScheme.setModuleName("gen");
        defaultGenScheme.setFunctionAuthor(Global.getAuthorName());
        defaultGenScheme.setFunctionName("uml " + name);

        return defaultGenScheme;
    }

    @Override
    public String generateCode(GenScheme genScheme) {
        GenUmlClassDiagram diagram = new GenUmlClassDiagram();
        diagram.setId(genScheme.getRefId());
        diagram = iGenUmlClassDiagramDao.get(diagram);
        String formatJsonStr = diagram.getFormatJsonStr();
        UMLFile umlFile = new UMLFile();
        umlFile.setFileJsonStr(formatJsonStr);
        List<UMLProject> umlProjects = new DefaultParse().parse(umlFile);
        List<UMLClass> classes = new ArrayList<>();
        List<UMLInterface> interfaces = new ArrayList<>();
        List<UMLEnumeration> enumerations = new ArrayList<>();
        UMLProject project = umlProjects.get(0);
        LinkedList<UMLModel> cachedModels = new LinkedList<>();
        cachedModels.addAll(project.getModels());
        LinkedList<UMLPackage> cachedPackages = new LinkedList<>();
        while (!cachedModels.isEmpty()) {
            UMLModel model = cachedModels.removeLast();
            if (model.getPackages() != null) {
                cachedPackages.addAll(model.getPackages());
            }
            while (!cachedPackages.isEmpty()) {
                UMLPackage umlPackage = cachedPackages.removeLast();
                if (umlPackage.getPackages() != null) {
                    cachedPackages.addAll(umlPackage.getPackages());
                }
                if (umlPackage.getClasses() != null) {
                    classes.addAll(umlPackage.getClasses());
                }
                if (umlPackage.getUmlModels() != null) {
                    cachedModels.addAll(umlPackage.getUmlModels());
                }
                if (umlPackage.getInterfaces() != null) {
                    interfaces.addAll(umlPackage.getInterfaces());
                }
                if (umlPackage.getEnumerations() != null) {
                    enumerations.addAll(umlPackage.getEnumerations());
                }
            }
            if (model.getUmlClasses() != null) {
                classes.addAll(model.getUmlClasses());
            }
            if (model.getUmlInterfaces() != null) {
                interfaces.addAll(model.getUmlInterfaces());
            }
            if (model.getUmlEnumerations() != null) {
                enumerations.addAll(model.getUmlEnumerations());
            }
        }
        GenConfig config = GenUtils.getConfig();
        List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);

        StringBuilder result = new StringBuilder();
        for (GenTemplate template : templateList) {
            Map<String, Object> dataModel = new HashMap<>();
            switch (template.getName()) {
                case "class": {
                    for (UMLClass umlClass : classes) {
                        if (!StringUtils.isEmpty(umlClass.getStereotype()))
                            continue;
                        dataModel.put("class", umlClass);
                        result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(template, dataModel, genScheme.getReplaceFile()), "\\"));
                    }
                    break;
                }
                case "interface": {
                    for (UMLInterface umlInterface : interfaces) {
                        dataModel.put("interface", umlInterface);
                        result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(template, dataModel, genScheme.getReplaceFile()), "\\"));
                    }
                    break;
                }
                case "enum": {
                    for (UMLEnumeration umlEnumeration : enumerations) {
                        dataModel.put("enum", umlEnumeration);
                        result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(template, dataModel, genScheme.getReplaceFile()), "\\"));
                    }
                    break;
                }
            }
        }
        return result.toString();
    }

}
