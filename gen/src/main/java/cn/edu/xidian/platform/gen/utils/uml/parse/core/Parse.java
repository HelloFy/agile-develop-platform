package cn.edu.xidian.platform.gen.utils.uml.parse.core;

import java.util.List;

import cn.edu.xidian.platform.gen.entity.uml.UMLFile;

/**
 * Created by 李婧 on 2017-4-21.
 */
public interface Parse<T> {

    List<T> parse(UMLFile umlFile);
}
