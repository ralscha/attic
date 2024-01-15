
package ch.ess.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.NDC;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:28 $
 *    
 * @web.filter name="ndcFilter"
 * @web.filter-mapping url-pattern="*.do"
 */
public class NDCFilter implements Filter {

  public void init(FilterConfig config) {
    //no action
  }

  public void destroy() {
    //no action
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      try {
        HttpSession session = ((HttpServletRequest)request).getSession();

        NDC.push(session.getId());
        chain.doFilter(request, response);
      } finally {
        NDC.pop();
        NDC.remove();
      }
    } else {
      chain.doFilter(request, response);
    }
  }
}
