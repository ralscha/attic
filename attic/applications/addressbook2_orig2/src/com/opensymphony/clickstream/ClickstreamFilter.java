package com.opensymphony.clickstream;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.clickstream.config.ConfigLoader;

/**
 * The filter that keeps track of a new entry in the clickstream for <b>every request</b>.
 *
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody</a>
 * @web.filter name="clickstreamFilter"
 * @web.filter-mapping url-pattern="*.do" 
 */
public class ClickstreamFilter implements Filter {
  
  private final static String FILTER_APPLIED = "_clickstream_filter_applied";
  private boolean doLog = false;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    
    if (doLog) {
      // Ensure that filter is only applied once per request.
      if (request.getAttribute(FILTER_APPLIED) == null) {      
        request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
  
        HttpSession session = ((HttpServletRequest)request).getSession();
        Clickstream stream = (Clickstream)session.getAttribute("clickstream");
  
        if (stream != null) {
        stream.addRequest((HttpServletRequest)request);
      }
    }
    }
    
    // pass the request on
    chain.doFilter(request, response);
  }

  public void init(FilterConfig cfg) throws ServletException {
    doLog = (ConfigLoader.getInstance().getConfig().getLogSize() != 0);

  }

  public void destroy() {
    //no action
  }
}
