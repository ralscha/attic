package ch.ess.addressbook.web.userconfig;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enum.Enum;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public final class TimeEnum extends Enum {

  public static final TimeEnum MINUTE = new TimeEnum("minute");
  public static final TimeEnum HOUR = new TimeEnum("hour");
  public static final TimeEnum DAY = new TimeEnum("day");
  public static final TimeEnum WEEK = new TimeEnum("week");
  public static final TimeEnum MONTH = new TimeEnum("month");
  public static final TimeEnum YEAR = new TimeEnum("year");

  private TimeEnum(String attributeName) {
    super(attributeName);
  }

  public static TimeEnum getEnum(String attributName) {
    return (TimeEnum)getEnum(TimeEnum.class, attributName);
  }

  public static Map getEnumMap() {
    return getEnumMap(TimeEnum.class);
  }

  public static List getEnumList() {
    return getEnumList(TimeEnum.class);
  }

  public static Iterator iterator() {
    return iterator(TimeEnum.class);
  }
}
