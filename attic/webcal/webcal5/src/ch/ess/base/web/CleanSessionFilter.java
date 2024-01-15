package ch.ess.base.web;

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

public class CleanSessionFilter implements Filter {

  private static Set<String> sessionObjectSet = new HashSet<String>();

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
        
    if (request.getParameter("startCrumb") != null) {
      if (request instanceof HttpServletRequest) {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        session.removeAttribute(WebConstants.SESSION_CALL_STACK);
        session.removeAttribute(WebConstants.SESSION_CRUMBS_CONTROL);
      }          
    }
    
    chain.doFilter(request, response);
  }

  public void init(FilterConfig config) {
    try {
      InputStream is = getClass().getResourceAsStream("/cleansession.txt");
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
      e.printStackTrace();
      config.getServletContext().log("CleanSessionFilter.init", e);
    }

  }

  public void destroy() {
    //do nothing    
  }

}
