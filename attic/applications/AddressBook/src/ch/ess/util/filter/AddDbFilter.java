/*
 * $Header: c:/cvs/pbroker/src/ch/ess/pbroker/filter/AddDbFilter.java,v 1.1.1.1 2002/02/26 06:46:54 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:54 $
 */

package ch.ess.util.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import ch.ess.util.pool.*;
import com.objectmatter.bsf.*;
import ch.ess.addressbook.*;

/**
 * Class AddDbFilter
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:54 $
 */
public class AddDbFilter implements Filter {

  private FilterConfig config = null;

  public void init(FilterConfig config) {
    this.config = config;
  }

  public void destroy() {
    config = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    if (request.getAttribute(Constants.DB_KEY) == null) {
      Database db = PoolManager.requestDatabase();

      try {
        request.setAttribute(Constants.DB_KEY, db);
        chain.doFilter(request, response);
      } finally {
        PoolManager.returnDatabase(db);
      }
    }
  }
}
