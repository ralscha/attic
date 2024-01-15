package ch.ess.addressbook.web.userconfig;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enum.Enum;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.5 $ $Date: 2003/11/11 19:06:51 $ 
  */
public final class TimeEnum extends Enum {

  public static final TimeEnum MINUTE = new TimeEnum("time.minute");
  public static final TimeEnum HOUR = new TimeEnum("time.hour");
  public static final TimeEnum DAY = new TimeEnum("time.day");
  public static final TimeEnum WEEK = new TimeEnum("time.week");
  public static final TimeEnum MONTH = new TimeEnum("time.month");
  public static final TimeEnum YEAR = new TimeEnum("time.year");

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
