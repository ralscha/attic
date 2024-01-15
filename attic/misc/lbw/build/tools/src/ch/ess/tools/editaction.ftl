package ${package}.web.${className?lower_case};

import java.util.Set;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.Role;
import org.apache.commons.lang.StringUtils;
<#foreach prop in properties>
<#if prop.reference || prop.many || prop.manymany>
import ${prop.refDaoImport};
</#if>
<#if prop.many || prop.manymany>
import ${prop.refImport};
</#if>
<#if prop.manymany>
import ${prop.joinImport};
</#if>
</#foreach>
import ${package}.model.${className};
import ch.ess.base.web.AbstractEditAction;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

<#if role?exists>
@Role("${role}")
</#if>
public class ${className}EditAction extends AbstractEditAction<${className}> {

  <#foreach prop in properties>
  <#if prop.reference>
  private ${prop.name?cap_first}Dao ${prop.name}Dao;
  <#elseif prop.many || prop.manymany>
  private ${prop.typOfSet}Dao ${prop.typOfSet?uncap_first}Dao;
  </#if>  
  </#foreach>
  
  <#foreach prop in properties>
  <#if prop.reference>
  public void set${prop.name?cap_first}Dao(${prop.name?cap_first}Dao ${prop.name}Dao) {
    this.${prop.name}Dao = ${prop.name}Dao;
  }
  <#elseif prop.many || prop.manymany>
  public void set${prop.typOfSet}Dao(${prop.typOfSet}Dao ${prop.typOfSet?uncap_first}Dao) {
    this.${prop.typOfSet?uncap_first}Dao = ${prop.typOfSet?uncap_first}Dao;
  }  
  </#if>
  </#foreach>  
  
