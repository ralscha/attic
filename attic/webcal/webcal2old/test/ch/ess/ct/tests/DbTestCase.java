package ch.ess.ct.tests;

import junit.framework.*;
import ch.ess.cal.resource.*;

public class DbTestCase extends TestCase {

  private DatabaseChecker dbChecker;
  private String dbDriver;
  private String dbUrl;
  private String dbUser;
  private String dbType;      
  private String dbPassword;

  public DbTestCase(String arg0) {
    super(arg0);
    dbChecker = null;
  }

  protected DatabaseChecker getDatabaseChecker() {
    return dbChecker;
  }

  protected void setUp() throws Exception {
    super.setUp();
    
    dbDriver = System.getProperty("test.db.driver");
    dbUrl = System.getProperty("test.db.url");
    dbUser = System.getProperty("test.db.user");
    dbType = System.getProperty("test.db.type");      
    dbPassword = System.getProperty("test.db.password");
       
    dbChecker = DatabaseChecker.createDatabaseChecker(dbDriver, dbUrl, dbUser, dbPassword); 
    
    
    HibernateManager.init();           
  }

  protected void tearDown() throws Exception {
    if (dbChecker != null) {
      dbChecker.closeConnection();
    }
    super.tearDown();
  }
  
  public String getDbDriver() {
    return dbDriver;
  }

  public String getDbPassword() {
    return dbPassword;
  }

  public String getDbType() {
    return dbType;
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public String getDbUser() {
    return dbUser;
  }
  
  protected void sleep(long sec) {
    try { 
      Thread.sleep(sec * 1000);
    } catch (InterruptedException ie) {
    }    
  }  

}
