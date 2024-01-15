package ch.sr.lottowin;

import com.objectmatter.bsf.*;
import com.objectmatter.bsf.mapping.repository.*;
import com.objectmatter.bsf.mapping.schema.*;


/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.2 $ $Date: 2003/08/27 06:43:45 $
 * @author $Author: sr $
 */
public class DbPool {

  // default values
  private static final int MAX_POOLSIZE = 0; //Unlimited
  private static final int INIT_POOLSIZE = 5;
  private Server server;
  private static DbPool instance;


  private DbPool(String schema, String dbType, String dbDriver, String dbURL, String user, String password) {
    this(schema, dbType, dbDriver, dbURL, user, password, INIT_POOLSIZE, MAX_POOLSIZE);
  }

  private DbPool(String schema, String dbType, String dbDriver, String dbURL, String user, String password, 
                 int initSize, int maxSize) {

    try {
      server = getServer(schema, dbType, dbDriver, dbURL, user, password, initSize, maxSize);
    } catch (Exception e) {
      System.err.println("DB not ready");
      e.printStackTrace();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param schema DOCUMENT ME!
   * @param dbType DOCUMENT ME!
   * @param dbDriver DOCUMENT ME!
   * @param dbURL DOCUMENT ME!
   * @param userName DOCUMENT ME!
   * @param password DOCUMENT ME!
   * @param initNoDatabases DOCUMENT ME!
   * @param maxNoDatabases DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   * 
   * @throws Exception DOCUMENT ME!
   */
  private Server getServer(String schema, String dbType, String dbDriver, String dbURL, String userName, 
                           String password, int initNoDatabases, int maxNoDatabases)
                    throws Exception {

    Server.setProfessionalMode();
    
    //update URL, username & password
    if (dbURL != null) {

      //load schema directly from repository directory
      AppSchema sch = Repository.readAppSchema(schema);


      DBConfiguration dbConfig = sch.getDBConfiguration(); //get the default DBconfig

      if (dbDriver != null) {
        dbConfig.setDBDriver(dbDriver);
      }

      if (dbType != null) {

        String[] types = DBConfiguration.DBTYPE_LOOKUP_VALUES;
        dbConfig.setDBType(DBConfiguration.AUTO_DETECT);

        for (int i = 0; i < types.length; i++) {

          if (dbType.equalsIgnoreCase(types[i])) {            
            dbConfig.setDBType(types[i]);

            break;
          }
        }
      }
      dbConfig.setDBURL(dbURL);
      dbConfig.setLoginName(userName);
      dbConfig.setLoginPassword(password);
      dbConfig.setInitNoConnections(initNoDatabases);
      dbConfig.setMaxNoConnections(maxNoDatabases);
      
      
      
      return new Server(schema, dbConfig);
    }

    return new Server(schema);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public static synchronized Database requestDatabase() {

    return instance.server.getDatabase();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param db Database Object
   */
  public static synchronized void returnDatabase(Database db) {
    db.close();
  }


  /**
   * DOCUMENT ME!
   * 
   * @param schema DOCUMENT ME!
   * @param dbType DOCUMENT ME!
   * @param dbDriver DOCUMENT ME!
   * @param dbURL DOCUMENT ME!
   * @param user DOCUMENT ME!
   * @param password DOCUMENT ME!
   */
  public static void initPool(String schema, String dbType, String dbDriver, String dbURL, String user, String password) {
    synchronized (DbPool.class) {
      instance = new DbPool(schema, dbType, dbDriver, dbURL, user, password);
    }
  }
}