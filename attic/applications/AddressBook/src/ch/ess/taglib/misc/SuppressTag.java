/*
 * $Header: c:/cvs/pbroker/src/ch/ess/taglib/misc/SuppressTag.java,v 1.2 2002/03/18 09:17:58 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/03/18 09:17:58 $
 */

package ch.ess.taglib.misc;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


/**
 * Class SuppressTag
 *
 * @version $Revision: 1.2 $ $Date: 2002/03/18 09:17:58 $
 */
public class SuppressTag extends BodyTagSupport {

  public int doAfterBody() throws JspException {

    BodyContent bodycontent = getBodyContent();
    String s = bodycontent.getString();
    StringBuffer stringbuffer = new StringBuffer(2048);
    JspWriter jspwriter = bodycontent.getEnclosingWriter();
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
