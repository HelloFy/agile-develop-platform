package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 李婧 on 2017-4-20.
 */
public class UMLPackage extends UMLBase {

    private List<UMLClass> classes;

    private List<UMLInterface> interfaces;

    private List<UMLEnumeration> enumerations;

    private List<UMLModel> umlModels;

    private List<UMLPackage> packages;

    public List<UMLClass> getClasses() {
        return classes;
    }

    public void setClasses(List<UMLClass> classes) {
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

    public List<UMLModel> getUmlModels() {
        return umlModels;
    }

    public void setUmlModels(List<UMLModel> umlModels) {
        this.umlModels = umlModels;
    }

    public List<UMLPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<UMLPackage> packages) {
        this.packages = packages;
    }
}
