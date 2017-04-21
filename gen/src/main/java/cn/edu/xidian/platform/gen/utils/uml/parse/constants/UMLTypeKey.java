package cn.edu.xidian.platform.gen.utils.uml.parse.constants;

/**
 * Created by 费玥 on 2017-4-21.
 */
public enum UMLTypeKey {

    UML_TYPE_KEY("_type"), OWNED_ELEMENTS("ownedElements");

    private String key;

    UMLTypeKey(String key) {
        this.key = key;
    }

    public String toString() {
        return key;
    }
}
