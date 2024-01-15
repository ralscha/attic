package ch.ess.addressbook.resource;

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

import org.apache.log4j.servlet.ServletContextLogAppender;

import ch.ess.addressbook.Constants;


/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.7 $ $Date: 2003/11/11 18:59:57 $ 
  * 
  * @web.listener 
  */
public class Log4jContextListener implements ServletContextListener {

  public void contextDestroyed(ServletContextEvent sce) {
    InitLog.shutdown();
    ServletContextLogAppender.setServletContexts(null);    

  }

  public void contextInitialized(ServletContextEvent sce) {

    ServletContext context = sce.getServletContext();
    ServletContextLogAppender.setServletContexts(context);    

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


    try {
      InitLog.init(fileName, substMap);
      InitLog.configure();
    } catch (IOException e) {
      context.log(e.getMessage(), e);
      return;
    }
    
  }

  private void readConfig(ServletContext sce, Map substMap) throws NamingException, SQLException {
    Context ctx = new InitialContext();
    DataSource ds = (DataSource)ctx.lookup(Constants.DATASOURCE_NAME);
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
