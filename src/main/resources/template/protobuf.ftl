[TOC]

#### 简要描述

- ${serviceMethod.description}

##### 请求方法

- `${"${serviceName}.${serviceMethod.name}"}`

#### 请求参数

- `${request.fullName}`

| 参数名称 | 参数类型 | 参数标签 | 参数说明 |
| :------: | :------: | :------: | :------: |
<#if (request.fields?size>0)>
<#list request.fields as field>
|    ${field.name}      |     <#if field.fullType?contains(".")> ${"[${field.fullType}](#${field.fullType})"} <#else> ${field.fullType} </#if>     |     ${field.label}     |     ${field.description}     |
</#list>
</#if>

#### 返回参数说明

- `${response.fullName}`
- `${response.fullName}`

| 参数名称 | 参数类型 | 参数标签 | 参数说明 |
| :------: | :------: | :------: | :------: |
<#if (request.fields?size>0)>
<#list response.fields as field>
|    ${field.name}      |     <#if field.fullType?contains(".")> ${"[${field.fullType}](#${field.fullType})"} <#else> ${field.fullType} </#if>     |     ${field.label}     |     ${field.description}     |
</#list>
</#if>

<#if (extraMessages?size>0)||(extraEnums?size>0)>
#### 附加参数说明

<#if (extraMessages?size>0)>
##### `Message`

<#list extraMessages as message>
###### ${'[${message.fullName}](#${message.fullName})'}

<#if (message.description??)&&(message.description!="")>
- 描述： ${message.description}
</#if>

| 参数名称 | 参数类型 | 参数标签 | 参数说明 |
| :------: | :------: | :------: | :------: |
<#list message.fields as field>
|    ${field.name}      |     <#if field.fullType?contains(".")> ${"[${field.fullType}](#${field.fullType})"} <#else> ${field.fullType} </#if>     |     ${field.label}     |     ${field.description}     |
</#list>
</#list>

</#if>
<#if (extraEnums?size>0)>
##### `Enum`

<#list extraEnums as enum>
###### ${'[${enum.fullName}](#${enum.fullName})'}

<#if (enum.description??)&&(enum.description!="")>
- 描述：enum.description
</#if>

| 参数名称 | 参数下标 | 参数说明 |
| :------: | :------: | :------: |
<#list enum.values as value>
|    ${value.name}      |   ${value.number}   |     ${value.description}     |
</#list>
</#list>

</#if>
</#if>