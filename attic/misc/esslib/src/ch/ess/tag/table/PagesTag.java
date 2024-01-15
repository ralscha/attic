
package ch.ess.tag.table;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;



public class PagesTag extends BodyTagSupport {

  public static final String PAGE_URL = "pageURL";
  public static final String PAGE_NO = "pageNo";
  
  private String baseURL;
  private int totalPages;
  private int countPage;

  public PagesTag() {
    init();
  }

  public int doStartTag() throws JspException {

    TableTag tableTag = (TableTag)findAncestorWithClass(this, TableTag.class);

    if (tableTag == null) {
      throw new JspTagException("not nested within a TableTag");
    }

    totalPages = tableTag.getModel().getTotalPages();
    baseURL = tableTag.getUrl();

    int maxIndexPages = tableTag.getMaxIndexPages();

    if (totalPages > maxIndexPages) {
      int currentPage = tableTag.getModel().getCurrentPage();

      countPage = currentPage - (maxIndexPages / 2);

      if (countPage < 1) {
        countPage = 1;
      }

      if ((totalPages - countPage + 1) < maxIndexPages) {
        countPage -= maxIndexPages - (totalPages - countPage + 1);
      }

      if (totalPages >= (countPage + maxIndexPages)) {
        totalPages = countPage + maxIndexPages - 1;
      }
    } else {
      countPage = 1;
    }

    if (totalPages > 0) {
      try {
        setAttributes();
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }

      countPage++;

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

    if (countPage <= totalPages) {
      try {
        setAttributes();
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }

      countPage++;

      return EVAL_BODY_AGAIN;
    } else {
      return SKIP_BODY;
    }
  }

  private void setAttributes() throws MalformedURLException {

    pageContext.setAttribute(PAGE_NO, new Integer(countPage));

    /*
    HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
    StringBuffer url = new StringBuffer();

    url.append(baseURL);

    if (baseURL.indexOf('?') == -1) {
      url.append("?");
    } else {
      url.append("&");
    }

    url.append("page=").append(countPage);
    pageContext.setAttribute(PAGE_URL, response.encodeURL(url.toString()));
    */  
    
    Map paramMap = new HashMap();
    paramMap.put("tpage", String.valueOf(countPage));

    pageContext.setAttribute(PAGE_URL,
                RequestUtils.computeURL(pageContext, null, null, baseURL, null, paramMap, null, true));
  
    
  }

  public int doEndTag() throws JspException {

    // Continue processing this page
    pageContext.removeAttribute(PAGE_URL);
    pageContext.removeAttribute(PAGE_NO);

    return (EVAL_PAGE);
  }

  public void release() {
    super.release();
    init();
  }
  
  private void init() {
    baseURL = null;
    totalPages = 0;
    countPage = 1;  
  }
}
