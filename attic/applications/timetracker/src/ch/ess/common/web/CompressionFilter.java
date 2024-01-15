package ch.ess.common.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

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
 * Filter that compresses output with gzip
 *  (assuming that browser supports gzip).
 * <p>Taken from More Servlets and JavaServer Pages
 * from Prentice Hall and Sun Microsystems Press,
 * http://www.moreservlets.com/.
 * &copy; 2002 Marty Hall; may be freely used or adapted.</p>
 *
 * @author  Matt Raible 
 *
 * @web.filter
 *     name="compressionFilter"
 *
 * @web.filter-mapping
 *     url-pattern="*.do"
 */
public final class CompressionFilter implements Filter {
  //~ Instance fields ========================================================

  private Log log = LogFactory.getLog(CompressionFilter.class);

  //~ Methods ================================================================

  /**
   *  If browser does not support gzip, invoke resource
   *  normally. If browser <em>does</em> support gzip,
   *  set the Content-Encoding response header and
   *  invoke resource with a wrapped response that
   *  collects all the output. Extract the output
   *  and write it into a gzipped byte array. Finally,
   *  write that array to the client's output stream.
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;

    String userAgent = req.getHeader("User-Agent");

    if (!isGzipSupported(req) || userAgent.startsWith("httpunit")) {
      // Invoke resource normally.
      if (log.isDebugEnabled()) {
        log.debug("browser doesn't support gzip encoding: " + req.getHeader("User-Agent"));
      }

      chain.doFilter(req, res);
    } else {
      // Tell browser we are sending it gzipped data.
      res.setHeader("Content-Encoding", "gzip");

      // Invoke resource, accumulating output in the wrapper.
      CharArrayWrapper responseWrapper = new CharArrayWrapper(res);
      chain.doFilter(req, responseWrapper);

      // Get character array representing output.
      char[] responseChars = responseWrapper.toCharArray();

      if (log.isDebugEnabled()) {
        log.debug("Unzipped size: " + responseChars.length);
      }

      // Make a writer that compresses data and puts
      // it into a byte array.
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      GZIPOutputStream zipOut = new GZIPOutputStream(byteStream);
      OutputStreamWriter tempOut = new OutputStreamWriter(zipOut);

      // Compress original output and put it into byte array.
      tempOut.write(responseChars);

      // Gzip streams must be explicitly closed.
      tempOut.close();

      // Update the Content-Length header.
      if (log.isDebugEnabled()) {
        log.debug("Zipped size: " + byteStream.size());
      }

      res.setContentLength(byteStream.size());

      // Send compressed result to client.
      OutputStream realOut = res.getOutputStream();
      byteStream.writeTo(realOut);
    }
  }

  /**
   * Initialize controller values of filter.
   **/
  public void init(FilterConfig filterConfig) {
    //no action
  }

  /** destroy any instance values other than filterConfig **/
  public void destroy() {
    //no action
  }

  private boolean isGzipSupported(HttpServletRequest req) {
    String browserEncodings = req.getHeader("Accept-Encoding");

    return ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
  }
}
