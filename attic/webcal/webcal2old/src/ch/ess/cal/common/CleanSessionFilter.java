package ch.ess.cal.common;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;




public class CleanSessionFilter implements Filter {

  private Set excludeSet = new HashSet();

  public void init(FilterConfig config) {
    String excludeParameter = config.getInitParameter("excludes");
    if (excludeParameter != null) {
      
      StringTokenizer st = new StringTokenizer(excludeParameter, ",");
      while (st.hasMoreTokens()) {
        String token = st.nextToken();
        excludeSet.add(token);       
      }
    }
    
    
  }

  public void destroy() {
   //do nothing
  }



  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

    if (request.getParameter("clean") != null) {

      if (request instanceof HttpServletRequest) {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        CleanSession.cleanSession(session, excludeSet);
      }
    }
    chain.doFilter(request, response);
  }

}