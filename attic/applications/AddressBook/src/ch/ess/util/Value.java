/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/Value.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util;

import java.io.*;


/**
 * Class Value
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class Value {

  private long time;
  private Variant value;
  private long expire;
  private String key;

  public Value() {

    key = null;
    value = null;
    expire = 0;
    time = 0;
  }

  public Value(String key) {

    this.key = key;
    value = null;
    expire = 0;
    time = 0;
  }

  public Value(String key, Variant value, int expire) {

    this.key = key;
    this.value = value;
    this.expire = expire * 1000;
    this.time = System.currentTimeMillis();
  }

  public Variant getValue() {
    return value;
  }

  public String getKey() {
    return key;
  }

  boolean hasExpired() {

    if (expire != 0) {
      if ((System.currentTimeMillis() - time) > expire) {
        return true;
      }
    }

    return false;
  }

  public String toString() {
    return "\nkey = " + key + "\n" + "value = " + value + "\n" + "expire = " + expire + "\n\n";
  }
}
