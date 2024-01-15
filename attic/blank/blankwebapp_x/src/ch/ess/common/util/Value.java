package ch.ess.common.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
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
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}