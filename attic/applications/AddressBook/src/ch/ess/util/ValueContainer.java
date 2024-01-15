/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/ValueContainer.java,v 1.2 2002/04/02 18:27:21 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/04/02 18:27:21 $
 */

package ch.ess.util;

import java.util.*;
import java.io.*;
import java.text.*;


/**
 * Class ValueContainer
 *
 * @version $Revision: 1.2 $ $Date: 2002/04/02 18:27:21 $
 */
public final class ValueContainer {

  private static final ValueContainer instance = new ValueContainer();
  private static final Map valueMap = new HashMap();
  private static final int CLEANUP_SLEEP_TIME = 600;    // 10 Minuten

  private ValueContainer() {

    Thread cleanupThread = new Thread(new ValueContainerCleanupThread(CLEANUP_SLEEP_TIME));

    cleanupThread.setDaemon(true);
    cleanupThread.start();
  }

  public static synchronized void addValue(String key, Variant value, int expire) {
    valueMap.put(key, new Value(key, value, expire));
  }

  public static synchronized void addValue(String key, int value, int expire) {
    addValue(key, new Variant(value), expire);
  }

  public static synchronized void removeValue(String key) {
    valueMap.remove(key);
  }

  public static synchronized Variant getValue(String key) {

    Object o = valueMap.get(key);

    if (o != null) {
      return ((Value)o).getValue();
    } else {
      return null;
    }
  }

  public static synchronized List getValues() {

    ArrayList values = new ArrayList();

    for (Iterator it = valueMap.values().iterator(); it.hasNext(); ) {
      values.add(it.next());
    }

    return values;
  }

  public static synchronized void cleanUp() {

    Iterator it = valueMap.values().iterator();

    while (it.hasNext()) {
      Value val = (Value)it.next();

      if (val.hasExpired()) {
        it.remove();
      }
    }
  }

  //NUR FUER TESTZWECKE
  public static void setCleanupThreadTimeout(int timeoutInSec) {

    Thread cleanupThread = new Thread(new ValueContainerCleanupThread(timeoutInSec));

    cleanupThread.setDaemon(true);
    cleanupThread.start();
  }

  //NUR FUER TESTZWECKE 
}
