package ch.ess.blankrc.persistence.hibernate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.cfg.Environment;
import net.sf.hibernate.connection.ConnectionProvider;
import net.sf.hibernate.connection.ConnectionProviderFactory;

import org.springframework.orm.hibernate.LocalSessionFactoryBean;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2004/06/06 05:24:10 $
 */
public class NamingLocalSessionFactoryBean extends LocalSessionFactoryBean {

  protected void postProcessConfiguration(Configuration config) throws HibernateException {
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

  private static String getDialect(ConnectionProvider connectionProvider) throws SQLException, HibernateException {
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
          if ("Microsoft SQL Server".equals(theVendor)) {
            dbDialect = "net.sf.hibernate.dialect.SQLServerDialect";
          } else if (theVendor.startsWith("Mckoi SQL Database")) {
            dbDialect = "net.sf.hibernate.dialect.MckoiDialect";
          } else if ("SAP DB".equals(theVendor)) {
            dbDialect = "net.sf.hibernate.dialect.SAPDBDialect";
          } else if ("MySQL".equals(theVendor)) {
            dbDialect = "net.sf.hibernate.dialect.MySQLDialect";
          } else if (theVendor.startsWith("DB2")) {
            dbDialect = "net.sf.hibernate.dialect.DB2Dialect";
          } else if ("Oracle".equals(theVendor)) {
            dbDialect = "net.sf.hibernate.dialect.OracleDialect";
          } else if (theVendor.toLowerCase().indexOf("mckoi") != -1) {
            dbDialect = "net.sf.hibernate.dialect.MckoiDialect";
          } else if (theVendor.toLowerCase().indexOf("pointbase") != -1) {
            dbDialect = "net.sf.hibernate.dialect.PointbaseDialect";
          } else if (theVendor.toLowerCase().indexOf("hsql") != -1) {
            dbDialect = "net.sf.hibernate.dialect.HSQLDialect";
          } else {
            dbDialect = "net.sf.hibernate.dialect.GenericDialect";
          }
        }

      } finally {
        conn.close();
        connectionProvider.close();
      }
    }

    return dbDialect;
  }

}