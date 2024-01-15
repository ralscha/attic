package ch.ess.cal.web.tag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $ 
 * 
 * @jsp.tag name="toString" body-content="JSP" 
 * @jsp.variable name-from-attribute="id" class="java.lang.String" declare="true" scope="AT_END"
 */

public class ToStringTag extends BodyTagSupport {

  private String id;

  public ToStringTag() {
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

  @Override
  public int doAfterBody() {

    pageContext.setAttribute(getId(), getBodyContent().getString(), PageContext.PAGE_SCOPE);

    return SKIP_BODY;
  }

  @Override
  public void release() {
    super.release();
    init();
  }

  private void init() {
    id = null;
  }
}
