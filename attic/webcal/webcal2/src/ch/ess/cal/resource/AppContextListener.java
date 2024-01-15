package ch.ess.cal.resource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.servlet.ServletContextLogAppender;
import org.apache.velocity.app.Velocity;
import org.springframework.web.context.ContextLoader;

import ch.ess.cal.Constants;
import ch.ess.cal.resource.text.RuleSet;
import ch.ess.cal.resource.text.TextResources;
import ch.ess.common.db.HibernateFactoryManager;


/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $ 
 * 
 * @web.listener 
 */
public class AppContextListener implements ServletContextListener {

  private ContextLoader contextLoader;
  
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext ctx = sce.getServletContext();
    try {
      HibernateFactoryManager.initLookForDialect(Constants.DATASOURCE_NAME);
      AppConfig.init();

      InitialDataLoad.load(ctx);

      
      //Log
      ServletContextLogAppender.setServletContexts(ctx);          
      Map substMap = new HashMap();
      String fileName = ctx.getInitParameter("log4j-config");

      try {
        readConfig(ctx, substMap);
      } catch (NamingException e) {
        ctx.log(e.getMessage(), e); 
      } catch (SQLException e) {
        ctx.log(e.getMessage(), e);      
      }

      try {
        InitLog.init(fileName, substMap);
        InitLog.configure();
      } catch (IOException e) {
        ctx.log(e.getMessage(), e);
      }      
      
      
      
      //Velocity
      initVelocity(ctx);

      //Holidays
      HolidayRegistry.init();      
      
      //Init Text Resources     
      initTextResources();
      InitialDataLoad.loadTextResources();
      
      //Spring
      contextLoader = new ContextLoader();
      contextLoader.initWebApplicationContext(ctx);
      
      
    } catch (Exception e) {
      ctx.log("init", e);
    }

  }

  public void contextDestroyed(ServletContextEvent event) {
    contextLoader.closeContext(event.getServletContext());
    HibernateFactoryManager.destroy();
    
    InitLog.shutdown();    
    ServletContextLogAppender.setServletContexts(null);  
  }

  
  private void initVelocity(ServletContext context) throws Exception {
    
    Velocity.setApplicationAttribute(ServletContext.class.getName(), context);
    
    InputStream is = getClass().getResourceAsStream("/velocity.properties");
    Properties props = new Properties();
    props.load(is);
    is.close();    
    Velocity.init(props);  
  }
  
  private void initTextResources() {
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/textresource.xml");
      if (is != null) {
        Digester digester = new Digester();

        digester.addRuleSet(new RuleSet());

        List l = (List)digester.parse(is);
        TextResources.addResource(l);        

      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
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
          rs = stmt.executeQuery("select name, propValue from calConfiguration");
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
          sce.log("error reading calConfiguration: " + sqle.toString());    
          
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
