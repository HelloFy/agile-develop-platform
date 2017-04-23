package cn.edu.xidian.platform.gen.entity.uml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 费玥 on 2017/4/18.
 */
public enum JavaType {
    Integer("Integer"), INT("int"), String("String"), Character("Character"), Char("char"),
    ByteO("Byte"), Byte("byte"), DoubleO("Double"), Double("double"), FloatO("Float"),
    Float("float"), LongO("Long"), Long("long"), ShortO("Short"), Short("short"),
    BooleanO("Boolean"), Boolean("boolean"), Object("Object");

    private String realType;

    private static Map<String, JavaType> string2JavaTypeMap = new HashMap<>();

    static {
        for (JavaType javaType : JavaType.values()) {
            string2JavaTypeMap.put(javaType.realType, javaType);
        }
    }

    JavaType(String realType) {
        this.realType = realType;
    }

    public String getRealType() {
        return this.realType;
    }

    public static JavaType convertFromString(String type) {
        return string2JavaTypeMap.get(type);
    }
}
