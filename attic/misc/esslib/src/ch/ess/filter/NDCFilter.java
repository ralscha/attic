
package ch.ess.filter;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.*;


public class NDCFilter implements Filter {


  public void init(FilterConfig config) {
  }

  public void destroy() {
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
