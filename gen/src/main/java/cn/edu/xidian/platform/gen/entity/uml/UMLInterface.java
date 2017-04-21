package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017/4/18.
 */
public class UMLInterface extends UMLBase {

    private List<UMLAttribute> attributes;
    private List<UMLOperation> UMLOperations;
    private UMLRelation relation;


    public List<UMLAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<UMLAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<UMLOperation> getUMLOperations() {
        return UMLOperations;
    }

    public void setUMLOperations(List<UMLOperation> UMLOperations) {
        this.UMLOperations = UMLOperations;
    }

    public UMLRelation getRelation() {
        return relation;
    }

    public void setRelation(UMLRelation relation) {
        this.relation = relation;
    }
}
