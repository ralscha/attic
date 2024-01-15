package ch.ess.common.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.hibernate.HibernateException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.RequestUtils;

import ch.ess.common.web.Options;

/**
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 *  
 * @jsp.tag name="initSelectOptions" body-content="empty" 
 */
public class InitSelectOptionsTag extends TagSupport {

  private String id;
  private String type;
  private String scope;

  public InitSelectOptionsTag() {
    init();
  }

  public String getId() {
    return id;
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true" 
   */
  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true" 
   */
  public void setType(String string) {
    type = string;
  }

  public String getScope() {
    return scope;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public void setScope(String string) {
    scope = string;
  }

  public int doStartTag() throws JspException {

    if (TagUtils.getInstance().lookup(pageContext, getId(), getScope()) == null) {

      try {

        Options options = (Options) RequestUtils.applicationInstance(type);
        options.init((HttpServletRequest) pageContext.getRequest());

        pageContext.setAttribute(getId(), options, TagUtils.getInstance().getScope(getScope()));
      } catch (ClassNotFoundException e) {
        throw new JspException(e);
      } catch (IllegalAccessException e) {
        throw new JspException(e);
      } catch (InstantiationException e) {
        throw new JspException(e);
      } catch (HibernateException e) {
        throw new JspException(e);
      }
    }

    return SKIP_BODY;
  }


  public void release() {
    super.release();
    init();
  }

  private void init() {
    id = null;
    type = null;
    scope = "request";
  }

}