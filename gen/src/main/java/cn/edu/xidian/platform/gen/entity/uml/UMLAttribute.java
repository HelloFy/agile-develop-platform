package cn.edu.xidian.platform.gen.entity.uml;

public class UMLAttribute extends UMLBase {
    private boolean statics = false;
    private JavaType javaType;
    private boolean list = false;
    private int arraySize = 0;
    private String otherType;
    private String otherTypePackageName;
    private boolean readOnly = false;
    private DefaultValue defaultValue;
    private boolean derived = false;

    public static class DefaultValue {
        private ValueType valueType;
        private Object value;

        public ValueType getValueType() {
            return valueType;
        }

        public void setValueType(ValueType valueType) {
            this.valueType = valueType;
        }

        public enum ValueType {
            STRING("String"), NUMBER("Number");
            private String valueType;

            ValueType(String valueType) {
                this.valueType = valueType;
            }
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public boolean isStatics() {
        return statics;
    }

    public void setStatics(boolean statics) {
        this.statics = statics;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public DefaultValue getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(DefaultValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isDerived() {
        return derived;
    }

    public void setDerived(boolean derived) {
        this.derived = derived;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    public String getOtherTypePackageName() {
        return otherTypePackageName;
    }

    public void setOtherTypePackageName(String otherTypePackageName) {
        this.otherTypePackageName = otherTypePackageName;
    }
}
