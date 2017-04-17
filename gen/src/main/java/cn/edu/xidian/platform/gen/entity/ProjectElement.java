package cn.edu.xidian.platform.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * Created by 费玥 on 2017/4/15.
 */
public class ProjectElement {
    @JSONField(name = "_id")
    private String id;
    @JSONField(name = "author")
    private String author;
    @JSONField(name = "_type")
    private String umlType;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "ownedElements")
    private ArrayList<ProjectElement> ownedElements = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUmlType(String umlType) {
        this.umlType = umlType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnedElements(ArrayList<ProjectElement> ownedElements) {
        this.ownedElements = ownedElements;
    }

    public void addProjectElement(ProjectElement projectElement) {
        ownedElements.add(projectElement);
    }
}

class ModelElement {
    @JSONField(name = "visible")
    private boolean visible;
    @JSONField(name = "visibility")
    private String visibility;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

/*    public ProjectElement getParent() {
        return parent;
    }

    public void setParent(ProjectElement parent) {
        this.parent = parent;
    }*/

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}

class DiagramElement extends ModelElement {
}

class ClassDiagram extends DiagramElement {
}

class Atribute extends ClassDiagram {

}