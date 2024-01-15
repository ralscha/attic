

package ch.ess.tag.table;

import java.net.*;
import java.util.*;

import javax.servlet.jsp.*;

import org.apache.struts.util.*;



public class PrevTag extends IndexTagSupport {

  public static final String PREV_PAGE_URL = "prevPageURL";
  public static final String FIRST_PAGE_URL = "firstPageURL";
  public static final String PREV_PAGE_NO = "prevPageNo";

  public int doStartTag() throws JspException {

    super.doStartTag();

    if (!tableTag.getModel().isFirstPage()) {
      int prevPage = tableTag.getModel().getCurrentPage() - 1;
      
      /*
      HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
      StringBuffer prevUrl = new StringBuffer();
      StringBuffer firstUrl = new StringBuffer();

      prevUrl.append(tableTag.getUrl());
      firstUrl.append(tableTag.getUrl());

      if (tableTag.getUrl().indexOf('?') == -1) {
        prevUrl.append("?");
        firstUrl.append("?");
      } else {
        prevUrl.append("&");
        firstUrl.append("&");
      }

      prevUrl.append("page=").append(prevPage);
      firstUrl.append("page=1");
      pageContext.setAttribute(PREV_PAGE_URL, response.encodeURL(prevUrl.toString()));
      pageContext.setAttribute(FIRST_PAGE_URL, response.encodeURL(firstUrl.toString()));
      */

      Map paramMap = new HashMap();
      paramMap.put("tpage", String.valueOf(prevPage));
      try {
        pageContext.setAttribute(PREV_PAGE_URL,
                    RequestUtils.computeURL(pageContext, null, null, tableTag.getUrl(), null, paramMap, null, true));
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }


      paramMap.clear();
      paramMap.put("tpage", "1");
      try {
        pageContext.setAttribute(FIRST_PAGE_URL,
                    RequestUtils.computeURL(pageContext, null, null, tableTag.getUrl(), null, paramMap, null, true));
      } catch (MalformedURLException e) {
        throw new JspException(e.toString());
      }      
      
      pageContext.setAttribute(PREV_PAGE_NO, new Integer(prevPage));
    } else {
      if (!showAlways) {
        return SKIP_BODY;
      }

      pageContext.removeAttribute(PREV_PAGE_URL);
      pageContext.removeAttribute(FIRST_PAGE_URL);
      pageContext.removeAttribute(PREV_PAGE_NO);
    }

    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws JspException {

    pageContext.removeAttribute(PREV_PAGE_URL);
    pageContext.removeAttribute(FIRST_PAGE_URL);
    pageContext.removeAttribute(PREV_PAGE_NO);

    return (EVAL_PAGE);
  }
}
