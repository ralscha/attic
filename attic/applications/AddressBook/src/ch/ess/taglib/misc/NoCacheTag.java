/*
 * $Header: c:/cvs/pbroker/src/ch/ess/taglib/misc/NoCacheTag.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.taglib.misc;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


/**
 * Schreibt Header Informationen in die Response damit die
 * Seite die diesen Tag enthält nicht gecacht wird
 *
 * @author Ralph Schaer
 * @version 1.0  28.09.2000
 */
public final class NoCacheTag extends TagSupport {

  public int doStartTag() throws JspException {

    HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();

    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "No-cache");

    //response.setHeader("Cache-Control", "No-store");
    response.setDateHeader("Expires", 1);

    return (SKIP_BODY);
  }

  public int doEndTag() throws JspException {
    return (EVAL_PAGE);
  }
}
