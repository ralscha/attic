package ch.ess.tag.misc;

import java.io.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.*;
import org.apache.struts.util.*;



/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.3 $ $Date: 2003/04/09 06:21:23 $
 * @author $Author: sr $
 */
public class ConfirmTag extends TagSupport {

  private String key = null;
  private String functionName = "confirmRequest";

  public ConfirmTag() {
    init();
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getKey() {

    return (this.key);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key DOCUMENT ME!
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getFunctionName() {

    return functionName;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param methodName DOCUMENT ME!
   */
  public void setFunctionName(String methodName) {
    this.functionName = methodName;
  }

  // --------------------------------------------------------- Public Methods

  /**
   * Process the start tag.
   * 
   * @return DOCUMENT ME!
   * 
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {
    
    Locale locale = RequestUtils.retrieveUserLocale(pageContext, null);
    
    MessageResources messages = (MessageResources)pageContext.getAttribute(Globals.MESSAGES_KEY, 
                                                                           PageContext.APPLICATION_SCOPE);
    String part1 = key;
    String part2 = null;

    if (key != null) {

      if ((locale != null) && (messages != null)) {
        part1 = messages.getMessage(locale, key);
      }
    }

    int p = part1.indexOf("{0}");

    if (p != -1) {

      String tmp = part1;
      part1 = tmp.substring(0, p);
      part2 = tmp.substring(p + 3);
    }

    // Print this property value to our output writer, suitably filtered
    JspWriter writer = pageContext.getOut();

    try {
      writer.println("<script type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
      writer.println("<!--");

      if (part2 != null) {
        writer.println("function " + functionName + "(val) {");
        writer.println("var agree = confirm(\"" + part1 + "\"+val+\"" + part2 + "\");");
      } else {
        writer.println("function " + functionName + "() {");
        writer.println("var agree = confirm(\"" + part1 + "\");");
      }
      writer.println("if (agree)");
      writer.println("  return true ;");
      writer.println("else");
      writer.println("  return false ;");
      writer.println("}");
      writer.println("// -->");
      writer.println("</script>");
    } catch (IOException e) {
      throw new JspException(e.toString());
    }

    // Continue processing this page
    return SKIP_BODY;
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    init();
  }

  /**
   * DOCUMENT ME!
   */
  private void init() {
    key = null;
    functionName = "confirmRequest";
  }
}