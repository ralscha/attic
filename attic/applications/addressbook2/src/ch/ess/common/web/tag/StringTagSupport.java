package ch.ess.common.web.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:29 $ 
  */
public abstract class StringTagSupport extends BodyTagSupport {

  public int doEndTag() throws JspException {

    if ((bodyContent == null)) {
      return EVAL_PAGE;
    }

    String text = "";
    if (bodyContent != null) {
      StringWriter body = new StringWriter();
      try {
        bodyContent.writeOut(body);
        text = body.toString();
      } catch (IOException ioe) {
        throw new JspException(ioe);
      }
    }

    text = changeString(text);

    JspWriter writer = pageContext.getOut();
    try {
      writer.print(text);
    } catch (IOException e) {
      throw new JspException(e);
    }

    return (EVAL_PAGE);
  }

  public abstract String changeString(String str);

}
