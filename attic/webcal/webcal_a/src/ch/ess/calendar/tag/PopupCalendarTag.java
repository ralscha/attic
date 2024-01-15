package ch.ess.calendar.tag;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ch.ess.util.*;


public class PopupCalendarTag extends TagSupport {

  private static final long serialVersionUID = 1L;
  
  protected String elem = null;
  protected String form = null;

  public PopupCalendarTag() {
    init();
  }

  public String getElem() {

    return elem;
  }

  public String getForm() {

    return form;
  }

  public void setElem(String elem) {
    this.elem = elem;
  }

  public void setForm(String form) {
    this.form = form;
  }

  // --------------------------------------------------------- Public Methods

  /**
   * Process the start tag.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    
    BrowserDetector bd = new BrowserDetector((HttpServletRequest)pageContext.getRequest());
    String browserName = bd.getBrowserName();
    float version = bd.getBrowserVersion();

    JspWriter writer = pageContext.getOut();

    try {

      if (((version >= 5.5) && (browserName.equals(BrowserDetector.MSIE))) || 
              (version >= 5.0) && (browserName.equals(BrowserDetector.MOZILLA))) {
        writer.print("<a href=\"javascript: void(0);\" onclick='popUpCalendar(this, " + form + "." + elem + ", \"dd.mm.yyyy\")'>");
        writer.print("<img src=\"images/calendar.gif\" alt=\"\" width=\"34\" height=\"21\" border=\"0\" align=\"top\">");
        writer.print("</a>");
      } else {
        writer.print(
              "<a href=\"javascript:calpopup('calendar.jsp?elem=" + elem + "&form=" + form + "',document." + form + "." + 
              elem + ".value)\">");
        writer.print("<img src=\"images/popupcal.gif\" border=\"0\" width=\"21\" height=\"18\" alt=\"\" align=\"bottom\"></a>");
      }
    } catch (IOException e) {
      throw new JspException(e.toString());
    }

    // Continue processing this page
    return (SKIP_BODY);
  }

  public void release() {
    super.release();
    init();
  }

  private void init() {
    elem = null;
    form = null;
  }
}