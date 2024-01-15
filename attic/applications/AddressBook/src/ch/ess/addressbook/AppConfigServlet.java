/*
 * $Header: c:/cvs/pbroker/src/ch/ess/pbroker/resource/AppConfigServlet.java,v 1.4 2002/03/14 14:02:21 sr Exp $
 * $Revision: 1.4 $
 * $Date: 2002/03/14 14:02:21 $
 */

package ch.ess.addressbook;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import com.objectmatter.bsf.*;
import ch.ess.util.pool.*;

/**
 * Class AppConfigServlet
 *
 * @version $Revision: 1.4 $ $Date: 2002/03/14 14:02:21 $
 */
public class AppConfigServlet extends HttpServlet {

  public void destroy() {
    super.destroy();
  }

  public void init() throws ServletException {

    Locale.setDefault(new Locale("de", "CH"));

    //Debug.setDebugging(Debugger.DATABASE+Debugger.PERSISTENCE+Debugger.DEEP, false, false); 
    String schema = getServletConfig().getInitParameter("schema");
    String dbURL = getServletConfig().getInitParameter("dbURL");
    String dbUser = getServletConfig().getInitParameter("dbUser");
    String dbPassword = getServletConfig().getInitParameter("dbPassword");

    //Init DB    
    PoolManager.initPool(schema, dbURL, dbUser, dbPassword);

  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, ServletException {
    response.sendError(HttpServletResponse.SC_NO_CONTENT);
  }
}
