package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017-4-21.
 */
public class UMLModel extends UMLBase {

    private List<UMLPackage> packages;

    private List<UMLClass> umlClasses;

    private List<UMLEnumeration> umlEnumerations;

    private List<UMLInterface> umlInterfaces;

    private List<UMLModel> umlModels;

    public List<UMLPackage> getPackages() {
        return packages;
    }


    public void setPackages(List<UMLPackage> packages) {
        this.packages = packages;
    }

    public List<UMLClass> getUmlClasses() {
        return umlClasses;
    }

    public void setUmlClasses(List<UMLClass> umlClasses) {
        this.umlClasses = umlClasses;
    }

    public List<UMLEnumeration> getUmlEnumerations() {
        return umlEnumerations;
    }

    public void setUmlEnumerations(List<UMLEnumeration> umlEnumerations) {
        this.umlEnumerations = umlEnumerations;
    }

    public List<UMLInterface> getUmlInterfaces() {
        return umlInterfaces;
    }

    public void setUmlInterfaces(List<UMLInterface> umlInterfaces) {
        this.umlInterfaces = umlInterfaces;
    }

    public List<UMLModel> getUmlModels() {
        return umlModels;
    }

    public void setUmlModels(List<UMLModel> umlModels) {
        this.umlModels = umlModels;
    }
}
