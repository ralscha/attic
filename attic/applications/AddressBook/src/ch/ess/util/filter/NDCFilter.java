/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/filter/NDCFilter.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;


/**
 * Class NDCFilter
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class NDCFilter implements Filter {

  private FilterConfig config = null;

  public void init(FilterConfig config) {
    this.config = config;
  }

  public void destroy() {
    config = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      try {
        HttpSession session = ((HttpServletRequest)request).getSession();

        NDC.push(session.getId());
        chain.doFilter(request, response);
      } finally {
        NDC.pop();
      }
    } else {
      chain.doFilter(request, response);
    }
  }
}
