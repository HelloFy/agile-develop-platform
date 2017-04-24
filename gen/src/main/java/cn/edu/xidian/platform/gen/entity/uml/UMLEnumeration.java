package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017-4-20.
 */
public class UMLEnumeration extends UMLBase implements JavaFileType {

    private List<UMLOperation> operations;

    private UMLRelation umlRelation;

    public List<UMLEnumerationLiteral> getLiterals() {
        return literals;
    }

    public void setLiterals(List<UMLEnumerationLiteral> literals) {
        this.literals = literals;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public List<UMLAttribute> getAttributes() {
        return null;
    }

    @Override
    public List<UMLOperation> getOperations() {
        return this.operations;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public UMLRelation getUmlRelation() {
        return this.umlRelation;
    }

    public void setOperations(List<UMLOperation> operations) {
        this.operations = operations;
    }

    public void setUmlRelation(UMLRelation umlRelation) {
        this.umlRelation = umlRelation;
    }

    public static class UMLEnumerationLiteral extends UMLBase {

    }

    private List<UMLEnumerationLiteral> literals;

    private String packageName;
}
