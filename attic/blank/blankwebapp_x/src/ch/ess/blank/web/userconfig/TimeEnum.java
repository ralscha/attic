package ch.ess.blank.web.userconfig;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public final class TimeEnum extends ValuedEnum {

  public static final TimeEnum MINUTE = new TimeEnum("time.minute", 1);
  public static final TimeEnum HOUR = new TimeEnum("time.hour", 2);
  public static final TimeEnum DAY = new TimeEnum("time.day", 3);
  public static final TimeEnum WEEK = new TimeEnum("time.week", 4);
  public static final TimeEnum MONTH = new TimeEnum("time.month", 5);
  public static final TimeEnum YEAR = new TimeEnum("time.year", 6);

  private TimeEnum(String attributeName, int value) {
    super(attributeName, value);
  }

  public static TimeEnum getEnum(String attributName) {
    return (TimeEnum)getEnum(TimeEnum.class, attributName);
  }

  public static TimeEnum getEnum(int value) {
    return (TimeEnum)getEnum(TimeEnum.class, value);
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