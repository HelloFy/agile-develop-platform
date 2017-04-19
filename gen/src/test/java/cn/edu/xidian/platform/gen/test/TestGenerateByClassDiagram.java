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
import java.util.List;
import java.util.Set;
import java.util.Stack;

import cn.edu.xidian.platform.commons.utils.FileUtils;
import cn.edu.xidian.platform.gen.entity.uml.Attribute;
import cn.edu.xidian.platform.gen.entity.uml.JavaType;
import cn.edu.xidian.platform.gen.entity.uml.Opreation;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLType;

/**
 * Created by 费玥 on 2017-4-18.
 */
public class TestGenerateByClassDiagram {

    private UMLClass parseUMLClass(JSONObject var1) {
        String name = var1.getString("name");
        UMLClass umlClass = new UMLClass();
        umlClass.setName(name);
        umlClass.setAbstract(var1.getBoolean("isAbstract"));
        umlClass.setFinalSpecialization(var1.getBoolean("isFinalSpecialization"));
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
        ArrayList<Opreation> opreations = new ArrayList<>();
        if (var1.containsKey("opreations")) {
            JSONArray var7 = var1.getJSONArray("opreations");
            for (Object var8 : var7) {
                JSONObject var9 = (JSONObject) var8;
                Opreation opreation = new Opreation();
                opreation.setName(var9.getString("name"));
                opreation.setStatic(var9.getBoolean("isStatic"));
                opreation.setAbstract(var9.getBoolean("isAbstract"));
                String concurrency = var9.getString("concurrency");
                opreation.setConcurrency(Opreation.convertFromString(concurrency));
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
                            opreation.setParamterOut(parameter);
                            break;
                        }
                        case IN: {
                            paramtersIn.add(parameter);
                            break;
                        }
                    }

                }
                opreation.setParamterIn(paramtersIn);
            }
        }
        umlClass.setAtributes(attributes);
        umlClass.setOpreations(opreations);
        return umlClass;
    }

    private UMLInterface parseInterface(JSONObject var1) {
        UMLInterface umlInterface = new UMLInterface();
        String name = var1.getString("name");
        return umlInterface;
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
        stack.push(jsonArray);
        while (!stack.isEmpty()) {
            jsonArray = stack.pop();
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
                            Stack<JSONArray> innerClasses = new Stack<>();
                            Stack<UMLClass> umlClasses = new Stack<>();
                            umlClasses.push(umlClass);
                            innerClasses.push(var3);
                            while (!innerClasses.isEmpty()) {
                                JSONArray var4 = innerClasses.pop();
                                UMLClass var5 = umlClasses.pop();
                                List<UMLClass> var7 = new ArrayList<>();
                                for (Object var6 : var4) {
                                    JSONObject var8 = (JSONObject) var6;
                                    _type = var8.getString("_type");
                                    umlType = UMLType.covertFromString(_type);
                                    if (umlType == null) {
                                        continue;
                                    }
                                    switch (umlType) {
                                        case UMLClass: {
                                            var7.add(parseUMLClass(var8));
                                            break;
                                        }
                                        default:
                                            break;
                                    }
                                    umlClasses.push(parseUMLClass(var8));
                                    if (var8.containsKey("ownedElements")) {
                                        innerClasses.push(var8.getJSONArray("ownedElements"));
                                    }
                                }
                                var5.setInnerClasses(var7);
                            }
                        }

                        parsedClass.add(umlClass);
                        break;
                    }
                    case UMLInterface: {

                        break;
                    }
                    case UMLPackage: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }

        }
        System.out.println(parsedClass);
    }
}
