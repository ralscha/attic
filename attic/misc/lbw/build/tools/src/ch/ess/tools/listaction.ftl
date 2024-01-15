package ${package}.web.${className?lower_case};

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;
import ch.ess.base.annotation.struts.Role;
<#foreach prop in listproperties>
<#if prop.reference || prop.many>
import ${prop.refImport};
</#if>
<#if prop.manymany>
import ${prop.joinImport};
</#if>
</#foreach>
import ch.ess.base.Constants;
import ${package}.dao.${className}Dao;
import ${package}.model.${className};
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.base.web.MapForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

<#if role?exists>
@Role("${role}")
</#if>
public class ${className}ListAction extends AbstractListAction {

  private ${className}Dao ${className?uncap_first}Dao;

  public void set${className}Dao(final ${className}Dao ${className?uncap_first}Dao) {
    this.${className?uncap_first}Dao = ${className?uncap_first}Dao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    List<${className}> ${className?uncap_first}s = null;
    if (searchForm != null) {        
      ${className?uncap_first}s = ${className?uncap_first}Dao.find(<#foreach prop in filterproperties><#if prop.filter>searchForm.getStringValue("${prop.name}<#if prop.reference>Id</#if>")</#if><#if prop_has_next>, </#if></#foreach>);
    } else {
      ${className?uncap_first}s = ${className?uncap_first}Dao.findAll();
    }

    for (${className} ${className?uncap_first} : ${className?uncap_first}s) {

      DynaBean dynaBean = new LazyDynaBean("${className?uncap_first}List");

      dynaBean.set("id", ${className?uncap_first}.getId().toString());
      
      <#foreach prop in listproperties>
      <#if prop.reference>
      <#if prop.required>
      dynaBean.set("${prop.name}", ${className?uncap_first}.${prop.readMethod}().${prop.refReadProperty}());
      <#else>
      ${prop.name?cap_first} ${prop.name} = ${className?uncap_first}.${prop.readMethod}();
      if (${prop.name} != null) {
        dynaBean.set("${prop.name}", ${prop.name}.${prop.refReadProperty}());      
      } else {
        dynaBean.set("${prop.name}", null);
      }
      </#if>
      <#elseif prop.many>
      Set<${prop.typOfSet}> ${prop.name} = ${className?uncap_first}.${prop.readMethod}();
      StringBuilder sb = new StringBuilder();
      for (${prop.typOfSet} each : ${prop.name}) {
        if (sb.length() > 0) {
          sb.append(",");
        }
        sb.append(each.${prop.refReadProperty}());
      }
      dynaBean.set("${prop.name}", sb.toString());      
      <#elseif prop.manymany>
      Set<${prop.joinClassName}> ${prop.name} = ${className?uncap_first}.${prop.readMethod}();
      StringBuilder sb = new StringBuilder();
      for (${prop.joinClassName} each : ${prop.name}) {
        if (sb.length() > 0) {
          sb.append(",");
        }
        sb.append(each.${prop.refReadProperty}().${prop.joinReadMethod}());
      }
      dynaBean.set("${prop.name}", sb.toString());        
      <#else>
      dynaBean.set("${prop.name}", ${className?uncap_first}.${prop.readMethod}());        
      </#if>
      </#foreach>
      
      dynaBean.set("deletable", ${className?uncap_first}Dao.canDelete(${className?uncap_first}));
      
      dataModel.add(dynaBean);

    }


    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    ${className?uncap_first}Dao.delete(key);
  }
  
  <#if titleReadMethod?length &gt; 0>
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      ${className} ${className?uncap_first} = ${className?uncap_first}Dao.findById(id);
      if (${className?uncap_first} != null) {
        return ${className?uncap_first}.${titleReadMethod}();
      }
    }
    return null;
  }
  </#if>   
}
