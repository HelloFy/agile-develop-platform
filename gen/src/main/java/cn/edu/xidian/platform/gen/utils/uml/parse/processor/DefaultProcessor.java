package cn.edu.xidian.platform.gen.utils.uml.parse.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xidian.platform.gen.entity.uml.JavaType;
import cn.edu.xidian.platform.gen.entity.uml.UMLAnnotionType;
import cn.edu.xidian.platform.gen.entity.uml.UMLAttribute;
import cn.edu.xidian.platform.gen.entity.uml.UMLBase;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLEnumeration;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLModel;
import cn.edu.xidian.platform.gen.entity.uml.UMLOperation;
import cn.edu.xidian.platform.gen.entity.uml.UMLPackage;
import cn.edu.xidian.platform.gen.entity.uml.UMLProject;
import cn.edu.xidian.platform.gen.entity.uml.UMLRelation;
import cn.edu.xidian.platform.gen.entity.uml.UMLType;

import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.OWNED_ELEMENTS;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.UML_TYPE_KEY;

/**
 * Created by 费玥 on 2017-4-21.
 */
public class DefaultProcessor implements Processor<JSONObject, JSONArray>, ParseProcessor<JSONObject, JSONArray> {


    @Override
    public <E extends UMLBase> void parseUMLBase(E umlBase, JSONObject jsonObj) {
        if (jsonObj.containsKey("_id"))
            umlBase.setId(jsonObj.getString("_id"));
        if (jsonObj.containsKey("name"))
            umlBase.setName(jsonObj.getString("name"));
        if (jsonObj.containsKey("visibility"))
            umlBase.setVisibility(jsonObj.getString("visibility"));
        if (jsonObj.containsKey("documention")) {
            umlBase.setDocumention(jsonObj.getString("documention"));
        }
    }

    @Override
    public UMLProject parseUMLProject(JSONObject jsonObj) {
        UMLProject umlProject = new UMLProject();
        parseUMLBase(umlProject, jsonObj);
        return umlProject;
    }

    @Override
    public UMLModel parseUMLModel(JSONObject jsonObj) {
        UMLModel umlModel = new UMLModel();
        parseUMLBase(umlModel, jsonObj);
        return umlModel;
    }

    @Override
    public UMLPackage parseUMLPackage(JSONObject jsonObj) {
        UMLPackage umlPackage = new UMLPackage();
        parseUMLBase(umlPackage, jsonObj);
        return umlPackage;
    }

    @Override
    public UMLClass parseUMLClass(JSONObject jsonObj) {
        if (jsonObj.containsKey("stereotype")) {
            if (jsonObj.getString("stereotype").equals("annotationType")) {
                UMLAnnotionType umlAnnotionType = new UMLAnnotionType();
                parseUMLBase(umlAnnotionType, jsonObj);
                umlAnnotionType.setAttributes(parseUMLAttribute(jsonObj));
                umlAnnotionType.setUMLOperations(parseUMLOperation(jsonObj));
                return umlAnnotionType;
            }
        }
        UMLClass umlClass = new UMLClass();
        parseUMLBase(umlClass, jsonObj);
        if (jsonObj.containsKey("isAbstract")) {
            umlClass.setAbstract(jsonObj.getBoolean("isAbstract"));
        }
        if (jsonObj.containsKey("isFinalSpecialization")) {
            umlClass.setFinalSpecialization(jsonObj.getBoolean("isFinalSpecialization"));
        }
        umlClass.setAttributes(parseUMLAttribute(jsonObj));
        umlClass.setUMLOperations(parseUMLOperation(jsonObj));
        return umlClass;
    }

    @Override
    public UMLInterface parseUMLInterface(JSONObject jsonObj) {
        UMLInterface umlInterface = new UMLInterface();
        parseUMLBase(umlInterface, jsonObj);
        umlInterface.setAttributes(parseUMLAttribute(jsonObj));
        umlInterface.setUMLOperations(parseUMLOperation(jsonObj));
        return umlInterface;
    }

