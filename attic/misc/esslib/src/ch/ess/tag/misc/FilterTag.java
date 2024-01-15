
package ch.ess.tag.misc;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ch.ess.util.*;


/**
 * Ersetzt Umlaute und Ampersand durch entsprechende HTML Entitäten
 *
 * @author Ralph Schaer
 * @version 1.0  28.09.2000
 */

public class FilterTag extends BodyTagSupport {

  public int doAfterBody() throws JspException {

    if (bodyContent != null) {
      try {
        JspWriter writer = getPreviousOut();
        String bodyString = bodyContent.getString();
        
        bodyString = Util.filterUmlaute(bodyString);
        
        writer.print(bodyString);
        
      } catch (IOException ioe) {
        throw new JspException(ioe.toString());
      }
    }

    return SKIP_BODY;
  }


}
