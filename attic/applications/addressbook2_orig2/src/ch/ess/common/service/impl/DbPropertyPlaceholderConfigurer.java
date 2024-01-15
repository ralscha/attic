package ch.ess.common.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.config.PropertyPlaceholderConfigurer;

import ch.ess.addressbook.Constants;
import ch.ess.addressbook.resource.AppConfig;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:10:07 $ 
  */

public class DbPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  private static final Log LOG = LogFactory.getLog(DbPropertyPlaceholderConfigurer.class);
  
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    
    Properties props = new Properties();
        
        
    
    try {
      Context ctx = new InitialContext();
      DataSource ds = (DataSource)ctx.lookup(Constants.DATASOURCE_NAME);

      if (ds != null) {
        Connection conn = ds.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
          try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select name, propValue from addressbookConfiguration");
            while (rs.next()) {
              String name = rs.getString(1);
              String value = rs.getString(2);

              props.put(name, value);                            
            }
            
            if (props.get(AppConfig.MAIL_SMTPPASSWORD) == null) {
              props.put(AppConfig.MAIL_SMTPPASSWORD, "");
            }
            if (props.get(AppConfig.MAIL_SMTPUSER) == null) {
              props.put(AppConfig.MAIL_SMTPUSER, "");
            }
            
            
          } catch (SQLException sqle) {
            LOG.error("error reading addressbookConfiguration", sqle);

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
    } catch (NamingException e) {
      LOG.error("initializeService", e);
    } catch (SQLException e) {
      LOG.error("initializeService", e);
    }

    
    processProperties(beanFactory, props);
  }

}