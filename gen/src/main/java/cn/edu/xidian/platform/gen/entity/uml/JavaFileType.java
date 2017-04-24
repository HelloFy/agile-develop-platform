package cn.edu.xidian.platform.gen.entity.uml;

import java.util.List;

/**
 * @author 费玥
 * @since 2017/4/24 22:05 标记接口
 */
public interface JavaFileType {

    UMLRelation getUmlRelation();

    String getPackageName();

    List<UMLAttribute> getAttributes();

    List<UMLOperation> getOperations();
}
