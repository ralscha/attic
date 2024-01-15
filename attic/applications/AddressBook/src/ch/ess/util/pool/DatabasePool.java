/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/pool/DatabasePool.java,v 1.2 2002/04/02 18:27:21 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/04/02 18:27:21 $
 */

package ch.ess.util.pool;

import java.util.*;
import java.io.Serializable;
import com.objectmatter.bsf.*;
import com.objectmatter.bsf.mapping.schema.*;
import com.objectmatter.bsf.mapping.repository.Repository;
import org.apache.log4j.*;


/**
 * Class DatabasePool
 *
 * @version $Revision: 1.2 $ $Date: 2002/04/02 18:27:21 $
 */
public class DatabasePool implements Serializable {

  private static final transient Logger LOG = Logger.getLogger(DatabasePool.class.getName());
  private long objectTimeout;    // time SmartObjects have before being returned
  private Hashtable locked;    // objects checked out
  private Thread lifeguard;    // thread to protect objects from hazard

  // default values
  public static final int MAX_POOLSIZE = 0;    //Unlimited
  public static final int INIT_POOLSIZE = 5;
  public static final long DEFAULT_TIMEOUT = 43200;    // 12 hours
  private Server server;

  public DatabasePool(String schema) {
    this(schema, null, null, null, INIT_POOLSIZE, MAX_POOLSIZE, DEFAULT_TIMEOUT);
  }

  public DatabasePool(String schema, String dbURL, String user, String password) {
    this(schema, dbURL, user, password, INIT_POOLSIZE, MAX_POOLSIZE, DEFAULT_TIMEOUT);
  }

  public DatabasePool(String schema, String dbURL, String user, String password, int initSize, int maxSize, long timeoutSecs) {

    try {
      server = getServer(schema, dbURL, user, password, initSize, maxSize);
    } catch (Exception e) {
      LOG.error("getServer", e);
    }

    this.objectTimeout = timeoutSecs * 1000;
    this.locked = new Hashtable(1);

    // lifeguard
    this.lifeguard = new Thread(new LifeGuardThread(3600 /*1 Stunde */, this));

    lifeguard.setDaemon(true);
    lifeguard.start();
  }

  private Server getServer(String schema, String dbURL, String userName, String password, int initNoDatabases, int maxNoDatabases)
          throws Exception {

    Server svr = new Server();

    //load schema directly from repository directory
    AppSchema sch = (AppSchema)Repository.read(schema);

    //update URL, username & password
    if (dbURL != null) {
      DBConfiguration dbConfig = sch.getDBConfiguration();    //get the default DBconfig

      dbConfig.setDBURL(dbURL);
      dbConfig.setLoginName(userName);
      dbConfig.setLoginPassword(password);
    }

    //assign updated schema to this server
    //connection pool connected to dbURL using userName & password is created
    svr.setAppSchema(sch, initNoDatabases, maxNoDatabases, 1000, 5000);

    return svr;
  }

  /** @return int The number of objects currently available. */
  public synchronized int numCheckedOutObjects() {
    return locked.size();
  }

  public synchronized Database requestDatabase() {

    long now = System.currentTimeMillis();
    Database db = server.getDatabase();

    locked.put(db, new Long(now));

    return db;
  }

  public synchronized void returnDatabase(Database db) {

    locked.remove(db);
    db.discardAll();
    db.close();
  }

  public synchronized List getLockedCopy() {

    ArrayList lockedObjects = new ArrayList();

    for (Enumeration enum = locked.keys(); enum.hasMoreElements(); ) {
      lockedObjects.add(locked.get(enum.nextElement()));
    }

    return lockedObjects;
  }

  protected synchronized void checkTimeout() {

    long now = System.currentTimeMillis();

    // copy into temp list to avoid enum shared struct problem
    ArrayList lockedObjects = new ArrayList();

    for (Enumeration enum = locked.keys(); enum.hasMoreElements(); ) {
      lockedObjects.add(enum.nextElement());
    }

    for (int i = 0; i < lockedObjects.size(); i++) {
      Object o = lockedObjects.get(i);
      long lasttouch = ((Long)locked.get(o)).longValue();

      // User loses the Object, but it is still alive (gets cleaned on checkIn)
      if ((now - lasttouch) > this.objectTimeout) {
        Database db = (Database)o;

        returnDatabase(db);
      }
    }
  }
}
