package cn.edu.xidian.platform.gen.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import cn.edu.xidian.platform.commons.utils.FileUtils;
import cn.edu.xidian.platform.gen.entity.uml.Attribute;
import cn.edu.xidian.platform.gen.entity.uml.JavaType;
import cn.edu.xidian.platform.gen.entity.uml.Opreation;
import cn.edu.xidian.platform.gen.entity.uml.UMLBase;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLEnumeration;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLPackage;
import cn.edu.xidian.platform.gen.entity.uml.UMLRelation;
import cn.edu.xidian.platform.gen.entity.uml.UMLType;

/**
 * Created by 费玥 on 2017-4-18.
 */
public class TestGenerateByClassDiagram {

    private <T extends UMLBase> void parseBase(T umlBase, JSONObject var1) {
        if (var1.containsKey("name"))
            umlBase.setName(var1.getString("name"));
        if (var1.containsKey("visibility"))
            umlBase.setVisibility(var1.getString("visibility"));
        if (var1.containsKey("documention")) {
            umlBase.setDocumention(var1.getString("documention"));
        }
    }

    private UMLRelation parseRelation(JSONArray var1) {
        UMLRelation relation = new UMLRelation();
        List<UMLClass> innerClasses = new ArrayList<>();
        List<UMLInterface> impInterfaces = new ArrayList<>();
        UMLClass parentClass = null;
        List<UMLClass> combinationClasses = new ArrayList<>();
        for (Object var2 : var1) {
            JSONObject var3 = (JSONObject) var2;
            String _type = var3.getString("_type");
            UMLType umlType = UMLType.covertFromString(_type);
            switch (umlType) {
                case UMLClass: {
                    UMLClass umlClass = parseUMLClass(var3);
                    if (var3.containsKey("ownedElements")) {
                        umlClass.setUmlRelation(parseRelation(var3.getJSONArray("ownedElements")));
                    }
                    innerClasses.add(umlClass);
                    break;
                }
                case UMLGeneralization: {
                    parentClass = parseUMLClass(var3.getJSONObject("target"));
                    break;
                }
                case UMLInterfaceRealization: {
                    impInterfaces.add(parseInterface(var3.getJSONObject("target")));
                    break;
                }
                default: {
                    break;
                }
            }

        }
        relation.setInnerClasses(innerClasses);
        relation.setParentClass(parentClass);
        relation.setImpInterfaces(impInterfaces);
        return relation;
    }

    private UMLClass parseUMLClass(JSONObject var1) {
        UMLClass umlClass = new UMLClass();
        parseBase(umlClass, var1);
        if (var1.containsKey("isAbstract")) {
            umlClass.setAbstract(var1.getBoolean("isAbstract"));
        }
        if (var1.containsKey("isFinalSpecialization")) {
            umlClass.setFinalSpecialization(var1.getBoolean("isFinalSpecialization"));
        }
        ArrayList<Attribute> attributes = new ArrayList<>();
        if (var1.containsKey("attributes")) {
            JSONArray var4 = var1.getJSONArray("attributes");
            for (Object var5 : var4) {
                JSONObject var6 = (JSONObject) var5;
                Attribute attribute = new Attribute();
                attribute.setName(var6.getString("name"));
                attribute.setStatic(var6.getBoolean("isStatic"));
                attribute.setReadOnly(var6.getBoolean("isReadOnly"));
                attribute.setDefaultValue(var6.get("defaultValue"));
                attribute.setDerived(var6.getBooleanValue("isDerived"));
                String type = var6.getString("type");
                attribute.setType(JavaType.convertFromString(type));
                attributes.add(attribute);
            }
        }
        ArrayList<Opreation> operations = new ArrayList<>();
        if (var1.containsKey("operations")) {
            JSONArray var7 = var1.getJSONArray("operations");
            for (Object var8 : var7) {
                JSONObject var9 = (JSONObject) var8;
                Opreation operation = new Opreation();
                operation.setName(var9.getString("name"));
                operation.setStatic(var9.getBoolean("isStatic"));
                operation.setAbstract(var9.getBoolean("isAbstract"));
                String concurrency = var9.getString("concurrency");
                operation.setConcurrency(Opreation.convertFromString(concurrency));
                if (var9.containsKey("parameters")) {
                    ArrayList<Opreation.Paramter> paramtersIn = new ArrayList<>();
                    JSONArray var10 = var9.getJSONArray("parameters");
                    for (Object var11 : var10) {
                        JSONObject var12 = (JSONObject) var11;
                        Opreation.Paramter parameter = new Opreation.Paramter();
                        String paraName = var12.getString("name");
                        parameter.setName(paraName);
                        parameter.setReadOnly(var12.getBoolean("isReadOnly"));
                        parameter.setType(JavaType.convertFromString(var12.getString("type")));
                        String paraDirection = var12.getString("direction");
                        Opreation.Paramter.ParaType paraType = Opreation.Paramter.ParaType.convertFromString(paraDirection);
                        switch (paraType) {
                            case RETURN: {
                                operation.setParamterOut(parameter);
                                break;
                            }
                            case IN: {
                                paramtersIn.add(parameter);
                                break;
                            }
                        }

                    }
                    operation.setParamterIn(paramtersIn);
                }
                operations.add(operation);
            }
        }
        umlClass.setAtributes(attributes);
        umlClass.setOpreations(operations);
        return umlClass;
    }

