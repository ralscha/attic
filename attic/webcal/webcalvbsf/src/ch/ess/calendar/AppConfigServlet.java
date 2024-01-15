package ch.ess.calendar;


import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import ch.ess.util.pool.*;

import com.objectmatter.bsf.*;

public class AppConfigServlet extends HttpServlet {

  public void destroy() {
    super.destroy();
  }

  public void init() throws ServletException {

    //Locale.setDefault(new Locale("de", "CH"));

    //Debug.setDebugging(Debugger.DATABASE+Debugger.PERSISTENCE+Debugger.DEEP, false, false); 

    String schema = getServletConfig().getInitParameter("schema");
    String dbURL = getServletConfig().getInitParameter("dbURL");
    String dbUser = getServletConfig().getInitParameter("dbUser");
    String dbPassword = getServletConfig().getInitParameter("dbPassword");    

    //Init DB
    PoolManager.initPool(schema, dbURL, dbUser, dbPassword);        
    /*
    //redirect System.out and System.err to a file
    try {
      FileOutputStream err = new FileOutputStream("stderr.log");
      PrintStream errPrintStream = new PrintStream(err);
      System.setErr(errPrintStream);

      FileOutputStream out = new FileOutputStream("stdout.log");
      PrintStream outPrintStream = new PrintStream(out);
      System.setOut(outPrintStream);
    } catch (FileNotFoundException fnfe) {
      System.err.println(fnfe);
    }
*/
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws java.io.IOException, ServletException {
    response.sendError(HttpServletResponse.SC_NO_CONTENT);
  }
}
