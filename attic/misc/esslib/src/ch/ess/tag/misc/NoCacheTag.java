
package ch.ess.tag.misc;

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

    response.setHeader("Cache-Control", "no-store");
    //response.setHeader("Cache-Control", "no-cache");
        
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    


    return (SKIP_BODY);
  }

  public int doEndTag() throws JspException {
    return (EVAL_PAGE);
  }
}
