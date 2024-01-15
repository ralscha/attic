/*
 * $Header: c:/cvs/pbroker/src/ch/ess/taglib/misc/URLEncodeTag.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.taglib.misc;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import java.util.*;


/**
 * @author Ralph Schaer
 * @version 1.0  28.11.2000
 */
public final class URLEncodeTag extends TagSupport {

  public String url = null;

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public int doStartTag() throws JspException {

    if (url != null) {
      try {
        JspWriter out = pageContext.getOut();

        out.print(java.net.URLEncoder.encode(url));
      } catch (IOException ioe) {
        throw new JspException(ioe.toString());
      }
    }

    return (SKIP_BODY);
  }

  public int doEndTag() throws JspException {
    return (EVAL_PAGE);
  }

  public void release() {

    super.release();

    url = null;
  }
}
