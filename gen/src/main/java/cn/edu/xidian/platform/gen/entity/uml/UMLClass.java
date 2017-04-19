package cn.edu.xidian.platform.gen.entity.uml;

import java.util.ArrayList;

/**
 * Created by 费玥 on 2017/4/18.
 */

public class UMLClass extends UMLBase {

    private boolean isFinalSpecialization;

    private boolean isAbstract;

    private ArrayList<Attribute> atributes;

    private ArrayList<Opreation> opreations;

    private ArrayList<UMLClass> innerClasses;

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

    public ArrayList<Attribute> getAtributes() {
        return atributes;
    }

    public void setAtributes(ArrayList<Attribute> atributes) {
        this.atributes = atributes;
    }

    public ArrayList<Opreation> getOpreations() {
        return opreations;
    }

    public void setOpreations(ArrayList<Opreation> opreations) {
        this.opreations = opreations;
    }

    public ArrayList<UMLClass> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(ArrayList<UMLClass> innerClasses) {
        this.innerClasses = innerClasses;
    }
}
