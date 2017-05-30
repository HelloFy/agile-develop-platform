package cn.edu.xidian.platform.gen.utils.uml.parse.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.edu.xidian.platform.commons.utils.StringUtils;
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

import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.AGGREGATION;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.ANNOTATION_TYPE;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.ATTRIBUTES;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.CONCURRENCY;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.DEFAULT_VALUE;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.DIRECTION;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.DOCUMENTION;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.END_1;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.END_2;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.ID;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.IS_ABSTRACT;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.IS_FINALSPECIALIZATION;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.IS_READONLY;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.IS_STATIC;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.JAVA_TYPE;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.LITERALS;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.MULTIPLICITY;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.NAME;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.OPERATIONS;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.OWNED_ELEMENTS;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.PARAMETERS;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.PARENT;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.PARENT_ID;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.REFERENCE;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.STEREOTYPE;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.TARGET;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.UML_TYPE_KEY;
import static cn.edu.xidian.platform.gen.utils.uml.parse.constants.UMLTypeKey.VISIBILITY;

/**
 * Created by 李婧 on 2017-4-21.
 */
public class DefaultProcessor implements Processor<JSONObject, JSONArray>, ParseProcessor<JSONObject, JSONArray> {

    private static final ThreadLocal<HashMap<String, UMLClass>> classesCachedMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, UMLInterface>> interfacesCachedMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, UMLEnumeration>> enumsCachedMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLAttribute>>> otherTypeAttrRefPendingMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLOperation.Paramter>>> otherTypeOpRefPendingMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLRelation>>> classRefPendingMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLRelation.UMLCompose>>> composeRefPendingMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLRelation>>> interfaceImpRefPendingMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLRelation.UMLCompose>>> composeClassRefPendingMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLRelation.UMLCompose>>> composeInterfaceRefPendingMap = new ThreadLocal<>();

    private static final ThreadLocal<HashMap<String, List<UMLRelation.UMLCompose>>> composeEnumRefPendingMap = new ThreadLocal<>();


    private static void initCachedMap() {
        if (classesCachedMap.get() == null) {
            classesCachedMap.set(new HashMap<>());
        }
        if (interfacesCachedMap.get() == null) {
            interfacesCachedMap.set(new HashMap<>());
        }
        if (enumsCachedMap.get() == null) {
            enumsCachedMap.set(new HashMap<>());
        }
    }

    static {
        initCachedMap();
    }

