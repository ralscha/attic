package ch.ess.base.hibernate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

public class AppLocalSessionFactoryBean extends AnnotationSessionFactoryBean {

  private String defaultDriver;
  private Map<String, String> drivers;

  public void setDefaultDriver(final String defaultDriver) {
    this.defaultDriver = defaultDriver;
  }

  public void setDrivers(final Map<String, String> drivers) {
    this.drivers = drivers;
  }

  @Override
  protected void postProcessAnnotationConfiguration(AnnotationConfiguration config) throws HibernateException {
    try {

      Properties properties = config.getProperties();

      if (properties.get(Environment.DIALECT) == null) {
        ConnectionProvider connectionProvider = ConnectionProviderFactory.newConnectionProvider(properties);
        String dialect = getDialect(connectionProvider);
        if (dialect != null) {
          properties.put(Environment.DIALECT, dialect);
        }        
      }

    } catch (SQLException e) {
      throw new HibernateException(e);
    }
  }

  private String getDialect(final ConnectionProvider connectionProvider) throws SQLException, HibernateException {
    String dbDialect = null;

    Connection conn = connectionProvider.getConnection();
    if (conn != null) {
      try {

        DatabaseMetaData dmd = conn.getMetaData();
        String theVendor = dmd.getDatabaseProductName();

        if (theVendor == null) {
          theVendor = dmd.getDriverName();
        }

        if (theVendor != null) {

          theVendor = theVendor.toLowerCase();

          for (String key : drivers.keySet()) {

            if (theVendor.indexOf(key.toLowerCase()) != -1) {
              return drivers.get(key);
            }

          }

        }

        return defaultDriver;

      } finally {
        conn.close();
        connectionProvider.close();
      }
    }

    return dbDialect;
  }

}
