package ch.ess.cal.event;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntList;

import ch.ess.cal.Constants;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.Recurrence;
import ch.ess.cal.db.RecurrenceTypeEnum;
import ch.ess.cal.db.User;
import ch.ess.cal.resource.UserConfig;
import ch.ess.common.util.Util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.managestar.recurrance.Recurrance;

public class EventUtil {

  public static Calendar getTodayCalendar(User user, UserConfig config) {
    Calendar cal = new GregorianCalendar(user.getTimeZoneObj());
    cal.setMinimalDaysInFirstWeek(config.getIntegerProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK, 4));
    cal.setFirstDayOfWeek(config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK).intValue());    
    return cal;
  }  
  
  public static Integer getDayOfWeekMask(int[] wd) {
    int result = 0;
    for (int i = 0; i < wd.length; i++) {
      result += getDayOfWeekMask(wd[i]).intValue();
    }
    return new Integer(result);
  }

  public static Integer getDayOfWeekMask(int wd) {
    switch (wd) {
      case Calendar.SUNDAY :
        return new Integer(1);
      case Calendar.MONDAY :
        return new Integer(2);
      case Calendar.TUESDAY :
        return new Integer(4);
      case Calendar.WEDNESDAY :
        return new Integer(8);
      case Calendar.THURSDAY :
        return new Integer(16);
      case Calendar.FRIDAY :
        return new Integer(32);
      case Calendar.SATURDAY :
        return new Integer(64);
    }
    return new Integer(0);
  }

  public static int[] getWeekdayArray(Integer maskInt) {
    int mask = maskInt.intValue();
    IntList list = new ArrayIntList();

    if ((mask & 1) == 1) {
      list.add(Calendar.SUNDAY);
    }
    if ((mask & 2) == 2) {
      list.add(Calendar.MONDAY);
    }
    if ((mask & 4) == 4) {
      list.add(Calendar.TUESDAY);
    }
    if ((mask & 8) == 8) {
      list.add(Calendar.WEDNESDAY);
    }
    if ((mask & 16) == 16) {
      list.add(Calendar.THURSDAY);
    }
    if ((mask & 32) == 32) {
      list.add(Calendar.FRIDAY);
    }
    if ((mask & 64) == 64) {
      list.add(Calendar.SATURDAY);
    }

    return list.toArray();

  }

  public static int getWeekday(Integer maskInt) {
    int mask = maskInt.intValue();
    switch (mask) {
      case 1 :
        return Calendar.SUNDAY;
      case 2 :
        return Calendar.MONDAY;
      case 4 :
        return Calendar.TUESDAY;
      case 8 :
        return Calendar.WEDNESDAY;
      case 16 :
        return Calendar.THURSDAY;
      case 32 :
        return Calendar.FRIDAY;
      case 64 :
        return Calendar.SATURDAY;
    }
    return 0;
  }

  public static String getWeekdayStr(Integer maskInt) {
    int mask = maskInt.intValue();
    switch (mask) {
      case 1 :
        return "SU";
      case 2 :
        return "MO";
      case 4 :
        return "TU";
      case 8 :
        return "WE";
      case 16 :
        return "TH";
      case 32 :
        return "FR";
      case 64 :
        return "SA";
    }
    return null;
  }

  public static String getWeekdayArrayString(Integer maskInt) {
    int mask = maskInt.intValue();

    String result = "";

    if ((mask & 1) == 1) {
      result += "SU";
    }
    if ((mask & 2) == 2) {
      if (result.length() > 0) {
        result += ",";
      }
      result += "MO";
    }
    if ((mask & 4) == 4) {
      if (result.length() > 0) {
        result += ",";
      }

      result += "TU";
    }
    if ((mask & 8) == 8) {
      if (result.length() > 0) {
        result += ",";
      }

      result += "WE";
    }
    if ((mask & 16) == 16) {
      if (result.length() > 0) {
        result += ",";
      }

      result += "TH";
    }
    if ((mask & 32) == 32) {
      if (result.length() > 0) {
        result += ",";
      }

      result += "FR";
    }
    if ((mask & 64) == 64) {
      if (result.length() > 0) {
        result += ",";
      }

      result += "SA";
    }

    return result;

  }

  public static String getRfcRule(Recurrence recur, int weekStart) {

    String rule = "";

    if (recur.getType().toInt() == RecurrenceTypeEnum.DAILY.toInt()) {
      rule = "FREQ=DAILY;INTERVAL=" + recur.getInterval();

    } else if (recur.getType().toInt() == RecurrenceTypeEnum.WEEKLY.toInt()) {
      rule = "FREQ=WEEKLY;INTERVAL=" + recur.getInterval();
      rule += ";BYDAY=" + getWeekdayArrayString(recur.getDayOfWeekMask());

    } else if (recur.getType().toInt() == RecurrenceTypeEnum.MONTHLY.toInt()) {
      rule = "FREQ=MONTHLY;INTERVAL=" + recur.getInterval();
      if (recur.getDayOfMonth().intValue() == 31) {
        rule += ";BYMONTHDAY=-1";
      } else {
        rule += ";BYMONTHDAY=" + recur.getDayOfMonth();
      }
    } else if (recur.getType().toInt() == RecurrenceTypeEnum.MONTHLY_NTH.toInt()) {
      rule = "FREQ=MONTHLY;INTERVAL=" + recur.getInterval();

      String wd;
      if (recur.getInstance().intValue() == 5) {
        wd = "-1";
      } else {
        wd = recur.getInstance().toString();
      }
      wd += getWeekdayStr(recur.getDayOfWeekMask());

      rule += ";BYDAY=" + wd;

    } else if (recur.getType().toInt() == RecurrenceTypeEnum.YEARLY.toInt()) {
      rule = "FREQ=YEARLY;";
      rule += ";BYMONTH=" + (recur.getMonthOfYear().intValue() + 1);

      if (recur.getDayOfMonth().intValue() == 31) {
        rule += ";BYMONTHDAY=-1";
      } else {
        rule += ";BYMONTHDAY=" + recur.getDayOfMonth();
      }

    } else if (recur.getType().toInt() == RecurrenceTypeEnum.YEARLY_NTH.toInt()) {
      rule = "FREQ=YEARLY;";

      rule += ";BYMONTH=" + (recur.getMonthOfYear().intValue() + 1);

      String wd;
      if (recur.getInstance().intValue() == 5) {
        wd = "-1";
      } else {
        wd = recur.getInstance().toString();
      }
      wd += getWeekdayStr(recur.getDayOfWeekMask());

      rule += ";BYDAY=" + wd;

    }

    String wkst;
    if (weekStart == Calendar.SUNDAY) {
      wkst = "SU";
    } else {
      wkst = "MO";
    }
    rule += ";WKST=" + wkst;

    if (recur.getOccurrences() != null) {
      rule += ";COUNT=" + recur.getOccurrences();
    } else if (recur.getUntil() != null) {
      Calendar cal = Util.utcLong2UserCalendar(recur.getUntil().longValue(), Constants.UTC_TZ);
      String calStr = Util.userCalendar2String(cal, "yyyMMdd");

      rule += ";UNTIL=" + calStr;
    }

    return rule;

  }

  
  public static List getDaysBetween(String rule, Recurrence recur, Calendar start, Calendar end) {
    Event ev = recur.getEvent();
    Calendar patternStart = Util.utcLong2UserCalendar(recur.getPatternStartDate().longValue(), ev.getTimeZoneObj());

    Recurrance r = new Recurrance(rule, patternStart, end);
    List dates = r.getAllMatchingDates();
    
    for (ListIterator it = dates.listIterator(); it.hasNext();) {
      Calendar c = (Calendar)it.next();
      
      if (c.getTimeInMillis() < start.getTimeInMillis()) {
        it.remove();
      }        
      
    }
    return dates;
    
  
  }  

  

  public static List getDays(String rule, Recurrence recur, Calendar start) {
    
    if (recur.getOccurrences() != null) {
      int occ = recur.getOccurrences().intValue();
      
      Calendar end = (Calendar)start.clone();
      List dates;
      
      do {
        end.add(Calendar.YEAR, 1);  
        Recurrance r = new Recurrance(rule, start, end);
        dates = r.getAllMatchingDates();
                  
      } while(dates.size() < occ);
      
      return dates;
      
    } else {      
      Recurrance r = new Recurrance(rule, start, null);
      List dates = r.getAllMatchingDates();
      return dates;
    }      
  }
  

  
  

}