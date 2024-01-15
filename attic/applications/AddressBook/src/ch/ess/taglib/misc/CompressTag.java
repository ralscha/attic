/*
 * $Header: c:/cvs/pbroker/src/ch/ess/taglib/misc/CompressTag.java,v 1.1 2002/03/18 09:17:58 sr Exp $
 * $Revision: 1.1 $
 * $Date: 2002/03/18 09:17:58 $
 */

package ch.ess.taglib.misc;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.log4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.zip.*;

/**
 * Class CompressTag
 *
 * @version $Revision: 1.1 $ $Date: 2002/03/18 09:17:58 $
 */
public class CompressTag extends BodyTagSupport {
  
  private String sBody;

  public int doAfterBody() throws JspException {
    BodyContent bodycontent = getBodyContent();
    if (bodycontent == null) {
      sBody = "";
    } else {
      sBody = bodycontent.getString();
      bodycontent.clearBody();
    }
    return SKIP_BODY;
  }

  public int doEndTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
    
    String acceptHeader = request.getHeader("Accept-Encoding");
    if ((acceptHeader != null) && (acceptHeader.indexOf("gzip") >= 0)) {        
      response.setHeader("Content-Encoding", "gzip");
      try {
        pageContext.getOut().clearBuffer();
        ServletOutputStream servletoutputstream = response.getOutputStream();
        GZIPOutputStream gzipoutputstream = new GZIPOutputStream(servletoutputstream);
        gzipoutputstream.write(sBody.getBytes());
        gzipoutputstream.close();
        servletoutputstream.flush();
        servletoutputstream.close();
      } catch (Exception exception) {
        throw new JspException(exception);
      }
    } else {
      try {
        pageContext.getOut().write(sBody);
      } catch (Exception ex) {
        throw new JspException(ex);
      }
    }
    
    return SKIP_PAGE;
  }

  public void release() {
    super.release();
    sBody = null;
  }

}
