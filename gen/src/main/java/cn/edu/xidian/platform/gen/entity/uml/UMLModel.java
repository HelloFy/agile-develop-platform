package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * Created by 费玥 on 2017-4-21.
 */
public class UMLModel extends UMLBase {

    private List<UMLPackage> packages;

    public List<UMLPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<UMLPackage> packages) {
        this.packages = packages;
    }
}