    private void handleRef(UMLType type) {
        if (otherTypeOpRefPendingMap.get() == null
                && otherTypeAttrRefPendingMap.get() == null
                && composeRefPendingMap.get() == null
                && classRefPendingMap.get() == null
                && interfaceImpRefPendingMap.get() == null) {
            return;
        }
        Map<String, ? extends UMLBase> cachedMap = null;
        switch (type) {
            case UMLClass: {
                cachedMap = classesCachedMap.get();
                break;
            }
            case UMLInterface: {
                cachedMap = interfacesCachedMap.get();
                break;
            }
            case UMLEnumeration: {
                cachedMap = enumsCachedMap.get();
            }
        }
        if (cachedMap == null) {
            return;
        }
        if (otherTypeAttrRefPendingMap.get() != null && !otherTypeAttrRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLAttribute>>> i = otherTypeAttrRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    List<UMLAttribute> pendings = otherTypeAttrRefPendingMap.get().get(refKey);
                    for (UMLAttribute attribute : pendings) {
                        attribute.setOtherType(cachedMap.get(refKey).getName());
                        switch (cachedMap.get(refKey).getUmlType()) {
                            case UMLClass: {
                                attribute.setOtherTypePackageName(((UMLClass) cachedMap.get(refKey)).getPackageName());
                                break;
                            }
                            case UMLInterface: {
                                attribute.setOtherTypePackageName(((UMLInterface) cachedMap.get(refKey)).getPackageName());
                                break;
                            }
                            case UMLEnumeration: {
                                attribute.setOtherTypePackageName(((UMLEnumeration) cachedMap.get(refKey)).getPackageName());
                                break;
                            }
                        }
                    }
                    i.remove();
                }
            }
        }
        if (otherTypeOpRefPendingMap.get() != null && !otherTypeOpRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLOperation.Paramter>>> i = otherTypeOpRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    List<UMLOperation.Paramter> pendings = otherTypeOpRefPendingMap.get().get(refKey);
                    for (UMLOperation.Paramter paramter : pendings) {
                        paramter.setOtherType(cachedMap.get(refKey).getName());
                        switch (cachedMap.get(refKey).getUmlType()) {
                            case UMLClass: {
                                paramter.setOtherTypePackageName(((UMLClass) cachedMap.get(refKey)).getPackageName());
                                break;
                            }
                            case UMLInterface: {
                                paramter.setOtherTypePackageName(((UMLInterface) cachedMap.get(refKey)).getPackageName());
                                break;
                            }
                            case UMLEnumeration: {
                                paramter.setOtherTypePackageName(((UMLEnumeration) cachedMap.get(refKey)).getPackageName());
                                break;
                            }
                        }
                    }
                    i.remove();
                }
            }
        }

        if (interfaceImpRefPendingMap.get() != null && !interfaceImpRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLRelation>>> i = interfaceImpRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    List<UMLRelation> relations = interfaceImpRefPendingMap.get().get(refKey);
                    for (UMLRelation umlRelation : relations) {
                        umlRelation.getImpInterfaces().add((UMLInterface) cachedMap.get(refKey));
                    }
                    i.remove();
                }
            }
        }

        if (classRefPendingMap.get() != null && !classRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLRelation>>> i = classRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    for (UMLRelation umlRelation : classRefPendingMap.get().get(refKey)) {
                        umlRelation.setParentClass((UMLClass) cachedMap.get(refKey));
                    }
                    i.remove();
                }
            }
        }

        if (composeRefPendingMap.get() != null && !composeRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLRelation.UMLCompose>>> i = composeRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    for (UMLRelation.UMLCompose umlCompose : composeRefPendingMap.get().get(refKey)) {
                        switch (cachedMap.get(refKey).getUmlType()) {
                            case UMLClass: {
                                ((UMLClass) cachedMap.get(refKey)).getUmlRelation().getComposes().add(umlCompose);
                                break;
                            }
                            case UMLInterface: {
                                ((UMLInterface) cachedMap.get(refKey)).getUmlRelation().getComposes().add(umlCompose);
                                break;
                            }
                        }
                        i.remove();
                    }
                }
            }
        }

        if (composeClassRefPendingMap.get() != null && !composeClassRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLRelation.UMLCompose>>> i = composeClassRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    for (UMLRelation.UMLCompose compose : composeClassRefPendingMap.get().get(refKey)) {
                        compose.setComposeClass((UMLClass) cachedMap.get(refKey));
                    }
                    i.remove();
                }
            }
        }

        if (composeInterfaceRefPendingMap.get() != null && !composeInterfaceRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLRelation.UMLCompose>>> i = composeInterfaceRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    for (UMLRelation.UMLCompose compose : composeInterfaceRefPendingMap.get().get(refKey)) {
                        compose.setComposeInterface((UMLInterface) cachedMap.get(refKey));
                    }
                    i.remove();
                }
            }

        }

        if (composeEnumRefPendingMap.get() != null && !composeEnumRefPendingMap.get().isEmpty()) {
            for (Iterator<Map.Entry<String, List<UMLRelation.UMLCompose>>> i = composeEnumRefPendingMap.get().entrySet().iterator(); i.hasNext(); ) {
                String refKey = i.next().getKey();
                if (cachedMap.containsKey(refKey)) {
                    for (UMLRelation.UMLCompose compose : composeEnumRefPendingMap.get().get(refKey)) {
                        compose.setComposeEnum((UMLEnumeration) cachedMap.get(refKey));
                    }
                    i.remove();
                }
            }
        }


    }

    @Override
    public <E extends UMLBase> void parseUMLBase(E umlBase, JSONObject jsonObj) {
        if (jsonObj.containsKey(ID.toString()))
            umlBase.setId(jsonObj.getString(ID.toString()));
        if (jsonObj.containsKey(NAME.toString()))
            umlBase.setName(jsonObj.getString(NAME.toString()));
        if (jsonObj.containsKey(VISIBILITY.toString()))
            umlBase.setVisibility(jsonObj.getString(VISIBILITY.toString()));
        if (jsonObj.containsKey(DOCUMENTION.toString())) {
            umlBase.setDocumention(jsonObj.getString(DOCUMENTION.toString()));
        }
        if (jsonObj.containsKey(UML_TYPE_KEY.toString())) {
            umlBase.setUmlType(UMLType.covertFromString(jsonObj.getString(UML_TYPE_KEY.toString())));
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
        if (jsonObj.containsKey(STEREOTYPE.toString())) {
            if (jsonObj.getString(STEREOTYPE.toString()).equals(ANNOTATION_TYPE.toString())) {
                UMLAnnotionType umlAnnotionType = new UMLAnnotionType();
                parseUMLBase(umlAnnotionType, jsonObj);
                umlAnnotionType.setAttributes(parseUMLAttribute(jsonObj));
                umlAnnotionType.setOperations(parseUMLOperation(jsonObj));
                umlAnnotionType.setStereotype("annotionType");
                return umlAnnotionType;
            }
        }
        UMLClass umlClass = new UMLClass();
        parseUMLBase(umlClass, jsonObj);
        if (classesCachedMap.get() == null) {
            initCachedMap();
        }
        if (classesCachedMap.get().containsKey(umlClass.getId())) {
            return null;
        }
        classesCachedMap.get().put(umlClass.getId(), umlClass);
        if (jsonObj.containsKey(IS_ABSTRACT.toString())) {
            umlClass.setVirtual(jsonObj.getBoolean(IS_ABSTRACT.toString()));
        }
        if (jsonObj.containsKey(IS_FINALSPECIALIZATION.toString())) {
            umlClass.setFinalSpecialization(jsonObj.getBoolean(IS_FINALSPECIALIZATION.toString()));
        }
        umlClass.setAttributes(parseUMLAttribute(jsonObj));
        umlClass.setOperations(parseUMLOperation(jsonObj));
        handleRef(UMLType.UMLClass);
        return umlClass;
    }

    @Override
    public UMLInterface parseUMLInterface(JSONObject jsonObj) {
        UMLInterface umlInterface = new UMLInterface();
        parseUMLBase(umlInterface, jsonObj);
        if (interfacesCachedMap.get() == null) {
            initCachedMap();
        }
        if (interfacesCachedMap.get().containsKey(umlInterface.getId())) {
            return null;
        }
        interfacesCachedMap.get().put(umlInterface.getId(), umlInterface);
        umlInterface.setAttributes(parseUMLAttribute(jsonObj));
        umlInterface.setOperations(parseUMLOperation(jsonObj));
        handleRef(UMLType.UMLInterface);
        return umlInterface;
    }

    @Override
    public UMLEnumeration parseUMLEnumeration(JSONObject jsonObj) {
        UMLEnumeration umlEnumeration = new UMLEnumeration();
        parseUMLBase(umlEnumeration, jsonObj);
        if (enumsCachedMap.get() == null) {
            initCachedMap();
        }
        if (enumsCachedMap.get().containsKey(umlEnumeration.getId())) {
            return null;
        }
        List<UMLEnumeration.UMLEnumerationLiteral> literals = new ArrayList<>();
        if (jsonObj.containsKey(LITERALS.toString())) {
            JSONArray var1 = jsonObj.getJSONArray(LITERALS.toString());
            for (Object var2 : var1) {
                JSONObject var4 = (JSONObject) var2;
                UMLEnumeration.UMLEnumerationLiteral literal = new UMLEnumeration.UMLEnumerationLiteral();
                parseUMLBase(literal, var4);
                literals.add(literal);
            }
        }
        umlEnumeration.setLiterals(literals);
        handleRef(UMLType.UMLEnumeration);
        return umlEnumeration;
    }

    private boolean checkCache(String key) {
        return classesCachedMap.get().containsKey(key) || interfacesCachedMap.get().containsKey(key) || enumsCachedMap.get().containsKey(key);
    }

    @Override
    public List<UMLAttribute> parseUMLAttribute(JSONObject jsonObj) {
        ArrayList<UMLAttribute> attributes = new ArrayList<>();
        if (jsonObj.containsKey(ATTRIBUTES.toString())) {
            JSONArray var1 = jsonObj.getJSONArray(ATTRIBUTES.toString());
            for (Object var2 : var1) {
                JSONObject var3 = (JSONObject) var2;
                UMLAttribute attribute = new UMLAttribute();
                parseUMLBase(attribute, var3);
                attribute.setStatics(var3.getBoolean(IS_STATIC.toString()));
                attribute.setReadOnly(var3.getBoolean(IS_READONLY.toString()));
                if (var3.containsKey(DEFAULT_VALUE.toString())) {
                    String defaultValue = var3.getString(DEFAULT_VALUE.toString());
                    UMLAttribute.DefaultValue value = new UMLAttribute.DefaultValue();
                    if (StringUtils.isNumeric(defaultValue)) {
                        value.setValueType(UMLAttribute.DefaultValue.ValueType.NUMBER);
                        value.setValue(defaultValue);
                    } else {
                        value.setValueType(UMLAttribute.DefaultValue.ValueType.STRING);
                        value.setValue(defaultValue);
                    }
                    attribute.setDefaultValue(value);
                }
                if (var3.containsKey(MULTIPLICITY.toString())) {
                    String multiplicity = var3.getString(MULTIPLICITY.toString());
                    if (StringUtils.isNumeric(multiplicity)) {
                        attribute.setArraySize(Integer.valueOf(multiplicity));
                    } else {
                        attribute.setList(true);
                    }
                }
                String type = var3.getString(JAVA_TYPE.toString());
                if (type == null) {
                    attribute.setJavaType(JavaType.Object);
                } else if (JavaType.convertFromString(type.trim()) != null) {
                    attribute.setJavaType(JavaType.convertFromString(type));
                } else {
                    JSONObject var4 = var3.getJSONObject(JAVA_TYPE.toString());
                    String refTypeId = var4.getString(PARENT_ID.toString());
                    if (classesCachedMap.get().containsKey(refTypeId)) {
                        attribute.setOtherType(classesCachedMap.get().get(refTypeId).getName());
                    } else if (interfacesCachedMap.get().containsKey(refTypeId)) {
                        attribute.setOtherType(interfacesCachedMap.get().get(refTypeId).getName());
                    } else if (enumsCachedMap.get().containsKey(refTypeId)) {
                        attribute.setOtherType(enumsCachedMap.get().get(refTypeId).getName());
                    } else {
                        if (otherTypeAttrRefPendingMap.get() == null) {
                            otherTypeAttrRefPendingMap.set(new HashMap<>());
                        }
                        if (otherTypeAttrRefPendingMap.get().containsKey(refTypeId)) {
                            otherTypeAttrRefPendingMap.get().get(refTypeId).add(attribute);
                        } else {
                            List<UMLAttribute> pendingLists = new ArrayList<UMLAttribute>();
                            pendingLists.add(attribute);
                            otherTypeAttrRefPendingMap.get().put(refTypeId, pendingLists);
                        }
                    }
                }
                attributes.add(attribute);
            }
        }
        return attributes;
    }

    @Override
    public List<UMLOperation> parseUMLOperation(JSONObject jsonObj) {
        ArrayList<UMLOperation> operations = new ArrayList<>();
        if (jsonObj.containsKey(OPERATIONS.toString())) {
            JSONArray var1 = jsonObj.getJSONArray(OPERATIONS.toString());
            for (Object var2 : var1) {
                JSONObject var3 = (JSONObject) var2;
                UMLOperation operation = new UMLOperation();
                parseUMLBase(operation, var3);
                if (var3.containsKey(IS_ABSTRACT.toString())) {
                    operation.setVirtual(var3.getBoolean(IS_ABSTRACT.toString()));
                }
                if (var3.containsKey(IS_STATIC.toString())) {
                    operation.setStatics(var3.getBoolean(IS_STATIC.toString()));
                }
                if (var3.containsKey(CONCURRENCY.toString())) {
                    String concurrency = var3.getString(CONCURRENCY.toString());
                    operation.setConcurrency(UMLOperation.convertFromString(concurrency));
                }
                if (var3.containsKey(PARAMETERS.toString())) {
                    ArrayList<UMLOperation.Paramter> paramtersIn = new ArrayList<>();
                    JSONArray var4 = var3.getJSONArray(PARAMETERS.toString());
                    for (Object var5 : var4) {
                        JSONObject var6 = (JSONObject) var5;
                        UMLOperation.Paramter parameter = new UMLOperation.Paramter();
                        parseUMLBase(parameter, var6);
                        parameter.setReadOnly(var6.getBoolean(IS_READONLY.toString()));
                        if (var6.containsKey(MULTIPLICITY.toString())) {
                            String multiplicity = var6.getString(MULTIPLICITY.toString());
                            if (StringUtils.isNumeric(multiplicity)) {
                                parameter.setArraySize(Integer.valueOf(multiplicity));
                            } else {
                                parameter.setList(true);
                            }
                        }
                        String type = var6.getString(JAVA_TYPE.toString());
                        JavaType javaType = JavaType.convertFromString(type);
                        if (javaType != null) {
                            parameter.setJavaType(javaType);
                        } else {
                            JSONObject var7 = var6.getJSONObject(JAVA_TYPE.toString());
                            String refTypeId = var7.getString(PARENT_ID.toString());
                            if (classesCachedMap.get().containsKey(refTypeId)) {
                                parameter.setOtherType(classesCachedMap.get().get(refTypeId).getName());
                            } else if (interfacesCachedMap.get().containsKey(refTypeId)) {
                                parameter.setOtherType(interfacesCachedMap.get().get(refTypeId).getName());
                            } else if (enumsCachedMap.get().containsKey(refTypeId)) {
                                parameter.setOtherType(enumsCachedMap.get().get(refTypeId).getName());
                            } else {
                                if (otherTypeOpRefPendingMap.get() == null) {
                                    otherTypeOpRefPendingMap.set(new HashMap<>());
                                }
                                if (otherTypeOpRefPendingMap.get().containsKey(refTypeId)) {
                                    otherTypeOpRefPendingMap.get().get(refTypeId).add(parameter);
                                } else {
                                    List<UMLOperation.Paramter> pendingLists = new ArrayList<>();
                                    pendingLists.add(parameter);
                                    otherTypeOpRefPendingMap.get().put(refTypeId, pendingLists);
                                }
                            }
                        }
                        String paraDirection = var6.getString(DIRECTION.toString());
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
    public UMLRelation parseUMLRelation(JSONArray jsonArray, UMLType clzOfIface) {
        UMLRelation relation = new UMLRelation();
        List<UMLClass> innerClasses = new ArrayList<>();
        List<UMLInterface> impInterfaces = new ArrayList<>();
        List<UMLInterface> innerInterfaces = new ArrayList<>();
        List<UMLEnumeration> innerEnums = new ArrayList<>();
        UMLClass parentClass = null;
        List<UMLRelation.UMLCompose> composes = new ArrayList<>();
        for (Object var1 : jsonArray) {
            JSONObject var2 = (JSONObject) var1;
            String _type = var2.getString(UML_TYPE_KEY.toString());
            UMLType umlType = UMLType.covertFromString(_type);
            switch (umlType) {
                case UMLClass: {
                    UMLClass umlClass = parseUMLClass(var2);
                    if (umlClass.getName().equals("Class2")) {
                        System.out.println();
                    }
                    classesCachedMap.get().put(umlClass.getId(), umlClass);
                    if (containsElements(var2)) {
                        umlClass.setUmlRelation(parseUMLRelation(getElements(var2), UMLType.UMLClass));
                    }
                    innerClasses.add(umlClass);
                    break;
                }
                case UMLInterface: {
                    UMLInterface umlInterface = parseUMLInterface(var2);
                    interfacesCachedMap.get().put(umlInterface.getId(), umlInterface);
                    if (containsElements(var2)) {
                        umlInterface.setUmlRelation(parseUMLRelation(getElements(var2), UMLType.UMLInterface));
                    }
                    innerInterfaces.add(umlInterface);
                    break;
                }
                case UMLEnumeration: {
                    UMLEnumeration umlEnumeration = parseUMLEnumeration(var2);
                    enumsCachedMap.get().put(umlEnumeration.getId(), umlEnumeration);
                    if (containsElements(var2)) {
                        umlEnumeration.setUmlRelation(parseUMLRelation(getElements(var2), UMLType.UMLEnumeration));
                    }
                    innerEnums.add(umlEnumeration);
                    break;
                }
                case UMLGeneralization: {
                    JSONObject var3 = var2.getJSONObject(TARGET.toString());
                    switch (clzOfIface) {
                        case UMLClass: {
                            String refKeyId = var3.getString(PARENT_ID.toString());
                            if (checkCache(refKeyId)) {
                                parentClass = classesCachedMap.get().get(refKeyId);
                            } else {
                                if (classRefPendingMap.get() == null) {
                                    classRefPendingMap.set(new HashMap<>());
                                }
                                if (classRefPendingMap.get().containsKey(refKeyId)) {
                                    classRefPendingMap.get().get(refKeyId).add(relation);
                                } else {
                                    List<UMLRelation> pendings = new ArrayList<>();
                                    pendings.add(relation);
                                    classRefPendingMap.get().put(refKeyId, pendings);
                                }
                            }
                            break;
                        }
                        case UMLInterface: {
                            String refKeyId = var3.getString(PARENT_ID.toString());
                            if (checkCache(refKeyId)) {
                                impInterfaces.add(interfacesCachedMap.get().get(refKeyId));
                            } else {
                                if (interfaceImpRefPendingMap.get() == null) {
                                    interfaceImpRefPendingMap.set(new HashMap<>());
                                }
                                if (interfaceImpRefPendingMap.get().containsKey(refKeyId)) {
                                    interfaceImpRefPendingMap.get().get(refKeyId).add(relation);
                                } else {
                                    List<UMLRelation> pendings = new ArrayList<>();
                                    pendings.add(relation);
                                    interfaceImpRefPendingMap.get().put(refKeyId, pendings);
                                }
                            }
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                case UMLInterfaceRealization: {
                    JSONObject var3 = var2.getJSONObject(TARGET.toString());
                    String refKeyId = var3.getString(PARENT_ID.toString());
                    if (checkCache(refKeyId)) {
                        impInterfaces.add(interfacesCachedMap.get().get(refKeyId));
                    } else {
                        if (interfaceImpRefPendingMap.get() == null) {
                            interfaceImpRefPendingMap.set(new HashMap<>());
                        }
                        if (interfaceImpRefPendingMap.get().containsKey(refKeyId)) {
                            interfaceImpRefPendingMap.get().get(refKeyId).add(relation);
                        } else {
                            List<UMLRelation> pendings = new ArrayList<>();
                            pendings.add(relation);
                            interfaceImpRefPendingMap.get().put(refKeyId, pendings);
                        }
                    }
                    break;
                }
                case UMLAssociation: {
                    JSONObject var4 = var2.getJSONObject(END_1.toString());
                    JSONObject var5 = var2.getJSONObject(END_2.toString());
                    UMLRelation.UMLCompose.Aggregationtype aggregationType =
                            UMLRelation.UMLCompose.Aggregationtype.convertFromString(var5.getString(AGGREGATION.toString()));
                    switch (aggregationType) {
                        case NONE: {
                            break;
                        }
                        case SHARED:
                        case COMPOSITE: {
                            UMLRelation.UMLCompose umlCompose = new UMLRelation.UMLCompose();
                            switch (clzOfIface) {
                                case UMLClass: {
                                    String refKey = var4.getJSONObject(PARENT.toString()).getString(ID.toString());
                                    if (checkCache(refKey)) {
                                        umlCompose.setComposeClass(classesCachedMap.get().get(refKey));
                                    } else {
                                        if (composeClassRefPendingMap.get() == null) {
                                            composeClassRefPendingMap.set(new HashMap<>());
                                        }
                                        if (composeClassRefPendingMap.get().containsKey(refKey)) {
                                            composeClassRefPendingMap.get().get(refKey).add(umlCompose);
                                        } else {
                                            List<UMLRelation.UMLCompose> pendings = new ArrayList<>();
                                            pendings.add(umlCompose);
                                            composeClassRefPendingMap.get().put(refKey, pendings);
                                        }
                                    }
                                    break;
                                }
                                case UMLInterface: {
                                    String refKey = var4.getJSONObject(PARENT.toString()).getString(ID.toString());
                                    if (checkCache(refKey)) {
                                        umlCompose.setComposeInterface(interfacesCachedMap.get().get(refKey));
                                    } else {
                                        if (composeInterfaceRefPendingMap.get() == null) {
                                            composeInterfaceRefPendingMap.set(new HashMap<>());
                                        }
                                        if (composeInterfaceRefPendingMap.get().containsKey(refKey)) {
                                            composeInterfaceRefPendingMap.get().get(refKey).add(umlCompose);
                                        } else {
                                            List<UMLRelation.UMLCompose> pendings = new ArrayList<>();
                                            pendings.add(umlCompose);
                                            composeInterfaceRefPendingMap.get().put(refKey, pendings);
                                        }
                                    }
                                    break;
                                }
                                case UMLEnumeration: {
                                    String refKey = var4.getJSONObject(PARENT.toString()).getString(ID.toString());
                                    if (checkCache(refKey)) {
                                        umlCompose.setComposeEnum(enumsCachedMap.get().get(refKey));
                                    } else {
                                        if (composeEnumRefPendingMap.get() == null) {
                                            composeEnumRefPendingMap.set(new HashMap<>());
                                        }
                                        if (composeEnumRefPendingMap.get().containsKey(refKey)) {
                                            composeEnumRefPendingMap.get().get(refKey).add(umlCompose);
                                        } else {
                                            List<UMLRelation.UMLCompose> pendings = new ArrayList<>();
                                            pendings.add(umlCompose);
                                            composeEnumRefPendingMap.get().put(refKey, pendings);
                                        }
                                    }
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                            umlCompose.setFieldName(var4.getString(NAME.toString()));
                            umlCompose.setMultiplicity(UMLRelation.UMLCompose.MultiplicityType.covertFromString(var4.getString(MULTIPLICITY.toString())));
                            JSONObject var6 = var5.getJSONObject(REFERENCE.toString());
                            String refKeyId = var6.getString(PARENT_ID.toString());
                            if (classesCachedMap.get().containsKey(refKeyId)) {
                                UMLClass umlClass = classesCachedMap.get().get(var6.getString(PARENT_ID.toString()));
                                umlClass.getUmlRelation().getComposes().add(umlCompose);
                            } else if (interfacesCachedMap.get().containsKey(refKeyId)) {
                                UMLInterface umlInterface = interfacesCachedMap.get().get(var6.getString(PARENT_ID.toString()));
                                umlInterface.getUmlRelation().getComposes().add(umlCompose);
                            } else {
                                if (composeRefPendingMap.get() == null) {
                                    composeRefPendingMap.set(new HashMap<>());
                                }
                                if (composeRefPendingMap.get().containsKey(refKeyId)) {
                                    composeRefPendingMap.get().get(refKeyId).add(umlCompose);
                                } else {
                                    List<UMLRelation.UMLCompose> pendings = new ArrayList<>();
                                    pendings.add(umlCompose);
                                    composeRefPendingMap.get().put(refKeyId, pendings);
                                }
                            }
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                default: {
                    break;
                }
            }

        }
        relation.setInnerClasses(innerClasses);
        relation.setParentClass(parentClass);
        relation.setInnerInterfaces(innerInterfaces);
        relation.setImpInterfaces(impInterfaces);
        relation.setInnerEnums(innerEnums);
        relation.setComposes(composes);
        return relation;
    }

    @Override
    public JSONArray getElements(JSONObject jsonObj) {
        return jsonObj.getJSONArray(OWNED_ELEMENTS.toString());
    }

    @Override
    public boolean containsElements(JSONObject jsonObj) {
        return jsonObj.containsKey(OWNED_ELEMENTS.toString());
    }

    @Override
    public UMLType parseUMLType(JSONObject jsonObj) {
        if (!jsonObj.containsKey(UML_TYPE_KEY.toString())) {
            return null;
        }
        return UMLType.covertFromString(jsonObj.getString(UML_TYPE_KEY.toString()));
    }

    public void clearCache() {
        if (classesCachedMap.get() != null) {
            classesCachedMap.get().clear();
        }
        if (classRefPendingMap.get() != null) {
            classRefPendingMap.get().clear();
        }
        if (interfacesCachedMap.get() != null) {
            interfacesCachedMap.get().clear();
        }
        if (enumsCachedMap.get() != null) {
            enumsCachedMap.get().clear();
        }
        if (otherTypeAttrRefPendingMap.get() != null) {
            otherTypeAttrRefPendingMap.get().clear();
        }
        if (otherTypeOpRefPendingMap.get() != null) {
            otherTypeOpRefPendingMap.get().clear();
        }
        if (interfaceImpRefPendingMap.get() != null) {
            interfaceImpRefPendingMap.get().clear();
        }
        if (composeEnumRefPendingMap.get() != null) {
            composeEnumRefPendingMap.get().clear();
        }
        if (composeClassRefPendingMap.get() != null) {
            composeClassRefPendingMap.get().clear();
        }
        if (composeInterfaceRefPendingMap.get() != null) {
            composeInterfaceRefPendingMap.get().clear();
        }
        if (composeRefPendingMap.get() != null) {
            composeRefPendingMap.get().clear();
        }
        if (interfaceImpRefPendingMap.get() != null) {
            interfaceImpRefPendingMap.get().clear();
        }
    }
}
