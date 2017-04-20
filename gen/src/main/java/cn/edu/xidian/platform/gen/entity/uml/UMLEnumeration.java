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

    public class UMLEnumerationLiteral extends UMLBase {

    }

    private List<UMLEnumerationLiteral> literals;
}
