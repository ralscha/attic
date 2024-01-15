/*
 * $Header: c:/cvs/pbroker/src/ch/ess/pbroker/filter/CleanSessionFilter.java,v 1.1.1.1 2002/02/26 06:46:54 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:54 $
 */

package ch.sr.lottowin.filter;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Class CleanSessionFilter
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:54 $
 */
public class CleanSessionFilter implements Filter {

  //private FilterConfig config = null;

  public void init(FilterConfig cfg) {
    //this.config = cfg;
  }

  public void destroy() {
    //config = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    if (request.getParameter("clean") != null) {
      if (request instanceof HttpServletRequest) {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();

        CleanSession.cleanSession(session);
      }
    }

    chain.doFilter(request, response);
  }
}
