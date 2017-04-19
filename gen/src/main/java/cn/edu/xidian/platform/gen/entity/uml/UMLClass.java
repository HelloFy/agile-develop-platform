package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017/4/18.
 */

public class UMLClass extends UMLBase {

    private boolean isFinalSpecialization;

    private boolean isAbstract;

    private List<Attribute> atributes;

    private List<Opreation> opreations;

    private List<UMLClass> innerClasses;

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

    public List<Attribute> getAtributes() {
        return atributes;
    }

    public void setAtributes(List<Attribute> atributes) {
        this.atributes = atributes;
    }

    public List<Opreation> getOpreations() {
        return opreations;
    }

    public void setOpreations(List<Opreation> opreations) {
        this.opreations = opreations;
    }

    public List<UMLClass> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(List<UMLClass> innerClasses) {
        this.innerClasses = innerClasses;
    }
}
