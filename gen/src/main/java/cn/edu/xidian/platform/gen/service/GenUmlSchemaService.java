package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.xidian.platform.commons.config.Global;
import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenUmlClassDiagramDao;
import cn.edu.xidian.platform.gen.entity.GenConfig;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenTemplate;
import cn.edu.xidian.platform.gen.entity.GenUmlClassDiagram;
import cn.edu.xidian.platform.gen.entity.uml.JavaFileType;
import cn.edu.xidian.platform.gen.entity.uml.UMLAttribute;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLEnumeration;
import cn.edu.xidian.platform.gen.entity.uml.UMLFile;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLModel;
import cn.edu.xidian.platform.gen.entity.uml.UMLOperation;
import cn.edu.xidian.platform.gen.entity.uml.UMLPackage;
import cn.edu.xidian.platform.gen.entity.uml.UMLProject;
import cn.edu.xidian.platform.gen.entity.uml.UMLRelation;
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

    private Set<String> parseImport(JavaFileType javaFileType) {
        Set<String> refImport = new HashSet<>();
        LinkedList<JavaFileType> parseRefImpStack = new LinkedList<>();
        parseRefImpStack.addLast(javaFileType);
        if (!parseRefImpStack.isEmpty()) {
            JavaFileType curParse = parseRefImpStack.getLast();
            if (curParse.getAttributes() != null && curParse.getAttributes().size() > 0) {
                for (UMLAttribute a : curParse.getAttributes()) {
                    if (a.getOtherType() != null) {
                        if (a.getOtherTypePackageName() != null && !a.getOtherTypePackageName().equals(curParse.getPackageName())) {
                            if (!refImport.contains(a.getOtherTypePackageName() + "." + a.getOtherType() + ";")) {
                                refImport.add(a.getOtherTypePackageName() + "." + a.getOtherType() + ";");
                            }
                        }
                    }
                }
            }
            if (curParse.getOperations() != null && curParse.getOperations().size() > 0) {
                for (UMLOperation o : curParse.getOperations()) {
                    if (o.getParamterOut() != null) {
                        if (o.getParamterOut().getOtherType() != null) {
                            if (o.getParamterOut().getOtherTypePackageName() != null && !o.getParamterOut().getOtherTypePackageName().equals(curParse.getPackageName())) {
                                if (!refImport.contains(o.getParamterOut().getOtherTypePackageName() + "." + o.getParamterOut().getOtherType() + ";")) {
                                    refImport.add(o.getParamterOut().getOtherTypePackageName() + "." + o.getParamterOut().getOtherType() + ";");
                                }
                            }
                        }
                    }
                    if (o.getParamterIn() != null && o.getParamterIn().size() > 0) {
                        for (UMLOperation.Paramter p : o.getParamterIn()) {
                            if (p.getOtherType() != null) {
                                if (p.getOtherTypePackageName() != null && !p.getOtherTypePackageName().equals(curParse.getPackageName())) {
                                    if (!refImport.contains(p.getOtherTypePackageName() + "." + p.getOtherType() + ";")) {
                                        refImport.add(p.getOtherTypePackageName() + "." + p.getOtherType() + ";");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (curParse.getUmlRelation() != null) {
                UMLRelation umlRelation = curParse.getUmlRelation();
                if (umlRelation.getParentClass() != null) {
                    UMLClass parentClass = umlRelation.getParentClass();
                    if (parentClass.getPackageName() != null && !parentClass.getPackageName().equals(curParse.getPackageName())) {
                        if (!refImport.contains(parentClass.getPackageName() + "." + parentClass.getName() + ";")) {
                            refImport.add(parentClass.getPackageName() + "." + parentClass.getName() + ";");
                        }
                    }
                }
                if (umlRelation.getComposes() != null && umlRelation.getComposes().size() > 0) {
                    for (UMLRelation.UMLCompose compose : umlRelation.getComposes()) {
                        if (compose.getComposeClass() != null) {
                            if (compose.getComposeClass().getPackageName() != null && !compose.getComposeClass().getPackageName().equals(curParse.getPackageName())) {
                                if (!refImport.contains(compose.getComposeClass().getPackageName() + "." + compose.getComposeClass().getName() + ";")) {
                                    refImport.add(compose.getComposeClass().getPackageName() + "." + compose.getComposeClass().getName() + ";");
                                }
                            }
                        }
                        if (compose.getComposeEnum() != null) {
                            if (compose.getComposeEnum().getPackageName() != null && !compose.getComposeEnum().getPackageName().equals(curParse.getPackageName())) {
                                if (!refImport.contains(compose.getComposeEnum().getPackageName() + "." + compose.getComposeEnum().getName() + ";")) {
                                    refImport.add(compose.getComposeEnum().getPackageName() + "." + compose.getComposeEnum().getName() + ";");
                                }
                            }
                        }

                        if (compose.getComposeInterface() != null) {
                            if (compose.getComposeInterface().getPackageName() != null && !compose.getComposeInterface().getPackageName().equals(curParse.getPackageName())) {
                                if (!refImport.contains(compose.getComposeInterface().getPackageName() + "." + compose.getComposeInterface().getName() + ";")) {
                                    refImport.add(compose.getComposeInterface().getPackageName() + "." + compose.getComposeInterface().getName() + ";");
                                }
                            }
                        }
                    }

                }
                if (umlRelation.getImpInterfaces() != null && umlRelation.getImpInterfaces().size() > 0) {
                    for (UMLInterface i : umlRelation.getImpInterfaces()) {
                        if (i.getPackageName() != null && !i.getPackageName().equals(curParse.getPackageName())) {
                            if (!refImport.contains(i.getPackageName() + "." + i.getName() + ";")) {
                                refImport.add(i.getPackageName() + "." + i.getName() + ";");
                            }
                        }
                    }
                }
                if (umlRelation.getInnerClasses() != null && umlRelation.getInnerClasses().size() > 0) {
                    for (UMLClass c : umlRelation.getInnerClasses()) {
                        parseRefImpStack.addLast(c);
                    }
                }
                if (umlRelation.getInnerInterfaces() != null && umlRelation.getInnerInterfaces().size() > 0) {
                    for (UMLInterface i : umlRelation.getInnerInterfaces()) {
                        parseRefImpStack.addLast(i);
                    }
                }
            }
        }
        return refImport;
    }

    @Override
    public GenScheme initDefaultGenScheme(String name) {
        GenScheme defaultGenScheme = new GenScheme();
        defaultGenScheme.setName(name);
        defaultGenScheme.setCategory("uml");
        defaultGenScheme.setPackageName("cn.edu.xidian.platform");
        defaultGenScheme.setModuleName("gen");
        defaultGenScheme.setFunctionAuthor(Global.getAuthorName());
        defaultGenScheme.setFunctionName("uml " + name);
        defaultGenScheme.setRefType(GenScheme.GEN_UML_TYPE);
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
                        Set<String> imports = parseImport(umlClass);
                        dataModel.put("class", umlClass);
                        dataModel.put("imports", imports);
                        result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(template, dataModel, genScheme.getReplaceFile()), "\\"));
                    }
                    break;
                }
                case "interface": {
                    for (UMLInterface umlInterface : interfaces) {
                        Set<String> imports = parseImport(umlInterface);
                        dataModel.put("interface", umlInterface);
                        dataModel.put("imports", imports);
                        result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(template, dataModel, genScheme.getReplaceFile()), "\\"));
                    }
                    break;
                }
                case "enum": {
                    for (UMLEnumeration umlEnumeration : enumerations) {
                        dataModel.put("enum", umlEnumeration);
                        Set<String> imports = parseImport(umlEnumeration);
                        dataModel.put("imports", imports);
                        result.append(StringUtils.substringAfterLast(GenUtils.generateToFile(template, dataModel, genScheme.getReplaceFile()), "\\"));
                    }
                    break;
                }
            }
        }
        return result.toString();
    }

}
