package ch.ess.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author sr
 */
public class DbPropertiesFactoryBean implements FactoryBean, InitializingBean {

  private final Log logger = LogFactory.getLog(getClass());

  private DataSource dataSource;
  private Properties properties;
  private String query;
  
  
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public void setQuery(String query) {
    this.query = query;
  }

  public void afterPropertiesSet() {

    properties = new Properties();
    
    try {

      if (dataSource != null) {
        Connection conn = dataSource.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
          try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
              String name = rs.getString(1);
              String value = rs.getString(2);

              properties.put(name, value);
            }

          } finally {
            if (rs != null) {
              try {
                rs.close();
              } catch (SQLException e) {
                logger.error("afterPropertiesSet", e);
              }
            }

            if (stmt != null) {
              try {
                stmt.close();
              } catch (SQLException e) {
                logger.error("afterPropertiesSet", e);
              }
            }

            try {
              conn.close();
            } catch (SQLException e) {
              logger.error("afterPropertiesSet", e);
            }

          }
        }
      }

    } catch (SQLException e) {
      logger.info("afterPropertiesSet: " + e.toString());
    }

    if (properties.get("mail.smtppassword") == null) {
      properties.put("mail.smtppassword", "");
    }
    if (properties.get("mail.smtpuser") == null) {
      properties.put("mail.smtpuser", "");
    }
    if (properties.get("mail.smtpport") == null) {
      properties.put("mail.smtpport", "25");
    }
    if (properties.get("mail.smtphost") == null) {
      properties.put("mail.smtphost", "mail.ess.ch");
    }
    if (properties.get("mail.sender") == null) {
      properties.put("mail.sender", "sr@ess.ch");
    }

  }

  public Object getObject() {
    return this.properties;
  }

  public Class getObjectType() {
    return Properties.class;
  }

  public boolean isSingleton() {
    return true;
  }

}