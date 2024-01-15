package ch.ess.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 12:24:30 $
 */
public final class TimedValues {

  private static final int CLEANUP_SLEEP_TIME = 600; // 10 minutes
  private static final TimedValues INSTANCE = new TimedValues();

  private final Map valueMap = new HashMap();

  private TimedValues() {

    Thread cleanupThread = new Thread(new TimedValuesCleanupThread(CLEANUP_SLEEP_TIME));

    cleanupThread.setDaemon(true);
    cleanupThread.start();
  }

  public static synchronized void addValue(String key, Variant value, int expire) {
    INSTANCE.valueMap.put(key, new Value(key, value, expire));
  }

  public static synchronized void addValue(String key, int value, int expire) {
    addValue(key, new Variant(value), expire);
  }

  public static synchronized void removeValue(String key) {
    INSTANCE.valueMap.remove(key);
  }

  public static synchronized Variant getValue(String key) {

    Object o = INSTANCE.valueMap.get(key);

    if (o != null) {
      return ((Value) o).getValue();
    }
    return null;

  }

  public static synchronized List getValues() {

    ArrayList values = new ArrayList();

    for (Iterator it = INSTANCE.valueMap.values().iterator(); it.hasNext();) {
      values.add(it.next());
    }

    return values;
  }

  public static synchronized void cleanUp() {

    Iterator it = INSTANCE.valueMap.values().iterator();

    while (it.hasNext()) {
      Value val = (Value) it.next();

      if (val.hasExpired()) {
        it.remove();
      }
    }
  }

  //NUR FUER TESTZWECKE
  public static void setCleanupThreadTimeout(int timeoutInSec) {

    Thread cleanupThread = new Thread(new TimedValuesCleanupThread(timeoutInSec));

    cleanupThread.setDaemon(true);
    cleanupThread.start();
  }

  //NUR FUER TESTZWECKE
}