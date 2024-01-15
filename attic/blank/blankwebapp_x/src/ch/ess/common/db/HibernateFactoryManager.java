package ch.ess.common.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.cfg.Environment;
import net.sf.hibernate.tool.hbm2ddl.SchemaUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class HibernateFactoryManager {

  private static final Log LOG = LogFactory.getLog(HibernateFactoryManager.class);

  private static HibernateFactoryManager instance = new HibernateFactoryManager();
  private SessionFactory sf;

  private HibernateFactoryManager() {
    //do nothing
  }

  public static SessionFactory getSessionFactory() {
    return instance.sf;
  }

  public static void initXML(String xmlpath) throws HibernateException {
    Configuration cfg = new Configuration();
    cfg.configure(xmlpath);

    instance.sf = cfg.buildSessionFactory();

  }

  public static void init(String dialect) throws HibernateException {

    Configuration cfg = new Configuration();
    cfg.configure();

    if (dialect != null) {
      Properties prop = new Properties();
      prop.put(Environment.DIALECT, dialect);
      cfg.addProperties(prop);
    }

    instance.sf = cfg.buildSessionFactory();

    // update existing schema
    new SchemaUpdate(cfg).execute(false, true);
    //new SchemaExport(cfg).create(false, true);

  }

  public static void initLookForDialect(String jndiName) throws HibernateException {

    try {

      String dialect = getDialect(jndiName);
      init(dialect);

    } catch (NamingException e) {
      LOG.error("init", e);
      throw new HibernateException(e);
    } catch (SQLException e) {
      LOG.error("init", e);
      throw new HibernateException(e);
    }
  }

  public static void destroy() {
    if (null != instance.sf) {
      try {
        instance.sf.close();
      } catch (HibernateException e) {
        LOG.error("destroy", e);
      }
    }
    instance.sf = null;
  }

  private static String getDialect(String jndiName) throws NamingException, SQLException {
    String dbDialect = null;

    Context ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup(jndiName);
    if (ds != null) {
      Connection conn = ds.getConnection();
      if (conn != null) {
        try {

          DatabaseMetaData dmd = conn.getMetaData();
          String theVendor = dmd.getDatabaseProductName();

          if (theVendor == null) {
            theVendor = dmd.getDriverName();
          }

          //System.out.println(theVendor);

          if (theVendor != null) {
            if ("Microsoft SQL Server".equals(theVendor)) {
              dbDialect = "net.sf.hibernate.dialect.SQLServerDialect";
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
        }
      }
    }

    return dbDialect;
  }

}