package com.managestar.recurrance;

import java.util.Comparator;

import com.ibm.icu.util.Calendar;

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

  public static void fastSet(
    Calendar c,
    String year,
    String month,
    String day,
    String hour,
    String minute,
    String second)
    throws Exception {
    c.set(
      Integer.parseInt(year),
      Integer.parseInt(month) - 1,
      Integer.parseInt(day),
      Integer.parseInt(hour),
      Integer.parseInt(minute),
      Integer.parseInt(second));
  }

  public static Comparator calComparator = new Comparator() {
    public int compare(Object o1, Object o2) {

      Calendar c1 = (Calendar)o1;
      Calendar c2 = (Calendar)o2;

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
