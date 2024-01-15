package ch.ess.cal.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.RequestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.cal.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $
 *  
 * @jsp.tag name="initSelectOptions" body-content="empty" 
 */
public class InitSelectOptionsTag extends TagSupport {

  private String id;
  private String name;
  private String type;
  private String scope;
  private boolean exposeCollection;

  private ApplicationContext ctx;

  public InitSelectOptionsTag() {
    init();
  }

  @Override
  public String getId() {
    return id;
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true" 
   */
  @Override
  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public void setName(final String string) {
    name = string;
  }

  public String getType() {
    return type;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public void setType(final String string) {
    type = string;
  }

  public String getScope() {
    return scope;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public void setScope(final String string) {
    scope = string;
  }

  public boolean isExposeCollection() {
    return exposeCollection;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public void setExposeCollection(final boolean exposeCollection) {
    this.exposeCollection = exposeCollection;
  }

  @Override
  public int doStartTag() throws JspException {

    if (TagUtils.getInstance().lookup(pageContext, getId(), getScope()) == null) {

      AbstractOptions options = null;
      if (!GenericValidator.isBlankOrNull(name)) {
        options = (AbstractOptions)getApplicationContext().getBean(name, AbstractOptions.class);
      } else if (!GenericValidator.isBlankOrNull(type)) {
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

      options.init((HttpServletRequest)pageContext.getRequest());

      if (!exposeCollection) {
        pageContext.setAttribute(getId(), options, TagUtils.getInstance().getScope(getScope()));
      } else {
        pageContext.setAttribute(getId(), options.getLabelValue(), TagUtils.getInstance().getScope(getScope()));
      }
    }

    return SKIP_BODY;
  }

  private ApplicationContext getApplicationContext() {
    if (ctx == null) {
      ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    }
    return ctx;
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
