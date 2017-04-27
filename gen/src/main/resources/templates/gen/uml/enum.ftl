<#import "class.ftl" as clz/>
<#import "interface.ftl" as iface/>
<#macro em enum>
${enum.visibility} enum ${enum.name} {
    <#if enum.literals?? && (enum.literals?size>0)>
        <#assign lts>
            <#list enum.literals as t>
            ${t.name},
            </#list>
        </#assign>
    ${lts?substring(0, lts?last_index_of(","))};
    </#if>
    <#if enum.umlRelation??>
        <#if enum.umlRelation.innerClasses?? && (enum.umlRelation.innerClasses?size>0)>
            <#list enum.umlRelation.innerClasses as ic>
                <@clz.cm class=ic;c>
                    <@clz.cm class=c></@clz.cm>
                </@clz.cm>
            </#list>
        </#if>
        <#if enum.umlRelation.innerInterfaces?? && (enum.umlRelation.innerInterfaces?size>0)>
            <#list enum.umlRelation.innerInterfaces as ii>
                <@iface.itm interface=ii;c>
                    <@iface.itm interface=c></@iface.itm>
                </@iface.itm>
            </#list>
        </#if>
        <#if enum.umlRelation.innerEnums?? && (enum.umlRelation.innerEnums?size>0)>
            <#list  enum.umlRelation.innerEnums as ie>
                <#nested ie>
            </#list>
        </#if>
    </#if>
}
</#macro>