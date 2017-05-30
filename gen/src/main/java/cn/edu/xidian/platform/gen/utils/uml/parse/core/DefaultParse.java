package cn.edu.xidian.platform.gen.utils.uml.parse.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.edu.xidian.platform.gen.entity.uml.UMLBase;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLEnumeration;
import cn.edu.xidian.platform.gen.entity.uml.UMLFile;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLModel;
import cn.edu.xidian.platform.gen.entity.uml.UMLPackage;
import cn.edu.xidian.platform.gen.entity.uml.UMLProject;
import cn.edu.xidian.platform.gen.entity.uml.UMLType;
import cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey;
import cn.edu.xidian.platform.gen.utils.uml.parse.processor.DefaultProcessor;

/**
 * Created by 李婧 on 2017-4-21.
 */
public class DefaultParse implements Parse<UMLProject> {

    private final DefaultProcessor processor;

    private static final ThreadLocal<HashMap<String, String>> cachedPackageName = new ThreadLocal<>();

    public DefaultParse() {
        this.processor = new DefaultProcessor();
    }

    private List<UMLProject> parse(JSONObject jsonObject) {
        LinkedList<JSONObject> inParsed = new LinkedList<>();
        ArrayList<UMLProject> projects = new ArrayList<>();
        LinkedList<UMLBase> curParsed = new LinkedList<>();
        inParsed.addLast(jsonObject);
        UMLProject umlProject = processor.parseUMLProject(jsonObject);
        curParsed.addLast(umlProject);
        projects.add(umlProject);
        if (cachedPackageName.get() == null) {
            cachedPackageName.set(new HashMap<>());
        }
        while (!inParsed.isEmpty()) {
            JSONObject var1 = inParsed.removeLast();
            UMLBase parsedElement = null;
            if (processor.containsElements(var1)
                    || UMLType.covertFromString(var1.getString(UMLTypeKey.UML_TYPE_KEY.toString())) == UMLType.UMLModel
                    || UMLType.covertFromString(var1.getString(UMLTypeKey.UML_TYPE_KEY.toString())) == UMLType.UMLPackage) {
                if (!curParsed.isEmpty()) {
                    parsedElement = curParsed.removeLast();
                }
                if (!processor.containsElements(var1)) {
                    continue;
                }
                JSONArray var2 = processor.getElements(var1);
                for (Object var3 : var2) {
                    JSONObject var4 = (JSONObject) var3;
                    inParsed.addLast(var4);
                    UMLType _type = processor.parseUMLType(var4);
                    if (_type == null) {
                        continue;
                    }
                    switch (_type) {
                        case UMLModel: {
                            UMLModel model = processor.parseUMLModel(var4);
                            curParsed.addLast(model);
                            cachedPackageName.get().put(model.getId(), "");
                            if (parsedElement != null) {
                                switch (parsedElement.getUmlType()) {
                                    case UMLProject: {
                                        UMLProject projectParent = (UMLProject) parsedElement;
                                        List<UMLModel> models = projectParent.getModels();
                                        if (models == null) {
                                            models = new ArrayList<>();
                                            projectParent.setModels(models);
                                        }
                                        models.add(model);
                                        break;
                                    }
                                    case UMLPackage: {
                                        UMLPackage packageParent = (UMLPackage) parsedElement;
                                        List<UMLModel> models = packageParent.getUmlModels();
                                        if (models == null) {
                                            models = new ArrayList<>();
                                            packageParent.setUmlModels(models);
                                        }
                                        models.add(model);
                                        break;
                                    }
                                    case UMLModel: {
                                        UMLModel modelParent = (UMLModel) parsedElement;
                                        List<UMLModel> models = modelParent.getUmlModels();
                                        if (models == null) {
                                            models = new ArrayList<>();
                                            modelParent.setUmlModels(models);
                                        }
                                        models.add(model);
                                    }
                                }
                            }
                            break;
                        }
                        case UMLPackage: {
                            UMLPackage umlPackage = processor.parseUMLPackage(var4);
                            curParsed.addLast(umlPackage);
                            if (umlPackage.getName().equals("xidian")) {
                                System.out.println();
                            }
                            if (umlPackage.getName().equals("edu")) {
                                System.out.println();
                            }
                            if (parsedElement != null) {
                                switch (parsedElement.getUmlType()) {
                                    case UMLModel: {
                                        UMLModel parentModel = (UMLModel) parsedElement;
                                        List<UMLPackage> packages = parentModel.getPackages();
                                        if (packages == null) {
                                            packages = new ArrayList<>();
                                            parentModel.setPackages(packages);
                                        }
                                        packages.add(umlPackage);
                                        cachedPackageName.get().put(umlPackage.getId(), umlPackage.getName());
                                        break;
                                    }
                                    case UMLPackage: {
                                        UMLPackage parentPackage = (UMLPackage) parsedElement;
                                        List<UMLPackage> packages = parentPackage.getPackages();
                                        if (packages == null) {
                                            packages = new ArrayList<>();
                                            parentPackage.setPackages(packages);
                                        }
                                        String parentPacName = cachedPackageName.get().get(parentPackage.getId());
                                        cachedPackageName.get().put(umlPackage.getId(), parentPacName + "." + umlPackage.getName());
                                        packages.add(umlPackage);
                                        break;
                                    }
                                }
                            }
                            break;

                        }
                        case UMLClass: {
                            UMLClass umlClass = processor.parseUMLClass(var4);
                            if (umlClass == null) {
                                break;
                            }
                            if (processor.containsElements(var4)) {
                                umlClass.setUmlRelation(processor.parseUMLRelation(processor.getElements(var4), UMLType.UMLClass));
                            }
                            if (parsedElement != null) {
                                umlClass.setPackageName(cachedPackageName.get().get(parsedElement.getId()));
                                switch (parsedElement.getUmlType()) {
                                    case UMLModel: {
                                        UMLModel parentModel = (UMLModel) parsedElement;
                                        List<UMLClass> classes = parentModel.getUmlClasses();
                                        if (classes == null) {
                                            classes = new ArrayList<>();
                                            parentModel.setUmlClasses(classes);
                                        }
                                        classes.add(umlClass);
                                        break;
                                    }
                                    case UMLPackage: {
                                        UMLPackage parentPackage = (UMLPackage) parsedElement;
                                        List<UMLClass> classes = parentPackage.getClasses();
                                        if (classes == null) {
                                            classes = new ArrayList<>();
                                            parentPackage.setClasses(classes);
                                        }
                                        classes.add(umlClass);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case UMLInterface: {
                            UMLInterface umlInterface = processor.parseUMLInterface(var4);
                            if (umlInterface == null) {
                                break;
                            }
                            if (processor.containsElements(var4)) {
                                umlInterface.setUmlRelation(processor.parseUMLRelation(processor.getElements(var4), UMLType.UMLInterface));
                            }
                            if (parsedElement != null) {
                                umlInterface.setPackageName(cachedPackageName.get().get(parsedElement.getId()));
                                switch (parsedElement.getUmlType()) {
                                    case UMLModel: {
                                        UMLModel parentModel = (UMLModel) parsedElement;
                                        List<UMLInterface> interfaces = parentModel.getUmlInterfaces();
                                        if (interfaces == null) {
                                            interfaces = new ArrayList<>();
                                            parentModel.setUmlInterfaces(interfaces);
                                        }
                                        interfaces.add(umlInterface);
                                    }
                                    case UMLPackage: {
                                        UMLPackage parentPackage = (UMLPackage) parsedElement;
                                        List<UMLInterface> interfaces = parentPackage.getInterfaces();
                                        if (interfaces == null) {
                                            interfaces = new ArrayList<>();
                                            parentPackage.setInterfaces(interfaces);
                                        }
                                        interfaces.add(umlInterface);
                                    }
                                }
                            }
                            break;
                        }
                        case UMLEnumeration: {
                            UMLEnumeration umlEnumeration = processor.parseUMLEnumeration(var4);
                            if (umlEnumeration == null) {
                                break;
                            }
                            if (parsedElement != null) {
                                umlEnumeration.setPackageName(cachedPackageName.get().get(parsedElement.getId()));
                                switch (parsedElement.getUmlType()) {
                                    case UMLModel: {
                                        UMLModel parentModel = (UMLModel) parsedElement;
                                        List<UMLEnumeration> enumerations = parentModel.getUmlEnumerations();
                                        if (enumerations == null) {
                                            enumerations = new ArrayList<>();
                                            parentModel.setUmlEnumerations(enumerations);
                                        }
                                        enumerations.add(umlEnumeration);
                                    }
                                    case UMLPackage: {
                                        UMLPackage parentPackage = (UMLPackage) parsedElement;
                                        List<UMLEnumeration> enumerations = parentPackage.getEnumerations();
                                        if (enumerations == null) {
                                            enumerations = new ArrayList<>();
                                            parentPackage.setEnumerations(enumerations);
                                        }
                                        enumerations.add(umlEnumeration);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        processor.clearCache();
        return projects;
    }

    @Override
    public List<UMLProject> parse(UMLFile umlFile) {
        return parse(JSON.parseObject(umlFile.getFileJsonStr()));
    }
}
