package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017-4-20.
 */
public class UMLPackage extends UMLBase {

    private List<? super UMLClass> classes;

    private List<UMLInterface> interfaces;

    private List<UMLEnumeration> enumerations;

    public List<? super UMLClass> getClasses() {
        return classes;
    }

    public void setClasses(List<? super UMLClass> classes) {
        this.classes = classes;
    }

    public List<UMLInterface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<UMLInterface> interfaces) {
        this.interfaces = interfaces;
    }

    public List<UMLEnumeration> getEnumerations() {
        return enumerations;
    }

    public void setEnumerations(List<UMLEnumeration> enumerations) {
        this.enumerations = enumerations;
    }
}
