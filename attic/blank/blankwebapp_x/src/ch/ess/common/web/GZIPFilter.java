package ch.ess.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Filter that compresses output with gzip (assuming that browser supports gzip).
 * Code from <a href="http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html">
 * http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html</a>.
 *
 * &copy; 2003 Jayson Falkner You may freely use the code both commercially
 * and non-commercially.
 *
 * @author  Matt Raible
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 *
 * @web.filter
 *     name="compressionFilter"
 *
 * @web.filter-mapping
 *     url-pattern="*.do"
 */
public class GZIPFilter implements Filter {
  private static Log log = LogFactory.getLog(GZIPFilter.class);

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    if (req instanceof HttpServletRequest) {
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;

      if (isGZIPSupported(request)) {
        if (log.isDebugEnabled()) {
          log.debug("GZIP supported, compressing response");
        }

        GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);

        chain.doFilter(req, wrappedResponse);
        wrappedResponse.finishResponse();

        return;
      }

      chain.doFilter(req, res);
    }
  }

  /**
   * Convenience method to test for GZIP cababilities
   * 
   * @param req
   *          The current user request
   * @return boolean indicating GZIP support
   */
  private boolean isGZIPSupported(HttpServletRequest req) {
    String browserEncodings = req.getHeader("accept-encoding");
    boolean supported = ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
    return supported;

    //    String userAgent = req.getHeader("user-agent");

    //    if (userAgent.startsWith("httpunit")) {
    //      if (log.isDebugEnabled()) {
    //        log.debug("httpunit detected, disabling filter...");
    //      }
    //
    //      return false;
    //    } else {
    //      return supported;
    //    }
  }

  public void init(FilterConfig filterConfig) {
    //no action here
  }

  public void destroy() {
    //no action here
  }
}