

package ch.ess.tag.table;

import java.net.*;
import java.util.*;

import javax.servlet.jsp.*;

import org.apache.struts.util.*;



public class NextTag extends IndexTagSupport {

  public static final String NEXT_PAGE_URL = "nextPageURL";
  public static final String LAST_PAGE_URL = "lastPageURL";
  public static final String NEXT_PAGE_NO = "nextPageNo";

  public int doStartTag() throws JspException {

    super.doStartTag();

    if (!tableTag.getModel().isLastPage()) {
      int nextPage = tableTag.getModel().getCurrentPage() + 1;
      
      /*
      HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
      StringBuffer nextUrl = new StringBuffer();
      StringBuffer lastUrl = new StringBuffer();

      nextUrl.append(tableTag.getUrl());
      lastUrl.append(tableTag.getUrl());

      if (tableTag.getUrl().indexOf('?') == -1) {
        nextUrl.append("?");
        lastUrl.append("?");
      } else {
        nextUrl.append("&");
        lastUrl.append("&");
      }

      nextUrl.append("page=").append(nextPage);
      lastUrl.append("page=").append(tableTag.getModel().getTotalPages());
      pageContext.setAttribute(NEXT_PAGE_URL, response.encodeURL(nextUrl.toString()));
      pageContext.setAttribute(LAST_PAGE_URL, response.encodeURL(lastUrl.toString()));
      
      */
      Map paramMap = new HashMap();
      paramMap.put("tpage", String.valueOf(nextPage));
      try {        
        pageContext.setAttribute(NEXT_PAGE_URL, 
                    RequestUtils.computeURL(pageContext, null, null, tableTag.getUrl(), null, paramMap, null, true));
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }
      
      
      paramMap.clear();
      paramMap.put("tpage", String.valueOf(tableTag.getModel().getTotalPages()));
      try {
        pageContext.setAttribute(LAST_PAGE_URL,
                    RequestUtils.computeURL(pageContext, null, null, tableTag.getUrl(), null, paramMap, null, true));
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }      
      
      
      pageContext.setAttribute(NEXT_PAGE_NO, new Integer(nextPage));
    } else {
      if (!showAlways) {
        return SKIP_BODY;
      }

      pageContext.removeAttribute(NEXT_PAGE_URL);
      pageContext.removeAttribute(LAST_PAGE_URL);
      pageContext.removeAttribute(NEXT_PAGE_NO);
    }

    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws JspException {

    pageContext.removeAttribute(NEXT_PAGE_URL);
    pageContext.removeAttribute(LAST_PAGE_URL);
    pageContext.removeAttribute(NEXT_PAGE_NO);

    return (EVAL_PAGE);
  }
}
