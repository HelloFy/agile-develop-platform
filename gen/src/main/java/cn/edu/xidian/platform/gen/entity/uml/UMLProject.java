package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017-4-21.
 */
public class UMLProject extends UMLBase {

    private List<UMLModel> models;

    public List<UMLModel> getModels() {
        return models;
    }

    public void setModels(List<UMLModel> models) {
        this.models = models;
    }
}
