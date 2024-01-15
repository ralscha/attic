

package ch.ess.tag.table;

import java.net.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;


public class ColumnHeaderTag extends TagSupport {

  public static final String SORT_URL = "sortURL";
  public static final String SORT_ASCENDING = "isAscending";
  public static final String SORT_COLUMN = "isSortColumn";
  
  private JSPTableModel model;
  private String baseURL;
  protected int col;

  public ColumnHeaderTag() {
    init();
  }

  public void setCol(int col) {
    this.col = col;
  }

  public int getCol() {
    return this.col;
  }

  public int doStartTag() throws JspException {

    TableTag tableTag = (TableTag)findAncestorWithClass(this, TableTag.class);

    if (tableTag == null) {
      throw new JspTagException("not nested within a TableTag");
    }

    model = tableTag.getModel();
    baseURL = tableTag.getUrl();

    try {
      setAttributes();
    } catch (MalformedURLException e) {
      throw new JspException(e.toString());
    }

    return EVAL_BODY_INCLUDE;
  }

  private void setAttributes() throws MalformedURLException {

    boolean isSortColumn = (model.getSortColumn() == col);

    if (isSortColumn) {
      pageContext.setAttribute(SORT_COLUMN, Boolean.TRUE);

      if (model.getSortOrder()) {
        pageContext.setAttribute(SORT_ASCENDING, Boolean.TRUE);
      } else {
        pageContext.setAttribute(SORT_ASCENDING, Boolean.FALSE);
      }
    } else {
      pageContext.setAttribute(SORT_COLUMN, Boolean.FALSE);
      pageContext.removeAttribute(SORT_ASCENDING);
    }
/*
    HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
    StringBuffer url = new StringBuffer(baseURL);

    if (baseURL.indexOf('?') == -1) {
      url.append("?");
    } else {
      url.append("&");
    }

    url.append("page=").append(model.getCurrentPage()).append("&");

    if (isSortColumn) {
      if (model.getSortOrder()) {
        url.append("sort=d").append(col);
      } else {
        url.append("sort=a").append(col);
      }
    } else {
      url.append("sort=a").append(col);
    }

    pageContext.setAttribute(SORT_URL, response.encodeURL(url.toString()));
*/    

    Map paramMap = new HashMap();
    paramMap.put("tpage", String.valueOf(model.getCurrentPage()));
    if (isSortColumn) {
      if (model.getSortOrder()) {
        paramMap.put("sort", "d"+col);
      } else {
        paramMap.put("sort", "a"+col);
      }
    } else {
      paramMap.put("sort", "a"+col);
    }
    String url = RequestUtils.computeURL(pageContext, null, null, baseURL, null, paramMap, null, true);
    pageContext.setAttribute(SORT_URL, url);    
    
  }

  public int doEndTag() throws JspException {

    // Continue processing this page
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
    col = -1;
    model = null;
    baseURL = null;  
  }
}
