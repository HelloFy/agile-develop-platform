package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017/4/18.
 */
public class UMLInterface extends UMLBase implements JavaFileType {

    private List<UMLAttribute> attributes;
    private List<UMLOperation> operations;
    private UMLRelation umlRelation;
    private String packageName;


    public List<UMLAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<UMLAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<UMLOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<UMLOperation> operations) {
        this.operations = operations;
    }

    public UMLRelation getUmlRelation() {
        return this.umlRelation;
    }

    public void setUmlRelation(UMLRelation umlRelation) {
        this.umlRelation = umlRelation;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
