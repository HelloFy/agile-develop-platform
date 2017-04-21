package cn.edu.xidian.platform.gen.utils.uml.parse.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLFile;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLModel;
import cn.edu.xidian.platform.gen.entity.uml.UMLPackage;
import cn.edu.xidian.platform.gen.entity.uml.UMLProject;
import cn.edu.xidian.platform.gen.entity.uml.UMLType;
import cn.edu.xidian.platform.gen.utils.uml.parse.processor.DefaultProcessor;

/**
 * Created by 费玥 on 2017-4-21.
 */
public class DefaultParse implements Parse<UMLProject> {

    private static final ThreadLocal<HashMap<String, UMLClass>> classesCachedMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, UMLInterface>> interfacesCachedMap = new ThreadLocal<>();

    private final DefaultProcessor processor;

    public DefaultParse() {
        this.processor = new DefaultProcessor();
    }

    private void initCachedMap() {
        if (classesCachedMap.get() == null) {
            classesCachedMap.set(new HashMap<>());
        }
        if (interfacesCachedMap.get() == null) {
            interfacesCachedMap.set(new HashMap<>());
        }
    }


    private List<UMLProject> parse(JSONObject jsonObject) {
        initCachedMap();
        LinkedList<JSONObject> inParsed = new LinkedList<>();
        inParsed.addLast(jsonObject);
        LinkedList<UMLModel> parsedModels = new LinkedList<>();
        LinkedList<UMLPackage> parsedPackages = new LinkedList<>();
        List<UMLProject> projects = new ArrayList<>();
        while (!inParsed.isEmpty()) {
            JSONObject var1 = inParsed.removeLast();
            if (processor.containsElements(var1)) {
                JSONArray var2 = processor.getElements(var1);
                for (Object var3 : var2) {
                    JSONObject var4 = (JSONObject) var3;
                    inParsed.addLast(var4);
                    UMLType _type = processor.parseUMLType(var4);
                    if (_type == null) {
                        continue;
                    }
                    switch (_type) {
                        case UMLProject: {
                            UMLProject project = processor.parseUMLProject(var4);
                            projects.add(project);
                            break;
                        }
                        case UMLModel: {
                            UMLModel model = processor.parseUMLModel(var4);
                            parsedModels.addFirst(model);
                            if (projects.get(0) != null) {
                                if (projects.get(0).getModels() == null) {
                                    projects.get(0).setModels(new ArrayList<>(parsedModels));
                                } else {
                                    projects.get(0).getModels().add(model);
                                }
                            }
                            break;
                        }
                        case UMLPackage: {
                            UMLPackage umlPackage = processor.parseUMLPackage(var4);

                        }
                        case UMLClass: {

                        }
                        case UMLInterface: {

                        }
                        case UMLEnumeration: {

                        }
                    }
                }
            }
        }
        return projects;
    }

    @Override
    public List<UMLProject> parse(UMLFile umlFile) {
        return parse(JSON.parseObject(umlFile.getFileJsonStr()));
    }
}
