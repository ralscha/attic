package ch.ess.base.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author sr
 */
public class DbPropertiesFactoryBean implements FactoryBean, InitializingBean {

  private DataSource dataSource;
  private Properties properties;
  private String query;
  private Map<String, String> defaultValues;

  public void setDataSource(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void setQuery(final String query) {
    this.query = query;
  }

  public void setDefaultValues(final Map<String, String> defaultValues) {
    this.defaultValues = defaultValues;
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
                LogFactory.getLog(getClass()).error("afterPropertiesSet", e);
              }
            }

            if (stmt != null) {
              try {
                stmt.close();
              } catch (SQLException e) {
                LogFactory.getLog(getClass()).error("afterPropertiesSet", e);
              }
            }

            try {
              conn.close();
            } catch (SQLException e) {
              LogFactory.getLog(getClass()).error("afterPropertiesSet", e);
            }

          }
        }
      }

    } catch (SQLException e) {
      LogFactory.getLog(getClass()).info("afterPropertiesSet: " + e.toString());
    }

    for (Iterator<String> it = defaultValues.keySet().iterator(); it.hasNext();) {
      String key = it.next();

      if (properties.get(key) == null) {
        properties.put(key, defaultValues.get(key));
      }
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