    private UMLPackage parseUMLPackage(JSONObject var1) {
        UMLPackage umlPackage = new UMLPackage();
        parseBase(umlPackage, var1);
        return umlPackage;
    }

    private UMLInterface parseInterface(JSONObject var1) {
        UMLInterface umlInterface = new UMLInterface();
        parseBase(umlInterface, var1);
        return umlInterface;
    }

    private UMLEnumeration parseUMLEnumeration(JSONObject var1) {
        UMLEnumeration umlEnumeration = new UMLEnumeration();
        parseBase(umlEnumeration, var1);
        return umlEnumeration;
    }

    @Test
    public void testParse() throws IOException {
        String path = this.getClass().getClassLoader().getResource("templates/gen/").getPath();
        path = URLDecoder.decode(path, "UTF-8");
        String jsonStr = FileUtils.readFileToString(new File(path + "1.mdj"));
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray("ownedElements");
        Stack<JSONArray> stack = new Stack<>();
        Set<String> parsed = new HashSet<>();
        List<UMLClass> parsedClass = new ArrayList<>();
        LinkedList<UMLPackage> umlPackages = new LinkedList<>();
        List<UMLPackage> result = new ArrayList<>();
        stack.push(jsonArray);
        while (!stack.isEmpty()) {
            jsonArray = stack.pop();
/*            if (!umlPackages.isEmpty()) {
                result.add(umlPackages.removeLast());
            }*/
            for (Object var1 : jsonArray) {
                JSONObject var2 = (JSONObject) var1;
                if (var2.containsKey("ownedElements")) {
                    JSONArray var3 = var2.getJSONArray("ownedElements");
                    stack.push(var3);
                }
                String _type = var2.getString("_type");
                UMLType umlType = UMLType.covertFromString(_type);
                if (umlType == null) {
                    continue;
                }
                switch (umlType) {
                    case UMLClass: {
                        String name = var2.getString("name");
                        if (parsed.contains(name)) {
                            break;
                        }
                        parsed.add(name);
                        UMLClass umlClass = parseUMLClass(var2);
                        if (var2.containsKey("ownedElements")) {
                            JSONArray var3 = var2.getJSONArray("ownedElements");
                            umlClass.setUmlRelation(parseRelation(var3));
                        }
                        if (!umlPackages.isEmpty()) {
                            if (umlPackages.getLast().getClasses() != null) {
                                umlPackages.getLast().getClasses().add(umlClass);
                            } else {
                                List<UMLClass> umlClasses = new ArrayList<>();
                                umlPackages.getLast().setClasses(umlClasses);
                                umlClasses.add(umlClass);
                            }
                        }
                        break;
                    }
                    case UMLInterface: {
                        UMLInterface umlInterface = parseInterface(var2);
                        if (var2.containsKey("ownedElements")) {
                            umlInterface.setRelation(parseRelation(var2.getJSONArray("ownedElements")));
                        }
                        if (!umlPackages.isEmpty()) {
                            if (umlPackages.getLast().getInterfaces() != null) {
                                umlPackages.getLast().getInterfaces().add(umlInterface);
                            } else {
                                List<UMLInterface> interfaces = new ArrayList<>();
                                umlPackages.getLast().setInterfaces(interfaces);
                                interfaces.add(umlInterface);
                            }
                        }
                        break;
                    }
                    case UMLPackage: {
                        umlPackages.addFirst(parseUMLPackage(var2));
                        break;
                    }
                    case UMLEnumeration: {
                        UMLEnumeration umlEnumeration = parseUMLEnumeration(var2);
                        if (!umlPackages.isEmpty()) {
                            if (umlPackages.getLast().getEnumerations() != null) {
                                umlPackages.getLast().getEnumerations().add(umlEnumeration);
                            } else {
                                List<UMLEnumeration> enumerations = new ArrayList<>();
                                umlPackages.getLast().setEnumerations(enumerations);
                                enumerations.add(umlEnumeration);
                            }
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }

        }
        System.out.println(result);
    }
}
