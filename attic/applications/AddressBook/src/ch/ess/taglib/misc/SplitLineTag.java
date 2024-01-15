/*
 * $Header: c:/cvs/pbroker/src/ch/ess/taglib/misc/SplitLineTag.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.taglib.misc;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import java.util.*;


/**
 * Class SplitLineTag
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class SplitLineTag extends BodyTagSupport {

  public int doAfterBody() throws JspException {

    if (bodyContent != null) {
      try {
        JspWriter writer = bodyContent.getEnclosingWriter();
        String bodyString = bodyContent.getString();
        StringTokenizer st = new StringTokenizer(bodyString, "\n");

        while (st.hasMoreTokens()) {
          String nextToken = st.nextToken();

          writer.print(nextToken);

          if (st.hasMoreTokens()) {
            writer.println("<br>");
          }
        }
      } catch (IOException ioe) {
        throw new JspException(ioe.toString());
      }
    }

    return SKIP_BODY;
  }

  public int doEndTag() throws JspException {
    return EVAL_PAGE;
  }

  public void release() {
    super.release();
  }
}
