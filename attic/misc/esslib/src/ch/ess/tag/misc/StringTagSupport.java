package ch.ess.tag.misc;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

abstract public class StringTagSupport extends BodyTagSupport {


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
        ioe.printStackTrace();
      }
    }

    text = changeString(text);

    JspWriter writer = pageContext.getOut();
    try {
      writer.print(text);
    } catch (IOException e) {
      throw new JspException(e.toString());
    }

    return (EVAL_PAGE);
  }


  abstract public String changeString(String str);


}
