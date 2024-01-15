
package ch.ess.tag.table;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.swing.table.*;



public class TableTag extends TagSupport {

  public static final int DEFAULT_MAX_PAGE_ITEMS = 10;
  public static final int DEFAULT_MAX_INDEX_PAGES = 10;
  public static final String MODEL = "model";
  
  protected String name;
  protected String scope = null;
  protected String url = null;
  protected int maxPageItems = DEFAULT_MAX_PAGE_ITEMS;
  protected int maxIndexPages = DEFAULT_MAX_INDEX_PAGES;
  protected JSPTableModel tableModel = null;

  public TableTag() {
    init();
  }

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

  //URL property
  public void setUrl(String value) {
    url = value;
  }

  public String getUrl() {
    return url;
  }

  //MaxPageItems property
  public void setMaxPageItems(int value) {
    maxPageItems = value;
  }

  public int getMaxPageItems() {
    return maxPageItems;
  }

  //MaxIndexPags property
  public void setMaxIndexPages(int value) {
    maxIndexPages = value;
  }

  public int getMaxIndexPages() {
    return maxIndexPages;
  }

  /* END properties */
  
  public JSPTableModel getModel() {
    return tableModel;
  }

  public void addParam(String name, String value) {

    if (value == null) {
      value = pageContext.getRequest().getParameter(name);
    }

    if (value != null) {
      try {
        name = java.net.URLEncoder.encode(name, "UTF-8");
        value = java.net.URLEncoder.encode(value, "UTF-8");

      } catch (UnsupportedEncodingException e) {
        System.err.println(e);
      }

      if (url.indexOf('?') == -1) {
        url += "?" + name + "=" + value;
      } else {
        url += "&" + name + "=" + value;
      }
    }
  }

  public int doStartTag() throws JspException {

    Object model = lookup(pageContext, name, scope);
    tableModel = null;

    if (model instanceof TableModel) {
      tableModel = new JSPTableModel((TableModel)model);
    } else if (model instanceof JSPTableModel) {
      tableModel = (JSPTableModel)model;
    }

    if ((tableModel != null) && (tableModel.getTotalRowCount() > 0)) {

      //tableModel.set maxIndexPages
      tableModel.setRowsPerPage(maxPageItems);

      HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

      tableModel.processRequest(request);

      if (url == null) {
        url = ((HttpServletRequest)pageContext.getRequest()).getRequestURI();

        if (url.startsWith(request.getContextPath())) {
          url = url.substring(request.getContextPath().length());
        }

        int i = url.indexOf('?');

        if (i != -1) {
          url = url.substring(0, i);
        }
      }

      pageContext.setAttribute(MODEL, tableModel);

      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }

  public int doEndTag() throws JspException {
    return EVAL_PAGE;
  }

  public void release() {
    super.release();
    init();
  }
  
  private void init() {
    maxPageItems = DEFAULT_MAX_PAGE_ITEMS;
    maxIndexPages = DEFAULT_MAX_INDEX_PAGES;
    url = null;
    name = null;
    scope = null;
    tableModel = null;  
  }

  public static Object lookup(PageContext pageContext, String name, String scope) {

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
