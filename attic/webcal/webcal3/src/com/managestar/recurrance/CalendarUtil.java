package com.managestar.recurrance;

import java.util.Calendar;
import java.util.Comparator;

public class CalendarUtil {
  public static boolean isBeforeOrEqual(Calendar one, Calendar two) {
    return one.getTimeInMillis() <= two.getTimeInMillis();
  }

  public static boolean isAfterOrEqual(Calendar one, Calendar two) {
    return one.getTimeInMillis() >= two.getTimeInMillis();
  }

  public static boolean isEqual(Calendar one, Calendar two) {
    return one.getTimeInMillis() == two.getTimeInMillis();
  }

  public static void fastSet(Calendar c, String year, String month, String day, String hour, String minute,
      String second) throws Exception {
    c.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour), Integer
        .parseInt(minute), Integer.parseInt(second));
  }

  public static Comparator<Calendar> calComparator = new Comparator<Calendar>() {
    public int compare(Calendar c1, Calendar c2) {

      if (c1.before(c2)) {
        return -1;
      } else if (CalendarUtil.isEqual(c1, c2)) {
        return 0;
      } else {
        return 1;
      }
    }
  };

}
