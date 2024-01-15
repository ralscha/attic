package ch.ess.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * @jsp.tag name="toString" body-content="JSP" 
  * @jsp.variable name-from-attribute="id" class="java.lang.String" declare="true" scope="AT_END"
  * 
  */

public class ToStringTag extends BodyTagSupport {

  private String id;

  public ToStringTag() {
    init();
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true" 
   */
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int doAfterBody() throws JspException {

    pageContext.setAttribute(getId(), getBodyContent().getString(), PageContext.PAGE_SCOPE);

    return SKIP_BODY;
  }

  public void release() {
    super.release();
    init();
  }

  private void init() {
    id = null;
  }
}
