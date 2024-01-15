package ch.ess.taglib.misc;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


public class SuppressTag extends BodyTagSupport {


  public int doAfterBody() throws JspException {
    BodyContent bodycontent = getBodyContent();
    String s = bodycontent.getString();
    StringBuffer stringbuffer = new StringBuffer(2048);
    JspWriter jspwriter = bodycontent.getEnclosingWriter();

    char c1 = '\n';
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '\r') 
        c = '\n';

      if (((c == ' ') || (c == '\t')) && c1 == '\n' || c1 == '\t') {
        while(((c == ' ') || (c == '\t')) && (i < s.length()-1)) {
          i++;
          
          c = s.charAt(i);
          if (c == '\r') 
            c = '\n';          
        }
      }

      if ((c1 != '\n' || c != '\n' && c != '\t') &&
          (c1 != '\t' && c != '\t')) {
        c1 = c;
        stringbuffer.append(c);
      }
    }

    try {
      jspwriter.print(stringbuffer.toString());
    } catch (Exception e) {
      System.err.println(e);
    }

    return SKIP_BODY;
  }

}
