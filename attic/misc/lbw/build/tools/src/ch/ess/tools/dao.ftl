package ${package}.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ${package}.model.${className};
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "${className?uncap_first}Dao", autowire = Autowire.BYTYPE)
public class ${className}Dao extends AbstractDao<${className}> {

  public ${className}Dao() {
    super(${className}.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<${className}> find(<#foreach prop in filterproperties><#if prop.filter><#if prop.reference>final String ${prop.name}Id<#else>final ${prop.typ} ${prop.name}</#if></#if><#if prop_has_next>, </#if></#foreach>) {
    Criteria criteria = getSession().createCriteria(${className}.class);

    <#foreach prop in filterproperties>
    <#if prop.filter>
    <#if prop.reference>
    if (StringUtils.isNotBlank(${prop.name}Id)) {
      criteria.add(Restrictions.eq("${prop.name}.id", new Integer(${prop.name}Id)));
    }
    <#else>
    if (StringUtils.isNotBlank(${prop.name})) {
      String str = ${prop.name}.trim();
      criteria.add(Restrictions.like("${prop.name}", str, MatchMode.START));
    }
    </#if>
    </#if>
    </#foreach>
    
    return criteria.list();

  }

  <#if canDelete?length &gt; 0> 
  @Override
  @Transactional(readOnly = true)
  public boolean canDelete(final ${className} ${className?uncap_first}) {
    //todo check
    return ${canDelete}
  }
  </#if>
  
}