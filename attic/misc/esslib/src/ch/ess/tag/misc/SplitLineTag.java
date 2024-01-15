
package ch.ess.tag.misc;

import java.io.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


/**
 * Ersetzt LineFeed durch eine <br> Tag
 *
 * @author Ralph Schaer
 * @version 1.0  28.09.2000
 */

public class SplitLineTag extends BodyTagSupport {

  public int doAfterBody() throws JspException {

    if (bodyContent != null) {
      try {
        JspWriter writer = getPreviousOut();
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


}
