package cn.edu.xidian.platform.gen.entity.uml;

/**
 * Created by 费玥 on 2017/4/18.
 */
public class UMLBase {
    protected String id;
    protected String name;
    protected String documention;
    protected String visibility;

    public UMLBase() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumention() {
        return documention;
    }

    public void setDocumention(String documention) {
        this.documention = documention;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
