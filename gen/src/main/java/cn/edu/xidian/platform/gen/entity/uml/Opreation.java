package cn.edu.xidian.platform.gen.entity.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Opreation extends UMLBase {

    private ConcurrencyType concurrency;
    private boolean isStatic;
    private boolean isAbstract;
    private ArrayList<Paramter> paramterIn;
    private Paramter paramterOut;

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
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

        private JavaType type;
        private boolean isReadOnly;

        public JavaType getType() {
            return type;
        }

        public void setType(JavaType type) {
            this.type = type;
        }

        public boolean isReadOnly() {
            return isReadOnly;
        }

        public void setReadOnly(boolean readOnly) {
            isReadOnly = readOnly;
        }
    }
}
