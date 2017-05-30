package cn.edu.xidian.platform.gen.utils.uml.parse.processor;

import cn.edu.xidian.platform.gen.entity.uml.UMLType;

/**
 * Created by 李婧 on 2017-4-21.
 */
public interface ParseProcessor<O, A> {

    A getElements(O jsonObj);

    boolean containsElements(O jsonObj);

    UMLType parseUMLType(O jsonObj);
}
