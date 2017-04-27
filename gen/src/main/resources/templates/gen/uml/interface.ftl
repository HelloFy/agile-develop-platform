<#import "class.ftl" as clz/>
<#import "enum.ftl" as enm/>
<#macro itm interface=interface>
${interface.visibility} interface ${interface.name}<#if interface.umlRelation??><#if interface.umlRelation.impInterfaces?? && (interface.umlRelation.impInterfaces?size>0)> extends <#assign insertImps><#list interface.umlRelation.impInterfaces as imp> ${imp.name}, </#list></#assign> ${insertImps?substring(0, insertImps?last_index_of(","))}</#if></#if> {
    <#if interface.attributes?? && (interface.attributes?size>0)>
        <#list interface.attributes as attr>
            <#if attr.readOnly>final </#if><#if attr.javaType??><#if attr.list>List<${attr.javaType.realType}> <#else><#if attr.arraySize == 0>${attr.javaType.realType} <#else>${attr.javaType.realType}[] </#if></#if><#else><#if attr.list>List<${attr.otherType}> <#else><#if attr.arraySize == 0>${attr.otherType} <#else>${attr.otherType}[] </#if></#if></#if>${attr.name} <#if attr.defaultValue??>= <#if attr.defaultValue.valueType == 'String'>"${attr.defaultValue.value}" <#else>${attr.defaultValue.value} </#if></#if>;
        </#list>
    </#if>
    <#if interface.operations?? && (interface.operations?size>0)>
        <#list interface.operations as op>
        ${op.visibility} <#if op.concurrency.concurrency??><#if op.concurrency.concurrency == 'concurrent'>synchronized </#if></#if><#if op.paramterOut??><#if op.paramterOut.javaType??><#if op.paramterOut.list> List<${op.paramterOut.javaType.realType}><#else><#if op.paramterOut.arraySize == 0>${op.paramterOut.javaType.realType} <#else>${op.paramterOut.javaType.realType}[] </#if></#if><#else><#if op.paramterOut.list>List <${op.paramterOut.otherType} > <#else><#if op.paramterOut.arraySize == 0>${op.paramterOut.otherType} <#else>${op.paramterOut.otherType}[] </#if></#if></#if><#else>void </#if>${op.name} <#if op.paramterIn?? && (op.paramterIn?size>0)><#assign paraIn><#list op.paramterIn as pin><#if pin.javaType??><#if pin.list>List < ${pin.javaType.realType} >${pin.name}, <#else><#if pin.arraySize == 0>${pin.javaType.realType} ${pin.name}, <#else>${pin.javaType.realType}[] ${pin.name}, </#if></#if><#else><#if pin.list>List < ${pin.otherType} > ${pin.name}, <#else><#if pin.arraySize == 0>${pin.otherType} ${pin.name}, <#else>${pin.otherType}[] ${pin.name}, </#if></#if></#if></#list></#assign>( ${paraIn?substring(0, paraIn?last_index_of(","))} ); <#else> (); </#if>
        </#list>
    </#if>

    <#if interface.umlRelation??>
        <#if interface.umlRelation.innerClasses?? && (interface.umlRelation.innerClasses?size>0)>
            <#list interface.umlRelation.innerClasses as ic>
                <@clz.cm class=ic;c>
                    <@clz.cm class=c></@clz.cm>
                </@clz.cm>
            </#list>
        </#if>
        <#if interface.umlRelation.innerInterfaces?? && (interface.umlRelation.innerInterfaces?size>0)>
            <#list interface.umlRelation.innerInterfaces as ii>
                <#nested ii>
            </#list>
        </#if>
        <#if interface.umlRelation.innerEnums?? && (interface.umlRelation.innerEnums?size>0)>
            <#list interface.umlRelation.innerEnums as ie>
                <@enm.em enum=ie;c>
                    <@enm.em enum=c></@enm.em>
                </@enm.em>
            </#list>
        </#if>
    </#if>

}
</#macro>