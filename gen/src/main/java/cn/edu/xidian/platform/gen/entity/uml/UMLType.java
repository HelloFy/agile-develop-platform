package cn.edu.xidian.platform.gen.entity.uml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 费玥 on 2017/4/18.
 */
public enum UMLType {
    UMLClass("UMLClass"), UMLPackage("UMLPackage"), UMLOperation("UMLOperation"),
    UMLInterface("UMLInterface"), UMLParameter("UMLParameter"), UMLAttribute("UMLAttribute"),
    UMLModel("UMLModel"), UMLGeneralization("UMLGeneralization"), UMLInterfaceRealization("UMLInterfaceRealization"),
    UMLEnumeration("UMLEnumeration"), UMLAssociation("UMLAssociation"), UMLAssociationEnd("UMLAssociationEnd");

    private String _type;
    private static Map<String, UMLType> string2UMLTypeMap = new HashMap<>();

    UMLType(String _type) {
        this._type = _type;
    }

    static {
        for (UMLType umlType : UMLType.values()) {
            string2UMLTypeMap.put(umlType._type, umlType);
        }
    }

    public static UMLType covertFromString(String _type) {
        return string2UMLTypeMap.get(_type);
    }
}

