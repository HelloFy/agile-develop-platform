package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017/4/18.
 */

public class UMLClass extends UMLBase {

    private boolean isFinalSpecialization;

    private boolean isAbstract;

    private String stereotype;

    private List<UMLAttribute> attributes;

    private List<UMLOperation> UMLOperations;

    private UMLRelation umlRelation;

    public boolean isFinalSpecialization() {
        return isFinalSpecialization;
    }

    public void setFinalSpecialization(boolean finalSpecialization) {
        isFinalSpecialization = finalSpecialization;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public List<UMLAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<UMLAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<UMLOperation> getUMLOperations() {
        return UMLOperations;
    }

    public void setUMLOperations(List<UMLOperation> UMLOperations) {
        this.UMLOperations = UMLOperations;
    }

    public String getStereotype() {
        return stereotype;
    }

    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    public UMLRelation getUmlRelation() {
        return umlRelation;
    }

    public void setUmlRelation(UMLRelation umlRelation) {
        this.umlRelation = umlRelation;
    }
}
