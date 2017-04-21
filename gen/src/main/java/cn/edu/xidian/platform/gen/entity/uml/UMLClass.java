package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017/4/18.
 */

public class UMLClass extends UMLBase {

    private boolean isFinalSpecialization;

    private boolean isAbstract;

    private String stereotype;

    private List<Attribute> attributes;

    private List<Opreation> opreations;

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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Opreation> getOpreations() {
        return opreations;
    }

    public void setOpreations(List<Opreation> opreations) {
        this.opreations = opreations;
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
