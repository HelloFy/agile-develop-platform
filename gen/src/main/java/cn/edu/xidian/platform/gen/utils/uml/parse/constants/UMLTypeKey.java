package cn.edu.xidian.platform.gen.utils.uml.parse.constants;

/**
 * Created by 李婧 on 2017-4-21.
 */
public enum UMLTypeKey {

    UML_TYPE_KEY("_type"), OWNED_ELEMENTS("ownedElements"), TARGET("target"),
    PARENT_ID("pId"), END_1("end1"), END_2("end2"), AGGREGATION("aggregation"),
    NAME("name"), MULTIPLICITY("multiplicity"), REFERENCE("reference"),
    ID("_id"), VISIBILITY("visibility"), DOCUMENTION("documention"),
    STEREOTYPE("stereotype"), ANNOTATION_TYPE("annotationType"),
    IS_ABSTRACT("isAbstract"), IS_FINALSPECIALIZATION("isFinalSpecialization"),
    LITERALS("literals"), ATTRIBUTES("attributes"), IS_STATIC("isStatic"),
    IS_READONLY("isReadOnly"), DEFAULT_VALUE("defaultValue"), IS_DERIVED("isDerived"),
    JAVA_TYPE("type"), OPERATIONS("operations"), CONCURRENCY("concurrency"),
    PARAMETERS("parameters"), DIRECTION("direction"), PARENT("_parent");

    private String key;

    UMLTypeKey(String key) {
        this.key = key;
    }

    public String toString() {
        return key;
    }
}
