package ch.ess.cal.web.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.LogFactory;

/**
 * Wraps Response for GZipFilter
 * 
 * @author Matt Raible
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:10 $
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {

  protected HttpServletResponse origResponse = null;
  protected ServletOutputStream stream = null;
  protected PrintWriter writer = null;
  protected int error = 0;

  public GZIPResponseWrapper(final HttpServletResponse response) {
    super(response);
    origResponse = response;
  }

  public ServletOutputStream createOutputStream() throws IOException {
    return (new GZIPResponseStream(origResponse));
  }

  public void finishResponse() {
    try {
      if (writer != null) {
        writer.close();
      } else {
        if (stream != null) {
          stream.close();
        }
      }
    } catch (IOException e) {
      LogFactory.getLog(getClass()).info("finishResponse", e);
    }
  }

  @Override
  public void flushBuffer() throws IOException {
    stream.flush();
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (writer != null) {
      throw new IllegalStateException("getWriter() has already been called!");
    }

    if (stream == null) {
      stream = createOutputStream();
    }

    return (stream);
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    // If access denied, don't create new stream or write because
    // it causes the web.xml's 403 page to not render
    if (this.error == HttpServletResponse.SC_FORBIDDEN) {
      return super.getWriter();
    }

    if (writer != null) {
      return (writer);
    }

    if (stream != null) {
      throw new IllegalStateException("getOutputStream() has already been called!");
    }

    stream = createOutputStream();
    writer = new PrintWriter(new OutputStreamWriter(stream, origResponse.getCharacterEncoding()));

    return (writer);
  }

  @Override
  public void setContentLength(final int length) {
    //no action here
  }

  /**
   * @see javax.servlet.http.HttpServletResponse#sendError(int,
   *      java.lang.String)
   */
  @Override
  public void sendError(final int err, final String message) throws IOException {
    super.sendError(err, message);
    this.error = err;
  }
}