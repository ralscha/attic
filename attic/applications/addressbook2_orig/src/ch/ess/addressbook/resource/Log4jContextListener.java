package ch.ess.addressbook.resource;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 23.07.2003 
  * 
  * @web.listener 
  */
public class Log4jContextListener implements ServletContextListener {

  public void contextDestroyed(ServletContextEvent sce) {
    InitLog.shutdown();
    //sce.getServletContext().log("context destroyed");    
  }

  public void contextInitialized(ServletContextEvent sce) {

    ServletContext context = sce.getServletContext();

    Map substMap = new HashMap();

    String fileName = context.getInitParameter("log4j-config");

    try {
      readConfig(context, substMap);
    } catch (NamingException e) {
      context.log(e.getMessage(), e);
      return;      
    } catch (SQLException e) {
      context.log(e.getMessage(), e);      
      return;
    }

    File tmpDir = (File)context.getAttribute("javax.servlet.context.tempdir");
    File tmpFile = new File(tmpDir, "ab.log");
    substMap.put("${FILENAME}", tmpFile.getPath());

    try {
      InitLog.init(fileName, substMap);
      InitLog.configure();
    } catch (IOException e) {
      context.log(e.getMessage(), e);
      return;
    }
    
    
    //sce.getServletContext().log("context initialized");
  }

  private void readConfig(ServletContext sce, Map substMap) throws NamingException, SQLException {
    Context ctx = new InitialContext();
    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/addressbook");
    if (ds != null) {
      Connection conn = ds.getConnection();
      Statement stmt = null;
      ResultSet rs = null;

      if (conn != null) {
        try {

          stmt = conn.createStatement();
          rs = stmt.executeQuery("select name, propValue from abConfiguration");
          while (rs.next()) {
            String name = rs.getString(1);
            String value = rs.getString(2);

            if (AppConfig.MAIL_SMTPHOST.equals(name)) {
              substMap.put("${SMTP}", value);
            } else if (AppConfig.MAIL_SENDER.equals(name)) {
              substMap.put("${MAILFROM}", value);
            } else if (AppConfig.ERROR_MAIL_RECEIVER.equals(name)) {
              substMap.put("${MAILTO}", value);
            }
          }
        } catch (SQLException sqle) {
          sce.log("error reading abConfiguration: " + sqle.toString());    
          
          substMap.put("${SMTP}", "mail.ess.ch");
          substMap.put("${MAILFROM}", "sr@ess.ch");
          substMap.put("${MAILTO}", "sr@ess.ch");                    
          
        } finally {
          if (rs != null) {
            rs.close();
          }

          if (stmt != null) {
            stmt.close();
          }

          if (conn != null) {
            conn.close();
          }

        }
      }
    }

  }

}
