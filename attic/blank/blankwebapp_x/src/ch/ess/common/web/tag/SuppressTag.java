package ch.ess.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Deletes all unnecessary whitespaces
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 *  
 * @jsp.tag name="suppress" body-content="JSP"
 */

public class SuppressTag extends BodyTagSupport {

  public int doAfterBody() throws JspException {

    BodyContent bodycontent = getBodyContent();
    String s = bodycontent.getString();
    StringBuffer stringbuffer = new StringBuffer(2048);
    JspWriter jspwriter = getPreviousOut();
    char c1 = '\n';

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);

      if (c == '\r') {
        c = '\n';
      }

      if (((c == ' ') || (c == '\t')) && (c1 == '\n') || (c1 == '\t')) {
        while (((c == ' ') || (c == '\t')) && (i < s.length() - 1)) {
          i++;

          c = s.charAt(i);

          if (c == '\r') {
            c = '\n';
          }
        }
      }

      if (((c1 != '\n') || ((c != '\n') && (c != '\t'))) && ((c1 != '\t') && (c != '\t'))) {
        c1 = c;

        stringbuffer.append(c);
      }
    }

    try {
      jspwriter.print(stringbuffer.toString());
    } catch (Exception e) {
      throw new JspException(e);
    }

    return SKIP_BODY;
  }
}