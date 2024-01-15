package ch.ess.cal.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter that compresses output with gzip (assuming that browser supports gzip).
 * Code from <a href="http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html">
 * http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html</a>.
 *
 * &copy; 2003 Jayson Falkner You may freely use the code both commercially
 * and non-commercially.
 *
 * @author  Matt Raible
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:10 $
 *
 * @web.filter name="compressionFilter"
 */
public class GZIPFilter implements Filter {

  public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
      throws IOException, ServletException {
    if (req instanceof HttpServletRequest) {
      HttpServletRequest request = (HttpServletRequest)req;
      HttpServletResponse response = (HttpServletResponse)res;

      if (isGZIPSupported(request)) {

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
  private boolean isGZIPSupported(final HttpServletRequest req) {
    String browserEncodings = req.getHeader("accept-encoding");
    boolean supported = ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
    return supported;
  }

  public void init(final FilterConfig filterConfig) {
    //no action here
  }

  public void destroy() {
    //no action here
  }
}