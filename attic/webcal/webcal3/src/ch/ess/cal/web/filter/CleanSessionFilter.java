package ch.ess.cal.web.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:13 $  
 * 
 * @web.filter name="cleanSessionFilter" 
 */
public class CleanSessionFilter implements Filter {

  private static Set<String> sessionObjectSet = new HashSet<String>();

  static {
    sessionObjectSet.add("mapForm");
    sessionObjectSet.add("categoryForm");
    sessionObjectSet.add("eventForm");
    sessionObjectSet.add("groupForm");
    sessionObjectSet.add("holidayForm");
    sessionObjectSet.add("resourceForm");
    sessionObjectSet.add("resourceGroupForm");
    sessionObjectSet.add("textResourceForm");
    sessionObjectSet.add("userForm");
    sessionObjectSet.add("userConfigForm");
    sessionObjectSet.add("groupMonthForm");
    sessionObjectSet.add("categorys");
    sessionObjectSet.add("events");
    sessionObjectSet.add("groups");
    sessionObjectSet.add("holidays");
    sessionObjectSet.add("jobs");
    sessionObjectSet.add("triggers");
    sessionObjectSet.add("resources");
    sessionObjectSet.add("resourcegroups");
    sessionObjectSet.add("textresources");
    sessionObjectSet.add("users");
    sessionObjectSet.add("usergroups");
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    if (request.getParameter("clean") != null) {

      if (request instanceof HttpServletRequest) {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();

        for (String sessionObject : sessionObjectSet) {
          session.removeAttribute(sessionObject);
        }
      }
    }
    chain.doFilter(request, response);
  }

  public void init(FilterConfig config) {
    //do nothing
  }

  public void destroy() {
    //do nothing    
  }

}
