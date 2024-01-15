/*
 * $Header: c:/cvs/pbroker/src/ch/ess/taglib/misc/ToStringTag.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.taglib.misc;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;


/**
 * Class ToStringTag
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class ToStringTag extends BodyTagSupport {

  private String id;

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

    id = null;
  }
}
