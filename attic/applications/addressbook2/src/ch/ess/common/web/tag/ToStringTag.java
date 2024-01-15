package ch.ess.common.web.tag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/03 15:44:41 $ 
  * 
  * @jsp.tag name="toString" body-content="JSP" 
  * @jsp.variable name-from-attribute="id" class="java.lang.String" declare="true" scope="AT_END"
  */

public class ToStringTag extends BodyTagSupport {

  private String id;

  public ToStringTag() {
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

  public int doAfterBody() {

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
