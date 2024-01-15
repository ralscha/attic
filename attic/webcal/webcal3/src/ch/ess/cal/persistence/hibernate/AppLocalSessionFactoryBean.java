package ch.ess.cal.persistence.hibernate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $
 */
public class AppLocalSessionFactoryBean extends LocalSessionFactoryBean {

  private Class[] annotatedClasses;
  private String[] annotatedPackages;
  private String existsQuery;
  private DataSource dataSource;
  private String defaultDriver;
  private Map<String, String> drivers;

  public void setExistsQuery(final String existsQuery) {
    this.existsQuery = existsQuery;
  }

  @Override
  public void setDataSource(final DataSource dataSource) {
    this.dataSource = dataSource;
    super.setDataSource(dataSource);
  }

  public void setDefaultDriver(final String defaultDriver) {
    this.defaultDriver = defaultDriver;
  }

  public void setDrivers(final Map<String, String> drivers) {
    this.drivers = drivers;
  }

  @Override
  public void updateDatabaseSchema() throws HibernateException {

    boolean dbExists = false;

    try {

      Connection conn = dataSource.getConnection();

      if (conn != null) {

        Statement stmt = null;
        ResultSet rs = null;

        try {
          stmt = conn.createStatement();
          rs = stmt.executeQuery(existsQuery);
          dbExists = true;
        } catch (SQLException sqle) {
          logger.info("no tables exists: " + sqle);
        } finally {
          if (rs != null) {
            try {
              rs.close();
            } catch (SQLException e) {
              logger.error("updateDatabaseSchema", e);
            }
          }

          if (stmt != null) {
            try {
              stmt.close();
            } catch (SQLException e) {
              logger.error("updateDatabaseSchema", e);
            }
          }

          try {
            conn.close();
          } catch (SQLException e) {
            logger.error("updateDatabaseSchema", e);
          }

        }

      }
    } catch (SQLException e) {
      logger.error("updateDatabaseSchema", e);
      throw new HibernateException(e);
    }

    if (dbExists) {
      super.updateDatabaseSchema();
    } else {
      createDatabaseSchema();
    }

  }

  public void setAnnotatedClasses(Class annotatedClasses[]) {
    this.annotatedClasses = annotatedClasses;
  }

  public void setAnnotatedPackages(String[] annotatedPackages) {
    this.annotatedPackages = annotatedPackages;
  }

  @Override
  protected Configuration newConfiguration() throws HibernateException {
    AnnotationConfiguration config = new AnnotationConfiguration();
    return config;
  }

  @Override
  protected void postProcessConfiguration(final Configuration config) throws HibernateException {
    try {

      Properties properties = config.getProperties();

      if (properties.get(Environment.DIALECT) == null) {
        ConnectionProvider connectionProvider = ConnectionProviderFactory.newConnectionProvider(properties);
        String dialect = getDialect(connectionProvider);
        if (dialect != null) {
          properties.put(Environment.DIALECT, dialect);
        }
      }

      super.postProcessConfiguration(config);

      if (this.annotatedPackages != null) {
        for (int i = 0; i < this.annotatedPackages.length; i++) {
          ((AnnotationConfiguration)config).addPackage(annotatedPackages[i]);
        }
      }
      
      if (this.annotatedClasses != null) {
        for (Class annClass : this.annotatedClasses) {
          ((AnnotationConfiguration)config).addAnnotatedClass(annClass);  
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
