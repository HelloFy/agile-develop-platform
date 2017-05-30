package cn.edu.xidian.platform.gen.utils.uml.parse.processor;

import java.util.List;

import cn.edu.xidian.platform.gen.entity.uml.UMLAttribute;
import cn.edu.xidian.platform.gen.entity.uml.UMLBase;
import cn.edu.xidian.platform.gen.entity.uml.UMLClass;
import cn.edu.xidian.platform.gen.entity.uml.UMLEnumeration;
import cn.edu.xidian.platform.gen.entity.uml.UMLInterface;
import cn.edu.xidian.platform.gen.entity.uml.UMLModel;
import cn.edu.xidian.platform.gen.entity.uml.UMLOperation;
import cn.edu.xidian.platform.gen.entity.uml.UMLPackage;
import cn.edu.xidian.platform.gen.entity.uml.UMLProject;
import cn.edu.xidian.platform.gen.entity.uml.UMLRelation;
import cn.edu.xidian.platform.gen.entity.uml.UMLType;


/**
 * Created by 李婧 on 2017-4-21.
 */
public interface Processor<O, A> {

    <E extends UMLBase> void parseUMLBase(E umlBase, O jsonObj);

    UMLProject parseUMLProject(O jsonObj);

    UMLModel parseUMLModel(O jsonObj);

    UMLPackage parseUMLPackage(O jsonObj);

    UMLClass parseUMLClass(O jsonObj);

    UMLInterface parseUMLInterface(O jsonObj);

    UMLEnumeration parseUMLEnumeration(O jsonObj);

    List<UMLAttribute> parseUMLAttribute(O jsonObj);

    List<UMLOperation> parseUMLOperation(O jsonObj);

    UMLRelation parseUMLRelation(A jsonArray, UMLType clzOrIface);
}
