/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/pool/PoolManager.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.pool;

import java.util.*;
import java.io.*;
import java.text.*;
import com.objectmatter.bsf.*;


/**
 * Class PoolManager
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public final class PoolManager {

  private DatabasePool pool;
  private static PoolManager instance;

  private PoolManager() {
  }

  public static Database requestDatabase() {
    return instance.pool.requestDatabase();
  }

  public static void returnDatabase(Database db) {
    instance.pool.returnDatabase(db);
  }

  public static void initPool(String schema) {

    synchronized (PoolManager.class) {
      instance = new PoolManager();
      instance.pool = new DatabasePool(schema);
    }
  }

  public static void initPool(String schema, String dbURL, String user, String password) {

    synchronized (PoolManager.class) {
      instance = new PoolManager();
      instance.pool = new DatabasePool(schema, dbURL, user, password);
    }
  }

  public static DatabasePool getPool() {
    return instance.pool;
  }
}
