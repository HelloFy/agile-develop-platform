package cn.edu.xidian.platform.gen.entity.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UMLOperation extends UMLBase {

    private ConcurrencyType concurrency = ConcurrencyType.SEQUENTIAL;
    private boolean statics;
    private boolean virtual;
    private ArrayList<Paramter> paramterIn;
    private Paramter paramterOut;

    public boolean isStatics() {
        return statics;
    }

    public void setStatics(boolean statics) {
        this.statics = statics;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public ConcurrencyType getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(ConcurrencyType concurrency) {
        this.concurrency = concurrency;
    }

    public static ConcurrencyType convertFromString(String concurrency) {
        return ConcurrencyType.convertFromString(concurrency);
    }

    public ArrayList<Paramter> getParamterIn() {
        return paramterIn;
    }

    public void setParamterIn(ArrayList<Paramter> paramterIn) {
        this.paramterIn = paramterIn;
    }

    public Paramter getParamterOut() {
        return paramterOut;
    }

    public void setParamterOut(Paramter paramterOut) {
        this.paramterOut = paramterOut;
    }

    private enum ConcurrencyType {
        SEQUENTIAL("sequential"), CONCURRENCY("concurrent");
        private String concurrency;
        private static Map<String, ConcurrencyType> string2ConcurrencyTypeMap = new HashMap<>();

        ConcurrencyType(String concurrency) {
            this.concurrency = concurrency;
        }

        static {
            for (ConcurrencyType concurrencyType : ConcurrencyType.values()) {
                string2ConcurrencyTypeMap.put(concurrencyType.concurrency, concurrencyType);
            }
        }

        public static ConcurrencyType convertFromString(String concurrency) {
            return string2ConcurrencyTypeMap.get(concurrency);
        }

    }

    public static class Paramter extends UMLBase {

        private JavaType javaType;
        private String otherType;
        private String otherTypePackageName;
        private boolean readOnly;
        private boolean list = false;
        private int arraySize = 0;

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

        public String getOtherType() {
            return otherType;
        }

        public void setOtherType(String otherType) {
            this.otherType = otherType;
        }

        public boolean isList() {
            return list;
        }

        public void setList(boolean list) {
            this.list = list;
        }

        public int getArraySize() {
            return arraySize;
        }

        public void setArraySize(int arraySize) {
            this.arraySize = arraySize;
        }

        public void setOtherTypePackageName(String otherTypePackageName) {
            this.otherTypePackageName = otherTypePackageName;
        }

        public String getOtherTypePackageName() {
            return otherTypePackageName;
        }

        public enum ParaType {
            IN("in"), RETURN("return");
            private String paraType;
            private static Map<String, ParaType> string2ParaTypeMap = new HashMap<>();

            static {
                for (ParaType paraType : ParaType.values()) {
                    string2ParaTypeMap.put(paraType.paraType, paraType);
                }
            }

            ParaType(String paraType) {
                this.paraType = paraType;
            }

            public static ParaType convertFromString(String paraType) {
                return string2ParaTypeMap.get(paraType);
            }
        }
    }
}
