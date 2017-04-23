package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017-4-20.
 */
public class UMLEnumeration extends UMLBase {

    public List<UMLEnumerationLiteral> getLiterals() {
        return literals;
    }

    public void setLiterals(List<UMLEnumerationLiteral> literals) {
        this.literals = literals;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public static class UMLEnumerationLiteral extends UMLBase {

    }

    private List<UMLEnumerationLiteral> literals;

    private String packageName;
}
