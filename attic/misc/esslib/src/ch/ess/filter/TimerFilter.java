

package ch.ess.filter;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.*;

public class TimerFilter implements Filter {

  private static final Log LOG = LogFactory.getLog(TimerFilter.class.getPackage().getName());

  public void init(FilterConfig config) {
  }

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    long before = System.currentTimeMillis();

    chain.doFilter(request, response);

    long after = System.currentTimeMillis();
    String name = "";

    if (request instanceof HttpServletRequest) {
      name = ((HttpServletRequest)request).getRequestURI();
    }

    name += ";" + (after - before);

    LOG.info(name);
    
  }
}
