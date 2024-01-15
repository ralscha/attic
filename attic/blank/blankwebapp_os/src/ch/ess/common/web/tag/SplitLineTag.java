package ch.ess.common.web.tag;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Replaces LineFeed with <br>
 *
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 12:24:40 $ 
 * 
 * @jsp.tag name="splitLine" body-content="JSP"
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