    @Override
    public UMLEnumeration parseUMLEnumeration(JSONObject jsonObj) {
        UMLEnumeration umlEnumeration = new UMLEnumeration();
        parseUMLBase(umlEnumeration, jsonObj);
        List<UMLEnumeration.UMLEnumerationLiteral> literals = new ArrayList<>();
        if (jsonObj.containsKey("literals")) {
            JSONArray var1 = jsonObj.getJSONArray("literals");
            for (Object var2 : var1) {
                JSONObject var4 = (JSONObject) var2;
                UMLEnumeration.UMLEnumerationLiteral literal = new UMLEnumeration.UMLEnumerationLiteral();
                parseUMLBase(literal, var4);
                literals.add(literal);
            }
        }
        umlEnumeration.setLiterals(literals);
        return umlEnumeration;
    }

    @Override
    public List<UMLAttribute> parseUMLAttribute(JSONObject jsonObj) {
        ArrayList<UMLAttribute> attributes = new ArrayList<>();
        if (jsonObj.containsKey("attributes")) {
            JSONArray var1 = jsonObj.getJSONArray("attributes");
            for (Object var2 : var1) {
                JSONObject var3 = (JSONObject) var2;
                UMLAttribute attribute = new UMLAttribute();
                attribute.setName(var3.getString("name"));
                attribute.setStatic(var3.getBoolean("isStatic"));
                attribute.setReadOnly(var3.getBoolean("isReadOnly"));
                attribute.setDefaultValue(var3.get("defaultValue"));
                attribute.setDerived(var3.getBooleanValue("isDerived"));
                String type = var3.getString("type");
                attribute.setType(JavaType.convertFromString(type));
                attributes.add(attribute);
            }
        }
        return attributes;
    }

    @Override
    public List<UMLOperation> parseUMLOperation(JSONObject jsonObj) {
        ArrayList<UMLOperation> operations = new ArrayList<>();
        if (jsonObj.containsKey("operations")) {
            JSONArray var1 = jsonObj.getJSONArray("operations");
            for (Object var2 : var1) {
                JSONObject var3 = (JSONObject) var2;
                UMLOperation operation = new UMLOperation();
                operation.setName(var3.getString("name"));
                operation.setStatic(var3.getBoolean("isStatic"));
                operation.setAbstract(var3.getBoolean("isAbstract"));
                String concurrency = var3.getString("concurrency");
                operation.setConcurrency(UMLOperation.convertFromString(concurrency));
                if (var3.containsKey("parameters")) {
                    ArrayList<UMLOperation.Paramter> paramtersIn = new ArrayList<>();
                    JSONArray var4 = var3.getJSONArray("parameters");
                    for (Object var5 : var4) {
                        JSONObject var6 = (JSONObject) var5;
                        UMLOperation.Paramter parameter = new UMLOperation.Paramter();
                        String paraName = var6.getString("name");
                        parameter.setName(paraName);
                        parameter.setReadOnly(var6.getBoolean("isReadOnly"));
                        parameter.setType(JavaType.convertFromString(var6.getString("type")));
                        String paraDirection = var6.getString("direction");
                        UMLOperation.Paramter.ParaType paraType = UMLOperation.Paramter.ParaType.convertFromString(paraDirection);
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
        return operations;
    }

    @Override
    public UMLRelation parseUMLRelation(JSONArray jsonArray) {
        return null;
    }

    @Override
    public JSONArray getElements(JSONObject jsonObj) {
        return jsonObj.getJSONArray("ownedElements");
    }

    @Override
    public boolean containsElements(JSONObject jsonObj) {
        return jsonObj.containsKey(OWNED_ELEMENTS.toString());
    }

    @Override
    public UMLType parseUMLType(JSONObject jsonObj) {
        if (jsonObj.containsKey(UML_TYPE_KEY.toString())) {
            return null;
        }
        return UMLType.covertFromString(jsonObj.getString(UML_TYPE_KEY.toString()));
    }
}
