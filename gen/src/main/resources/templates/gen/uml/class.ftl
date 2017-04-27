<#import "interface.ftl" as iface/>
<#import "enum.ftl" as enm/>
<#macro cm class>
${class.visibility} <#if (class.finalSpecialization)>final </#if><#if class.virtual>abstract </#if>class ${class.name} <#if class.umlRelation??><#if class.umlRelation.parentClass??>extends ${class.umlRelation.parentClass.name}</#if><#if class.umlRelation.impInterfaces?? && (class.umlRelation.impInterfaces?size>0)> implements <#assign insertImps><#list class.umlRelation.impInterfaces as imp>${imp.name},</#list></#assign>${insertImps?substring(0, insertImps?last_index_of(","))}</#if></#if> {
    <#if class.attributes?? && (class.attributes?size>0)>
        <#list class.attributes as attr>
        ${attr.visibility} <#if attr.statics>static </#if><#if attr.readOnly>final </#if><#if attr.javaType??><#if attr.list>List<${attr.javaType.realType}> <#else><#if attr.arraySize == 0>${attr.javaType.realType} <#else>${attr.javaType.realType}[] </#if></#if><#else><#if attr.list>List<${attr.otherType}> <#else><#if attr.arraySize == 0>${attr.otherType} <#else>${attr.otherType}[] </#if></#if></#if>${attr.name} <#if attr.defaultValue??>= <#if attr.defaultValue.valueType == 'String'>"${attr.defaultValue.value}" <#else>${attr.defaultValue.value} </#if></#if>;
        </#list>
    </#if>

    <#if class.umlRelation??>
        <#if class.umlRelation.composes?? && (class.umlRelation.composes?size>0)>
            <#list class.umlRelation.composes as c>
                <#if c.composeClass?? >
                ${c.visibility} <#if c.multiplicitytype??><#if c.multiplicitytype.multiplicity == '0' || c.multiplicitytype.multiplicity == '0..1' >${c.composeClass.name}<#else>List<${c.composeClass.name}></#if></#if> ${c.fieldName};
                </#if>
                <#if c.composeInterface?? >
                ${c.visibility} <#if c.multiplicitytype??><#if c.multiplicitytype.multiplicity == '0' || c.multiplicitytype.multiplicity == '0..1' >${c.composeInterface.name}<#else>List<${c.composeInterface.name}></#if></#if> ${c.fieldName};
                </#if>
                <#if c.composeEnum??>
                ${c.visibility} <#if c.multiplicitytype??><#if c.multiplicitytype.multiplicity == '0' || c.multiplicitytype.multiplicity == '0..1' >${c.composeEnum.name}<#else>List<${c.composeEnum.name}></#if></#if> ${c.fieldName};
                </#if>
            </#list>
        </#if>
        <#if class.umlRelation.innerClasses?? && (class.umlRelation.innerClasses?size>0)>
            <#list class.umlRelation.innerClasses as ic>
                <#nested ic>
            </#list>
        </#if>
        <#if class.umlRelation.innerInterfaces?? && (class.umlRelation.innerInterfaces?size>0)>
            <#list class.umlRelation.innerInterfaces as ii>
                <@iface.itm ii>
                    <@iface.itm interface=ii></@iface.itm>
                </@iface.itm>
            </#list>
        </#if>
        <#if class.umlRelation.innerEnums?? && (class.umlRelation.innerEnums?size>0)>
            <#list class.umlRelation.innerEnums as ie>
                <@enm.em enum=ie;c>
                    <@enm.em enum=c></@enm.em>
                </@enm.em>
            </#list>
        </#if>
    </#if>

    <#if class.operations?? && (class.operations?size>0)>
        <#list class.operations as op>
            <#if op.virtual>
            ${op.visibility} abstract <#if op.concurrency.concurrency??><#if op.concurrency.concurrency == 'concurrent'>synchronized </#if></#if><#if op.paramterOut??><#if op.paramterOut.javaType??><#if op.paramterOut.list> List<${op.paramterOut.javaType.realType}><#else><#if op.paramterOut.arraySize == 0>${op.paramterOut.javaType.realType} <#else>${op.paramterOut.javaType.realType}[] </#if></#if><#else><#if op.paramterOut.list>List <${op.paramterOut.otherType} > <#else><#if op.paramterOut.arraySize == 0>${op.paramterOut.otherType} <#else>${op.paramterOut.otherType}[] </#if></#if></#if><#else>void </#if>${op.name} <#if op.paramterIn?? && (op.paramterIn?size>0)><#assign paraIn><#list op.paramterIn as pin><#if pin.javaType??><#if pin.list>List < ${pin.javaType.realType} >${pin.name}, <#else><#if pin.arraySize == 0>${pin.javaType.realType} ${pin.name}, <#else>${pin.javaType.realType}[] ${pin.name}, </#if></#if><#else><#if pin.list>List < ${pin.otherType} > ${pin.name}, <#else><#if pin.arraySize == 0>${pin.otherType} ${pin.name}, <#else>${pin.otherType}[] ${pin.name}, </#if></#if></#if></#list></#assign>( ${paraIn?substring(0, paraIn?last_index_of(","))} ); <#else> (); </#if>
            <#else>
            ${op.visibility} <#if op.concurrency.concurrency??><#if op.concurrency.concurrency == 'concurrent'>synchronized </#if></#if><#if op.paramterOut??><#if op.paramterOut.javaType??><#if op.paramterOut.list> List<${op.paramterOut.javaType.realType}><#else><#if op.paramterOut.arraySize == 0>${op.paramterOut.javaType.realType} <#else>${op.paramterOut.javaType.realType}[] </#if></#if><#else><#if op.paramterOut.list>List <${op.paramterOut.otherType} > <#else><#if op.paramterOut.arraySize == 0>${op.paramterOut.otherType} <#else>${op.paramterOut.otherType}[] </#if></#if></#if><#else>void </#if>${op.name} <#if op.paramterIn?? && (op.paramterIn?size>0)><#assign paraIn><#list op.paramterIn as pin><#if pin.javaType??><#if pin.list>List < ${pin.javaType.realType} >${pin.name}, <#else><#if pin.arraySize == 0>${pin.javaType.realType} ${pin.name}, <#else>${pin.javaType.realType}[] ${pin.name}, </#if></#if><#else><#if pin.list>List < ${pin.otherType} > ${pin.name}, <#else><#if pin.arraySize == 0>${pin.otherType} ${pin.name}, <#else>${pin.otherType}[] ${pin.name}, </#if></#if></#if></#list></#assign>( ${paraIn?substring(0, paraIn?last_index_of(","))} ) <#else> () </#if>{
                <#if op.paramterOut??>
                return null;
                </#if>
            }
            </#if>
        </#list>
    </#if>
}
</#macro>