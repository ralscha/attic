package ch.ess.base.web.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.RequestUtils;
import org.springframework.context.support.StaticApplicationContext;

import ch.ess.base.web.WebConstants;
import ch.ess.base.web.options.AbstractOptions;

public class InitSelectOptionsTag extends TagSupport implements DynamicAttributes  {

  private String id;
  private String name;
  private String type;
  private String scope;
  private boolean exposeCollection;
  
  private Map<String,Object> tagAttributes = new HashMap<String,Object>();
  
  public InitSelectOptionsTag() {
    init();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }


  public void setName(final String string) {
    name = string;
  }

  public String getType() {
    return type;
  }


  public void setType(final String string) {
    type = string;
  }

  public String getScope() {
    return scope;
  }


  public void setScope(final String string) {
    scope = string;
  }

  public boolean isExposeCollection() {
    return exposeCollection;
  }


  public void setExposeCollection(final boolean exposeCollection) {
    this.exposeCollection = exposeCollection;
  }

  public void setDynamicAttribute(String uri, String localName, Object value) {
    tagAttributes.put(localName, value);    
  }
  
  @Override
  public int doStartTag() throws JspException {

    if (TagUtils.getInstance().lookup(pageContext, getId(), getScope()) == null) {

      AbstractOptions options = null;
      if (StringUtils.isNotBlank(name)) {
        options = (AbstractOptions)getApplicationContext(pageContext.getServletContext()).getBean(name, AbstractOptions.class);
      } else if (StringUtils.isNotBlank(type)) {
        try {
          options = (AbstractOptions)RequestUtils.applicationInstance(type);
        } catch (ClassNotFoundException e) {
          throw new JspException(e);
        } catch (IllegalAccessException e) {
          throw new JspException(e);
        } catch (InstantiationException e) {
          throw new JspException(e);
        }
      }
      options.setTagAttributes(tagAttributes);
      options.init((HttpServletRequest)pageContext.getRequest());

      if (!exposeCollection) {
        pageContext.setAttribute(getId(), options, TagUtils.getInstance().getScope(getScope()));
      } else {
        pageContext.setAttribute(getId(), options.getLabelValue(), TagUtils.getInstance().getScope(getScope()));
      }
    }

    return SKIP_BODY;
  }

  private StaticApplicationContext getApplicationContext(ServletContext servletContext) {    
    return (StaticApplicationContext)servletContext.getAttribute(WebConstants.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
  }


  @Override
  public void release() {
    super.release();
    init();
  }

  private void init() {
    id = null;
    name = null;
    type = null;
    scope = "request";
    exposeCollection = false;
  }



}