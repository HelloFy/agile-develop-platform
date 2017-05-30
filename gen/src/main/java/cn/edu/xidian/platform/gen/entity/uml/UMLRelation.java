package cn.edu.xidian.platform.gen.entity.uml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李婧 on 2017-4-20.
 */
public class UMLRelation extends UMLBase {

    private UMLClass parentClass;

    private List<UMLInterface> impInterfaces;

    private List<UMLCompose> composes;

    private List<UMLClass> innerClasses;

    private List<UMLInterface> innerInterfaces;

    private List<UMLEnumeration> innerEnums;

    public UMLClass getParentClass() {
        return parentClass;
    }

    public List<UMLInterface> getInnerInterfaces() {
        return innerInterfaces;
    }

    public void setInnerInterfaces(List<UMLInterface> innerInterfaces) {
        this.innerInterfaces = innerInterfaces;
    }

    public List<UMLEnumeration> getInnerEnums() {
        return innerEnums;
    }

    public void setInnerEnums(List<UMLEnumeration> innerEnums) {
        this.innerEnums = innerEnums;
    }

    public static class UMLCompose extends UMLBase {

        private MultiplicityType multiplicitytype;

        private String fieldName;

        private UMLClass composeClass;

        private UMLInterface composeInterface;

        private UMLEnumeration composeEnum;

        public MultiplicityType getMultiplicity() {
            return multiplicitytype;
        }

        public void setMultiplicity(MultiplicityType multiplicitytype) {
            this.multiplicitytype = multiplicitytype;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public UMLClass getComposeClass() {
            return composeClass;
        }

        public void setComposeClass(UMLClass composeClass) {
            this.composeClass = composeClass;
        }

        public UMLInterface getComposeInterface() {
            return composeInterface;
        }

        public void setComposeInterface(UMLInterface composeInterface) {
            this.composeInterface = composeInterface;
        }

        public UMLEnumeration getComposeEnum() {
            return composeEnum;
        }

        public void setComposeEnum(UMLEnumeration composeEnum) {
            this.composeEnum = composeEnum;
        }

        public enum MultiplicityType {
            ONE("1"), ZERO_OR_ONE("0..1"), MANY("*"), ZERO_TO_MANY("0..*");
            private String multiplicity;
            private static Map<String, MultiplicityType> string2MultiplicityMap = new HashMap<>();

            static {
                for (MultiplicityType multiplicity : MultiplicityType.values()) {
                    string2MultiplicityMap.put(multiplicity.multiplicity, multiplicity);
                }
            }

            MultiplicityType(String multiplicity) {
                this.multiplicity = multiplicity;
            }

            public static MultiplicityType covertFromString(String multiplicity) {
                return string2MultiplicityMap.get(multiplicity);
            }
        }

        public enum Aggregationtype {
            COMPOSITE("composite"), SHARED("shared"), NONE("none");
            private String aggregation;
            private static Map<String, Aggregationtype> string2AggregationMap = new HashMap<>();

            static {
                for (Aggregationtype aggregation : Aggregationtype.values()) {
                    string2AggregationMap.put(aggregation.aggregation, aggregation);
                }
            }

            Aggregationtype(String aggregation) {
                this.aggregation = aggregation;
            }

            public static Aggregationtype convertFromString(String aggregation) {
                return string2AggregationMap.get(aggregation);
            }
        }

    }

    public void setParentClass(UMLClass parentClass) {
        this.parentClass = parentClass;
    }

    public List<UMLInterface> getImpInterfaces() {
        return impInterfaces;
    }

    public void setImpInterfaces(List<UMLInterface> impInterfaces) {
        this.impInterfaces = impInterfaces;
    }

    public List<UMLCompose> getComposes() {
        return composes;
    }

    public void setComposes(List<UMLCompose> composes) {
        this.composes = composes;
    }

    public List<UMLClass> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(List<UMLClass> innerClasses) {
        this.innerClasses = innerClasses;
    }

}
