package ch.ess.common.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $  
 * 
 * @web.filter name="cleanSessionFilter"
 * @web.filter-mapping url-pattern="*.do"
 * @web.filter-init-param name="excludes" value="__oscache_cache,clickstream,clientinfo" 
 * 
 */
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

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    if (request.getParameter("clean") != null) {

      if (request instanceof HttpServletRequest) {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        CleanSession.cleanSession(session, excludeSet);
      }
    }
    chain.doFilter(request, response);
  }

}