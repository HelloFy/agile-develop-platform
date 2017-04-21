package cn.edu.xidian.platform.gen.utils.uml.parse.core;

import java.util.List;

import cn.edu.xidian.platform.gen.entity.uml.UMLFile;

/**
 * Created by 费玥 on 2017-4-21.
 */
public interface Parse<T> {

    List<T> parse(UMLFile umlFile);
}
