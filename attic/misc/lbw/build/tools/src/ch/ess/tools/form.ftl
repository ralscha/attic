package ${package}.web.${className?lower_case};

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.annotation.struts.Variable;
import ch.ess.base.web.AbstractForm;

public class ${className}Form extends AbstractForm {
  
  <#foreach prop in properties>
  <#if prop.reference>
  private ${prop.formTyp} ${prop.name}Id;  
  <#elseif prop.many>
  private ${prop.formTyp} ${prop.name}Ids;  
  <#elseif prop.manymany>
  private ${prop.formTyp} ${prop.typOfSet?uncap_first}Ids;  
  <#else>
  private ${prop.formTyp} ${prop.name};
  </#if>
  </#foreach>
  
  <#if hasResetAttributes>
  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    <#foreach prop in properties>
      <#if prop.formTyp == 'boolean'>
    ${prop.name} = false;
      <#elseif prop.many>
    ${prop.name}Ids = null;  
      <#elseif prop.manymany>
    ${prop.typOfSet?uncap_first}Ids = null;
      </#if>
    </#foreach>
  }
  </#if>

  <#foreach prop in properties>
  <#if prop.typ != 'Boolean'>
  <#if prop.typ == 'Date'>
  @ValidatorField(key = "${className?uncap_first}.${prop.name}", required=${prop.required?string}, validators = 
    @Validator(name = "date", vars = @Variable(name = "datePatternStrict", value = "dd.MM.yyyy")))
  <#elseif prop.typ == 'BigDecimal'>
  @ValidatorField(key="${className?uncap_first}.${prop.name}", required=${prop.required?string}, validators = @Validator(name = "float"))
  <#elseif prop.typ == 'Integer'>
  @ValidatorField(key="${className?uncap_first}.${prop.name}", required=${prop.required?string}, validators = @Validator(name = "integer"))
  <#else>
  <#if prop.required>@ValidatorField(key="${className?uncap_first}.${prop.name}", required=true)</#if>
  </#if>
  </#if>
  <#if prop.reference>
  public void ${prop.writeMethod}Id(final String ${prop.name}Id) {
    this.${prop.name}Id = ${prop.name}Id;
  }

  public String ${prop.formReadMethod}Id() {
    return ${prop.name}Id;
  }    
  
  <#elseif prop.many>
  public void ${prop.writeMethod}Ids(final ${prop.formTyp} ${prop.name}Id) {
    this.${prop.name}Ids = (String[])ArrayUtils.clone(${prop.name}Id);
  }

  public ${prop.formTyp} ${prop.formReadMethod}Ids() {
    return (String[])ArrayUtils.clone(${prop.name}Ids);
  }   
 
  <#elseif prop.manymany>
  public void set${prop.typOfSet}Ids(final ${prop.formTyp} ${prop.typOfSet?uncap_first}Id) {
    this.${prop.typOfSet?uncap_first}Ids = (String[])ArrayUtils.clone(${prop.typOfSet?uncap_first}Id);
  }

  public ${prop.formTyp} get${prop.typOfSet}Ids() {
    return (String[])ArrayUtils.clone(${prop.typOfSet?uncap_first}Ids);
  }    
  
  <#else>
  public void ${prop.writeMethod}(final ${prop.formTyp} ${prop.name}) {
    this.${prop.name} = ${prop.name};
  }

  public ${prop.formTyp} ${prop.formReadMethod}() {
    return ${prop.name};
  }
  
  </#if>
  
  </#foreach>

}
