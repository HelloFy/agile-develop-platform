package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017/4/18.
 */
public class UMLInterface extends UMLBase {

    private List<Attribute> attributes;
    private List<Opreation> opreations;
    private UMLRelation relation;


    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Opreation> getOpreations() {
        return opreations;
    }

    public void setOpreations(List<Opreation> opreations) {
        this.opreations = opreations;
    }

    public UMLRelation getRelation() {
        return relation;
    }

    public void setRelation(UMLRelation relation) {
        this.relation = relation;
    }
}
