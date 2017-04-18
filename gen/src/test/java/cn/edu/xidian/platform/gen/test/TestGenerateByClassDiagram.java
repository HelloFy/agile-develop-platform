package cn.edu.xidian.platform.gen.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import cn.edu.xidian.platform.commons.utils.FileUtils;
import cn.edu.xidian.platform.gen.entity.uml.Attribute;
import cn.edu.xidian.platform.gen.entity.uml.JavaType;
import cn.edu.xidian.platform.gen.entity.uml.Opreation;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLType;

/**
 * Created by 费玥 on 2017-4-18.
 */
public class TestGenerateByClassDiagram {

    private void parseUMLClass() {

    }

    @Test
    public void testParse() throws IOException {
        String jsonStr = FileUtils.readFileToString(new File("D:\\agile-develop-platform\\gen\\src\\main\\resources\\templates\\gen\\1.mdj"));
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray("ownedElements");
        Stack<JSONArray> stack = new Stack<>();
        Set<String> parsed = new HashSet<>();
        stack.push(jsonArray);
        while (!stack.isEmpty()) {
            jsonArray = stack.pop();
            for (Object var1 : jsonArray) {
                JSONObject var2 = (JSONObject) var1;
                String _type = var2.getString("_type");
                UMLType umlType = UMLType.covertFromString(_type);
                switch (umlType) {
                    case UMLClass: {
                        String name = var2.getString("name");
                        if (parsed.contains(name)) {
                            break;
                        }
                        UMLClass umlClass = new UMLClass();
                        umlClass.setName(name);
                        parsed.add(name);
                        umlClass.setAbstract(var2.getBoolean("isAbstract"));
                        umlClass.setFinalSpecialization(var2.getBoolean("isFinalSpecialization"));
                        JSONArray var4 = var2.getJSONArray("attributes");
                        ArrayList<Attribute> attributes = new ArrayList<>();
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
                        JSONArray var7 = var2.getJSONArray("opreations");
                        ArrayList<Opreation> opreations = new ArrayList<>();
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
                        umlClass.setAtributes(attributes);
                        umlClass.setOpreations(opreations);
                        JSONArray var13 = var2.getJSONArray("ownedElements");
                        Stack<JSONArray> innerClasses = new Stack<>();
                        Stack<UMLClass> umlClasses = new Stack<>();
                        umlClasses.push(umlClass);
                        innerClasses.push(var13);
                        while (!innerClasses.isEmpty()) {
                            JSONArray var14 = innerClasses.pop();
                            UMLClass var15 = umlClasses.pop();
                            for (Object var16 : var14) {

                            }
                        }
                        break;
                    }
                    case UMLInterface: {

                    }
                    default: {
                        continue;
                    }
                }
                if (var2.containsKey("ownedElements")) {
                    JSONArray var3 = var2.getJSONArray("ownedElements");
                    stack.push(var3);
                }
            }

        }
    }
}
