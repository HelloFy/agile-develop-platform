package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017/4/18.
 */

public class UMLClass extends UMLBase implements JavaFileType {

    private boolean finalSpecialization = false;

    private boolean virtual = false;

    private String stereotype;

    private List<UMLAttribute> attributes;

    private List<UMLOperation> operations;

    private UMLRelation umlRelation;

    private String packageName;

    public boolean isFinalSpecialization() {
        return this.finalSpecialization;
    }

    public void setFinalSpecialization(boolean finalSpecialization) {
        this.finalSpecialization = finalSpecialization;
    }

    public boolean isVirtual() {
        return this.virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public List<UMLAttribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<UMLAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<UMLOperation> getOperations() {
        return this.operations;
    }

    public void setOperations(List<UMLOperation> operations) {
        this.operations = operations;
    }

    public String getStereotype() {
        return this.stereotype;
    }

    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    public UMLRelation getUmlRelation() {
        return this.umlRelation;
    }

    public void setUmlRelation(UMLRelation umlRelation) {
        this.umlRelation = umlRelation;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
