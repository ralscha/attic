
package ch.ess.tag.misc;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Schreibt den Body in eine Variable mit namen id
 *
 * @author Ralph Schaer
 * @version 1.0  28.09.2000
 */

public class ToStringTag extends BodyTagSupport {

  private String id;

  public ToStringTag() {
    init();
  }

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
