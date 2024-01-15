
package ch.ess.common.util;

import javax.servlet.ServletContext;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;


public class ServletContextLogAppender extends AppenderSkeleton {

  private  static ServletContext servletContext;
  
  public static ServletContext getServletContext() {
    return servletContext;
  }

  public static void setServletContext(ServletContext sc) {
    servletContext = sc;
  }

  protected void append(final LoggingEvent event) {

    // Output log message
    String s = layout.format(event);
    ThrowableInformation ti = event.getThrowableInformation();
    if (ti == null) {
      servletContext.log(s);
    } else {
      servletContext.log(s);
      
      String[] tsr = ti.getThrowableStrRep();

      if (tsr != null) {
        int len = tsr.length;

        for (int i = 0; i < len; i++) {
          servletContext.log(tsr[i]);
        }
      }            
    }
    
    
    
  }

  public boolean requiresLayout() {
    return true;
  }

  public void close() {
    //no action
  }
}