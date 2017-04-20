package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017-4-20.
 */
public class UMLRelation extends UMLBase {

    private UMLClass parentClass;

    private List<UMLInterface> impInterfaces;

    private List<UMLClass> combinateClasses;

    private List<UMLClass> innerClasses;

    public UMLClass getParentClass() {
        return parentClass;
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

    public List<UMLClass> getCombinateClasses() {
        return combinateClasses;
    }

    public void setCombinateClasses(List<UMLClass> combinateClasses) {
        this.combinateClasses = combinateClasses;
    }

    public List<UMLClass> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(List<UMLClass> innerClasses) {
        this.innerClasses = innerClasses;
    }
}