  @Override
  public void formToModel(final ActionContext ctx, ${className} ${className?uncap_first}) {

    ${className}Form ${className?uncap_first}Form = (${className}Form)ctx.form();
    
    <#if hasDates>
    DateFormat df = new SimpleDateFormat(Constants.getParseDateFormatPattern());
    </#if>
    
    <#foreach prop in properties>
     <#if !prop.many && !prop.manymany>
      <#if prop.required || prop.formTyp == 'boolean'>
        <#if prop.typ == 'BigDecimal'>
    ${className?uncap_first}.${prop.writeMethod}(new BigDecimal(${className?uncap_first}Form.${prop.formReadMethod}()));              
        <#elseif prop.typ == 'Integer'>
    ${className?uncap_first}.${prop.writeMethod}(new Integer(${className?uncap_first}Form.${prop.formReadMethod}()));              
        <#elseif prop.typ == 'Date'>
    try {
      ${className?uncap_first}.${prop.writeMethod}(df.parse(${className?uncap_first}Form.${prop.formReadMethod}()));              
    } catch (ParseException e) {
      //no action
    }
        <#elseif prop.reference>
    ${className?uncap_first}.${prop.writeMethod}(${prop.name}Dao.findById(${className?uncap_first}Form.${prop.formReadMethod}Id()));         
        <#else>
    ${className?uncap_first}.${prop.writeMethod}(${className?uncap_first}Form.${prop.formReadMethod}());      
        </#if>
      <#else>
    <#if prop.reference>
    if (StringUtils.isNotBlank(${className?uncap_first}Form.${prop.formReadMethod}Id())) {    
    <#else>  
    if (StringUtils.isNotBlank(${className?uncap_first}Form.${prop.formReadMethod}())) {
    </#if>
        <#if prop.typ == 'BigDecimal'>
      ${className?uncap_first}.${prop.writeMethod}(new BigDecimal(${className?uncap_first}Form.${prop.formReadMethod}()));              
        <#elseif prop.typ == 'Integer'>
      ${className?uncap_first}.${prop.writeMethod}(new Integer(${className?uncap_first}Form.${prop.formReadMethod}()));              
        <#elseif prop.typ == 'Date'>
      try {
        ${className?uncap_first}.${prop.writeMethod}(df.parse(${className?uncap_first}Form.${prop.formReadMethod}()));              
      } catch (ParseException e) {
        //no action
      }
        <#elseif prop.reference>
      ${className?uncap_first}.${prop.writeMethod}(${prop.name}Dao.findById(${className?uncap_first}Form.${prop.formReadMethod}Id()));                 
        <#else>
      ${className?uncap_first}.${prop.writeMethod}(${className?uncap_first}Form.${prop.formReadMethod}());      
        </#if>
    } else {
      ${className?uncap_first}.${prop.writeMethod}(null);
    }      
      </#if>
     <#elseif prop.many>
    ${className?uncap_first}.${prop.readMethod}().clear();

    String[] ${prop.name}Ids = ${className?uncap_first}Form.${prop.formReadMethod}Ids();
    if (${prop.name}Ids != null) {

      for (String id : ${prop.name}Ids) {
        if (StringUtils.isNotBlank(id)) {
          ${prop.typOfSet} obj = ${prop.typOfSet?uncap_first}Dao.findById(id);
          if (obj != null) {
            ${className?uncap_first}.${prop.readMethod}().add(obj);
          }
        }
      }
    }     
     <#elseif prop.manymany>
    ${className?uncap_first}.${prop.readMethod}().clear();

    String[] ${prop.typOfSet?uncap_first}Ids = ${className?uncap_first}Form.get${prop.typOfSet}Ids();
    if (${prop.typOfSet?uncap_first}Ids != null) {

      for (String id : ${prop.typOfSet?uncap_first}Ids) {
        if (StringUtils.isNotBlank(id)) {
          ${prop.typOfSet} obj = ${prop.typOfSet?uncap_first}Dao.findById(id);
          if (obj != null) {
            ${prop.joinClassName} ${prop.joinClassName?uncap_first} = new ${prop.joinClassName}();            
            ${prop.joinClassName?uncap_first}.set${prop.typOfSet}(obj);
            ${prop.joinClassName?uncap_first}.set${className}(${className?uncap_first});
            ${className?uncap_first}.${prop.readMethod}().add(${prop.joinClassName?uncap_first});
          }
        }
      }
    }     

     </#if>
    </#foreach>
    
    return ${className?uncap_first};
  }

  @Override
  public void modelToForm(final ActionContext ctx, final ${className} ${className?uncap_first}) {

    ${className}Form ${className?uncap_first}Form = (${className}Form)ctx.form();
    
    <#if hasDates>
    DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
    </#if>
    
    <#foreach prop in properties>
     <#if !prop.many && !prop.manymany>
      <#if prop.required>
        <#if prop.typ == 'String' || prop.typ == 'Boolean'>
    ${className?uncap_first}Form.${prop.writeMethod}(${className?uncap_first}.${prop.readMethod}());
        <#elseif prop.typ == 'Integer'>
    ${className?uncap_first}Form.${prop.writeMethod}(${className?uncap_first}.${prop.readMethod}().toString());        
        <#elseif prop.typ == 'BigDecimal'>
    ${className?uncap_first}Form.${prop.writeMethod}(Constants.TWO_DECIMAL_FORMAT.format(${className?uncap_first}.${prop.readMethod}()));        
        <#elseif prop.typ == 'Date'>
    ${className?uncap_first}Form.${prop.writeMethod}(df.format(${className?uncap_first}.${prop.readMethod}())); 
        <#elseif prop.reference>
    ${className?uncap_first}Form.${prop.writeMethod}Id(${className?uncap_first}.${prop.readMethod}().getId().toString());               
        </#if>
      <#else>
    if (${className?uncap_first}.${prop.readMethod}() != null) {
        <#if prop.typ == 'String' || prop.typ == 'Boolean'>
      ${className?uncap_first}Form.${prop.writeMethod}(${className?uncap_first}.${prop.readMethod}());
        <#elseif prop.typ == 'Integer'>      
      ${className?uncap_first}Form.${prop.writeMethod}(${className?uncap_first}.${prop.readMethod}().toString());        
        <#elseif prop.typ == 'BigDecimal'>      
      ${className?uncap_first}Form.${prop.writeMethod}(Constants.TWO_DECIMAL_FORMAT.format(${className?uncap_first}.${prop.readMethod}()));        
        <#elseif prop.typ == 'Date'>      
      ${className?uncap_first}Form.${prop.writeMethod}(df.format(${className?uncap_first}.${prop.readMethod}()));        
        <#elseif prop.reference>
      ${className?uncap_first}Form.${prop.writeMethod}Id(${className?uncap_first}.${prop.readMethod}().getId().toString());                       
        </#if>       
    } else {
      <#if prop.typ == 'Boolean'>
      ${className?uncap_first}Form.${prop.writeMethod}(false);  
      <#elseif prop.reference>
      ${className?uncap_first}Form.${prop.writeMethod}Id(null);  
      <#else>
      ${className?uncap_first}Form.${prop.writeMethod}(null);       
      </#if>
    }    
      </#if>
     <#elseif prop.many>

    Set<${prop.typOfSet}> ${prop.name}Set = ${className?uncap_first}.${prop.readMethod}();
    String[] ${prop.name}Ids = null;

    if (!${prop.name}Set.isEmpty()) {
      ${prop.name}Ids = new String[${prop.name}Set.size()];

      int i = 0;
      for (${prop.typOfSet} ${prop.typOfSet?uncap_first} : ${prop.name}Set) {
        ${prop.name}Ids[i++] = ${prop.typOfSet?uncap_first}.getId().toString();
      }
    } else {
      ${prop.name}Ids = null;
    }
    ${className?uncap_first}Form.set${prop.name?cap_first}Ids(${prop.name}Ids);    
     <#elseif prop.manymany>

    Set<${prop.joinClassName}> ${prop.joinClassName?uncap_first}Set = ${className?uncap_first}.${prop.readMethod}();
    String[] ${prop.typOfSet?uncap_first}Ids = null;

    if (!${prop.joinClassName?uncap_first}Set.isEmpty()) {
      ${prop.typOfSet?uncap_first}Ids = new String[${prop.joinClassName?uncap_first}Set.size()];

      int i = 0;
      for (${prop.joinClassName} ${prop.joinClassName?uncap_first} : ${prop.joinClassName?uncap_first}Set) {
        ${prop.typOfSet?uncap_first}Ids[i++] = ${prop.joinClassName?uncap_first}.${prop.refReadProperty}().getId().toString();
      }
    } else {
      ${prop.typOfSet?uncap_first}Ids = null;
    }
    ${className?uncap_first}Form.set${prop.typOfSet}Ids(${prop.typOfSet?uncap_first}Ids);    

     </#if>
    </#foreach>    
  }

}
