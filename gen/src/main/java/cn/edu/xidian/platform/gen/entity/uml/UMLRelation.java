package cn.edu.xidian.platform.gen.entity.uml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 费玥 on 2017-4-20.
 */
public class UMLRelation extends UMLBase {

    private UMLClass parentClass;

    private List<UMLInterface> impInterfaces;

    private List<UMLCompose> combinateClasses;

    private List<UMLClass> innerClasses;

    public UMLClass getParentClass() {
        return parentClass;
    }

    public static class UMLCompose extends UMLBase {

        private MultiplicityType multiplicitytype;

        private String filedNames;

        public MultiplicityType getMultiplicity() {
            return multiplicitytype;
        }

        public void setMultiplicity(MultiplicityType multiplicitytype) {
            this.multiplicitytype = multiplicitytype;
        }

        public String getFiledNames() {
            return filedNames;
        }

        public void setFiledNames(String filedNames) {
            this.filedNames = filedNames;
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

    public List<UMLCompose> getCombinateClasses() {
        return combinateClasses;
    }

    public void setCombinateClasses(List<UMLCompose> combinateClasses) {
        this.combinateClasses = combinateClasses;
    }

    public List<UMLClass> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(List<UMLClass> innerClasses) {
        this.innerClasses = innerClasses;
    }

}
