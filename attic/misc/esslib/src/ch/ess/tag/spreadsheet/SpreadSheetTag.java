
package ch.ess.tag.spreadsheet;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ch.ess.util.spreadsheet.*;


public class SpreadSheetTag extends TagSupport {

  private String name;
  private String scope = null;
  private HtmlSpreadsheetTableModel model = null;

  //Name property
  public void setName(String value) {
    name = value;
  }

  public String getName() {
    return name;
  }

  //Scope property
  public void setScope(String value) {
    scope = value;
  }

  public String getScope() {
    return scope;
  }

  /* END properties */
  public HtmlSpreadsheetTableModel getModel() {
    return model;
  }

  public int doStartTag() throws JspException {

    model = (HtmlSpreadsheetTableModel)lookup(pageContext, name, scope);

    if (model == null) {

      //neu erstellen
      model = new HtmlSpreadsheetTableModel(name);

      setAttribute(pageContext, name, model, scope);
    }

    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws JspException {

    try {
      JspWriter out = pageContext.getOut();

      out.println("<script language=\"JavaScript\" type=\"text/javascript\">");
      out.println("var stack = new Stack(10);");
      out.println(model.getScriptHtml());
      out.println("</script>");
    } catch (IOException ioe) {
      throw new JspException(ioe.toString());
    }

    return EVAL_PAGE;
  }

  public void release() {

    super.release();

    name = null;
    scope = null;
    model = null;
  }

  private void setAttribute(PageContext pageContext, String name, Object o, String scope) {

    if (scope == null) {
      pageContext.setAttribute(name, o);
    } else if (scope.equalsIgnoreCase("page")) {
      pageContext.setAttribute(name, o, PageContext.PAGE_SCOPE);
    } else if (scope.equalsIgnoreCase("request")) {
      pageContext.setAttribute(name, o, PageContext.REQUEST_SCOPE);
    } else if (scope.equalsIgnoreCase("session")) {
      pageContext.setAttribute(name, o, PageContext.SESSION_SCOPE);
    } else if (scope.equalsIgnoreCase("application")) {
      pageContext.setAttribute(name, o, PageContext.APPLICATION_SCOPE);
    } else {
      throw new IllegalArgumentException(scope);
    }
  }

  private Object lookup(PageContext pageContext, String name, String scope) {

    Object bean = null;

    if (scope == null) {
      bean = pageContext.findAttribute(name);
    } else if (scope.equalsIgnoreCase("page")) {
      bean = pageContext.getAttribute(name, PageContext.PAGE_SCOPE);
    } else if (scope.equalsIgnoreCase("request")) {
      bean = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
    } else if (scope.equalsIgnoreCase("session")) {
      bean = pageContext.getAttribute(name, PageContext.SESSION_SCOPE);
    } else if (scope.equalsIgnoreCase("application")) {
      bean = pageContext.getAttribute(name, PageContext.APPLICATION_SCOPE);
    } else {
      throw new IllegalArgumentException(scope);
    }

    return (bean);
  }
}
