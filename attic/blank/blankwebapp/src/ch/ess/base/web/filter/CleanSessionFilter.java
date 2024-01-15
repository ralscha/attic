package ch.ess.base.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.apache.commons.lang.StringUtils;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:25 $  
 * 
 * @web.filter name="cleanSessionFilter" 
 */
public class CleanSessionFilter implements Filter {

  private static Set<String> sessionObjectSet = new HashSet<String>();

//  static {
//    sessionObjectSet.add("mapForm");
//    sessionObjectSet.add("categoryForm");
//    sessionObjectSet.add("eventForm");
//    sessionObjectSet.add("holidayForm");
//    sessionObjectSet.add("textResourceForm");
//    sessionObjectSet.add("userForm");
//    sessionObjectSet.add("userConfigForm");
//    sessionObjectSet.add("groupMonthForm");
//    sessionObjectSet.add("categorys");
//    sessionObjectSet.add("events");
//    sessionObjectSet.add("groups");
//    sessionObjectSet.add("holidays");
//    sessionObjectSet.add("triggers");
//    sessionObjectSet.add("textresources");
//    sessionObjectSet.add("users");
//    sessionObjectSet.add("usergroups");
//    sessionObjectSet.add("categoryLabels");
//  }

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
    try {
      InputStream is = config.getServletContext().getResourceAsStream("/WEB-INF/cleansession.properties");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = br.readLine()) != null) {
        String trimmedLine = line.trim();
        if (!trimmedLine.startsWith("#") && StringUtils.isNotBlank(trimmedLine)) {
          sessionObjectSet.add(trimmedLine);
        }
      }
      br.close();
      is.close();
    } catch (IOException e) {
      config.getServletContext().log("CleanSessionFilter.init", e);
    }

  }

  public void destroy() {
    //do nothing    
  }

}