
package ch.ess.tag.table;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;



public class ColumnHeadersTag extends BodyTagSupport {

  public static final String COLUMN_NAME = "columnName";
  public static final String COLUMN_NO = "columnNo";
  public static final String SORT_URL = "sortURL";
  public static final String SORT_ASCENDING = "isAscending";
  public static final String SORT_COLUMN = "isSortColumn";
  
  private JSPTableModel model;
  private String baseURL;
  private int index;

  public ColumnHeadersTag() {
    init();
  }

  public int doStartTag() throws JspException {

    index = 0;
    
    TableTag tableTag = (TableTag)findAncestorWithClass(this, TableTag.class);

    if (tableTag == null) {
      throw new JspTagException("not nested within a TableTag");
    }

    model = tableTag.getModel();
    baseURL = tableTag.getUrl();

    while (model.getColumnName(index).trim().equals("")) {
      index++;
    }

    if (index < model.getColumnCount()) {
      try {
        setAttributes();
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }

      index++;

      return EVAL_BODY_BUFFERED;
    } else {
      return SKIP_BODY;
    }
  }

  public int doAfterBody() throws JspException {

    BodyContent body = getBodyContent();

    if (body != null) {
      try {
        body.writeOut(getPreviousOut());
      } catch (IOException e) {
        throw new JspException(e.toString());
      }

      body.clearBody();
    }

    while (model.getColumnName(index).trim().equals("")) {
      index++;
    }

    if (index < model.getColumnCount()) {
      try {
        setAttributes();
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }

      index++;

      return EVAL_BODY_AGAIN;
    } else {
      return SKIP_BODY;
    }
  }

  private void setAttributes() throws MalformedURLException {

    pageContext.setAttribute(COLUMN_NAME, model.getColumnName(index));
    pageContext.setAttribute(COLUMN_NO, new Integer(index));

    boolean isSortColumn = model.getSortColumn() == index;

    pageContext.setAttribute(SORT_COLUMN, new Boolean(isSortColumn));

    if (isSortColumn) {
      pageContext.setAttribute(SORT_ASCENDING, new Boolean(model.getSortOrder()));
    } else {
      pageContext.removeAttribute(SORT_ASCENDING);
    }

    /*
    HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
    
    StringBuffer url = new StringBuffer();

    url.append(baseURL);

    if (baseURL.indexOf('?') == -1) {
      url.append("?");
    } else {
      url.append("&");
    }

    url.append("page=").append(model.getCurrentPage()).append("&");

    if (isSortColumn) {
      if (model.getSortOrder()) {
        url.append("sort=d").append(index);
      } else {
        url.append("sort=a").append(index);
      }
    } else {
      url.append("sort=a").append(index);
    }

    pageContext.setAttribute(SORT_URL, response.encodeURL(url.toString()));
    */
    Map paramMap = new HashMap();
    paramMap.put("tpage", String.valueOf(model.getCurrentPage()));
    if (isSortColumn) {
      if (model.getSortOrder()) {
        paramMap.put("sort", "d"+index);                
      } else {
        paramMap.put("sort", "a"+index);
      }
    } else {
      paramMap.put("sort", "a"+index);      
    }
    String url = RequestUtils.computeURL(pageContext, null, null, baseURL, null, paramMap, null, true);
    pageContext.setAttribute(SORT_URL, url);
    
  }

  public int doEndTag() throws JspException {

    // Continue processing this page
    pageContext.removeAttribute(COLUMN_NAME);
    pageContext.removeAttribute(COLUMN_NO);
    pageContext.removeAttribute(SORT_URL);
    pageContext.removeAttribute(SORT_ASCENDING);
    pageContext.removeAttribute(SORT_COLUMN);

    return (EVAL_PAGE);
  }

  public void release() {
    super.release();
    init();
  }
  
  private void init() {
    index = 0;
    model = null;
    baseURL = null;  
  }
}
