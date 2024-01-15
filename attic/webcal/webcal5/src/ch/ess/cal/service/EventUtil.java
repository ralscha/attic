package ch.ess.cal.service;

import java.awt.Font;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.parameter.Value;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.TranslationService;
import ch.ess.base.service.UserConfig;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.enums.PosEnum;
import ch.ess.cal.enums.RecurrenceTypeEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.model.Task;

public class EventUtil {

  public static Calendar getTodayCalendar(User user, Config config) {
    Calendar cal = new GregorianCalendar(user.getTimeZone());
    cal.setMinimalDaysInFirstWeek(config.getIntegerProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK, 4));
    cal.setFirstDayOfWeek(config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY));
    return cal;
  }

  public static Integer getDayOfWeekMask(int[] wd) {
    int result = 0;
    for (int element : wd) {
      result += getDayOfWeekMask(element);
    }
    return result;
  }

  public static Integer getDayOfWeekMask(int wd) {
    switch (wd) {
      case Calendar.SUNDAY :
        return 1;
      case Calendar.MONDAY :
        return 2;
      case Calendar.TUESDAY :
        return 4;
      case Calendar.WEDNESDAY :
        return 8;
      case Calendar.THURSDAY :
        return 16;
      case Calendar.FRIDAY :
        return 32;
      case Calendar.SATURDAY :
        return 64;
    }
    return 0;
  }

  public static Integer[] getWeekdayArray(int maskInt) {
    int mask = maskInt;
    List<Integer> list = new ArrayList<Integer>();

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

    return list.toArray(new Integer[list.size()]);

  }

  public static int getWeekday(int maskInt) {
    int mask = maskInt;
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

  public static String getWeekdayStr(int maskInt) {
    int mask = maskInt;
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

  public static String getWeekdayArrayString(int maskInt) {
    int mask = maskInt;

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

  public static int getWeekdayMask(String str) {
    int mask = 0;

    if (str.indexOf("SU") >= 0) {
      mask = mask + 1;
    }
    if (str.indexOf("MO") >= 0) {
      mask = mask + 2;
    }
    if (str.indexOf("TU") >= 0) {
      mask = mask + 4;
    }
    if (str.indexOf("WE") >= 0) {
      mask = mask + 8;
    }
    if (str.indexOf("TH") >= 0) {
      mask = mask + 16;
    }
    if (str.indexOf("FR") >= 0) {
      mask = mask + 32;
    }
    if (str.indexOf("SA") >= 0) {
      mask = mask + 64;
    }
    return mask;
  }

  public static int getWeekdayNo(String str) {
    if (str.equals("SU")) {
      return 1;
    }
    if (str.equals("MO")) {
      return 2;
    }
    if (str.equals("TU")) {
      return 4;
    }
    if (str.equals("WE")) {
      return 8;
    }
    if (str.equals("TH")) {
      return 16;
    }
    if (str.equals("FR")) {
      return 32;
    }
    if (str.equals("SA")) {
      return 64;
    }
    return 0;
  }

  public static String getParameter(String key, String str) {
    int p1 = str.indexOf(key);
    if (p1 >= 0) {
      int p2 = str.indexOf(";", p1);
      if (p2 >= 0) {
        String value = str.substring(p1 + key.length(), p2);
        return value;
      }
      String value = str.substring(p1 + key.length());
      return value;
    }
    return null;
  }

  public static Recurrence getRecurrence(TimeZone tz, String rule, Event event) throws ParseException {
    Recurrence recur = new Recurrence();
    recur.setActive(true);
    long diff = event.getEndDate() - event.getStartDate();
    diff = diff / 1000 / 60;
    recur.setDuration(diff);

    String freq = getParameter("FREQ=", rule);

    if ("DAILY".equalsIgnoreCase(freq)) {
      recur.setType(RecurrenceTypeEnum.DAILY);
      String interval = getParameter("INTERVAL=", rule);
      if (interval != null) {
        recur.setInterval(new Integer(interval));
      } else {
        recur.setInterval(1);
      }
    } else if ("WEEKLY".equalsIgnoreCase(freq)) {
      recur.setType(RecurrenceTypeEnum.WEEKLY);
      String interval = getParameter("INTERVAL=", rule);
      if (interval != null) {
        recur.setInterval(new Integer(interval));
      } else {
        recur.setInterval(1);
      }
      String byDay = getParameter("BYDAY=", rule);
      recur.setDayOfWeekMask(getWeekdayMask(byDay));
    } else if ("MONTHLY".equalsIgnoreCase(freq)) {
      String byMonthDay = getParameter("BYMONTHDAY=", rule);
      String interval = getParameter("INTERVAL=", rule);
      if (interval != null) {
        recur.setInterval(new Integer(interval));
      } else {
        recur.setInterval(1);
      }
      if (byMonthDay != null) {
        recur.setType(RecurrenceTypeEnum.MONTHLY);
        recur.setDayOfMonth(new Integer(byMonthDay));
        if (recur.getDayOfMonth() == -1) {
          recur.setDayOfMonth(31);
        }
      } else {
        recur.setType(RecurrenceTypeEnum.MONTHLY_NTH);
        String byDay = getParameter("BYDAY=", rule);
        String weekday = byDay.substring(byDay.length() - 2);
        recur.setDayOfWeekMask(getWeekdayNo(weekday));
        String instanc = byDay.substring(0, 1);
        if (instanc.equals("1")) {
          recur.setInstance(PosEnum.FIRST);
        } else if (instanc.equals("2")) {
          recur.setInstance(PosEnum.SECOND);
        } else if (instanc.equals("3")) {
          recur.setInstance(PosEnum.THIRD);
        } else if (instanc.equals("4")) {
          recur.setInstance(PosEnum.FOURTH);
        } else if (instanc.equals("-")) {
          recur.setInstance(PosEnum.LAST);
        }
      }
    } else if ("YEARLY".equalsIgnoreCase(freq)) {
      String byMonthDay = getParameter("BYMONTHDAY=", rule);
      String interval = getParameter("INTERVAL=", rule);
      if (interval != null) {
        recur.setInterval(new Integer(interval));
      } else {
        recur.setInterval(1);
      }
      String month = getParameter("BYMONTH=", rule);
      recur.setMonthOfYear(new Integer(month)-1);
      if (byMonthDay != null) {
        recur.setType(RecurrenceTypeEnum.YEARLY);
        recur.setDayOfMonth(new Integer(byMonthDay));
        if (recur.getDayOfMonth() == -1) {
          recur.setDayOfMonth(31);
        }
      } else {
        recur.setType(RecurrenceTypeEnum.YEARLY_NTH);
        String byDay = getParameter("BYDAY=", rule);
        String weekday = byDay.substring(byDay.length() - 2);
        recur.setDayOfWeekMask(getWeekdayNo(weekday));
        String instanc = byDay.substring(0, 1);
        if (instanc.equals("1")) {
          recur.setInstance(PosEnum.FIRST);
        } else if (instanc.equals("2")) {
          recur.setInstance(PosEnum.SECOND);
        } else if (instanc.equals("3")) {
          recur.setInstance(PosEnum.THIRD);
        } else if (instanc.equals("4")) {
          recur.setInstance(PosEnum.FOURTH);
        } else if (instanc.equals("-")) {
          recur.setInstance(PosEnum.LAST);
        }
      }
    } else {
      return null;
    }

    String count = getParameter("COUNT=", rule);
    String until = getParameter("UNTIL=", rule);
    if (count != null) {
      recur.setOccurrences(new Integer(count));
      recur.setAlways(false);
    } else if (until != null) {

      Date untilDate = null;
      if (until.length() == 8) {
        DateFormat df = new SimpleDateFormat(Constants.DATE_LOCAL_FORMAT);
        df.setTimeZone(Constants.UTC_TZ);
        untilDate = df.parse(until);
      } else if (until.length() == 15) {
        DateFormat df = new SimpleDateFormat(Constants.DATETIME_LOCAL_FORMAT);
        df.setTimeZone(tz);
        untilDate = df.parse(until);
      } else {
        untilDate = Constants.DATE_UTC_FORMAT.parse(until);
      }

      recur.setUntil(untilDate.getTime());
      recur.setAlways(false);
    } else {
      recur.setAlways(true);
    }
    recur.setExclude(false);

    Calendar startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), Constants.UTC_TZ);
    List<Calendar> days = EventUtil.getDays(rule, startCal);
    if (!days.isEmpty()) {
      Calendar s = days.get(0);
      recur.setPatternStartDate(s.getTimeInMillis());

      if (recur.isAlways()) {
        recur.setPatternEndDate(null);
      } else {
        Calendar e = days.get(days.size() - 1);
        recur.setPatternEndDate(e.getTimeInMillis());
      }
    }

    return recur;
  }

  public static String getRfcRule(Recurrence recur, int weekStart) {

    String rule = "";

    if (recur.getType() == RecurrenceTypeEnum.DAILY) {
      rule = "FREQ=DAILY;INTERVAL=" + recur.getInterval();

    } else if (recur.getType() == RecurrenceTypeEnum.WEEKLY) {
      rule = "FREQ=WEEKLY;INTERVAL=" + recur.getInterval();
      rule += ";BYDAY=" + getWeekdayArrayString(recur.getDayOfWeekMask());

    } else if (recur.getType() == RecurrenceTypeEnum.MONTHLY) {
      rule = "FREQ=MONTHLY;INTERVAL=" + recur.getInterval();
      if (recur.getDayOfMonth() == 31) {
        rule += ";BYMONTHDAY=-1";
      } else {
        rule += ";BYMONTHDAY=" + recur.getDayOfMonth();
      }
    } else if (recur.getType() == RecurrenceTypeEnum.MONTHLY_NTH) {
      rule = "FREQ=MONTHLY;INTERVAL=" + recur.getInterval();

      String wd;
      if (recur.getInstance() == PosEnum.LAST) {
        wd = "-1";
      } else {
        wd = String.valueOf(recur.getInstance().getValue());
      }
      wd += getWeekdayStr(recur.getDayOfWeekMask());

      rule += ";BYDAY=" + wd;

    } else if (recur.getType() == RecurrenceTypeEnum.YEARLY) {
      rule = "FREQ=YEARLY;";
      rule += ";BYMONTH=" + (recur.getMonthOfYear() + 1);

      if (recur.getDayOfMonth() == 31) {
        rule += ";BYMONTHDAY=-1";
      } else {
        rule += ";BYMONTHDAY=" + recur.getDayOfMonth();
      }

    } else if (recur.getType() == RecurrenceTypeEnum.YEARLY_NTH) {
      rule = "FREQ=YEARLY;";

      rule += ";BYMONTH=" + (recur.getMonthOfYear() + 1);

      String wd;
      if (recur.getInstance() == PosEnum.LAST) {
        wd = "-1";
      } else {
        wd = String.valueOf(recur.getInstance().getValue());
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
      Calendar cal = CalUtil.utcLong2UserCalendar(recur.getUntil().longValue(), Constants.UTC_TZ);
      String calStr = CalUtil.userCalendar2String(cal, "yyyyMMdd");

      rule += ";UNTIL=" + calStr + "T000000Z";
    }

    return rule;

  }


  @SuppressWarnings("unchecked")
  public static List<Calendar> getDaysBetween(Recurrence recurrence, Calendar start, Calendar end, TimeZone timeZone) {

    try {
      Calendar patternStart = new GregorianCalendar(Constants.UTC_TZ);
      patternStart.setTimeInMillis(recurrence.getPatternStartDate());

      List<Calendar> dates = new ArrayList<Calendar>();

      DateTime startDateTime = new DateTime(start.getTime());
      DateTime endDateTime = new DateTime(end.getTime());
      DateTime patternStartTime = new DateTime(patternStart.getTime());

      Recur recur = new Recur(recurrence.getRfcRule());
      
      DateList dateList = recur.getDates(patternStartTime, startDateTime, endDateTime, Value.DATE_TIME);

      for (Iterator<Date> it = dateList.iterator(); it.hasNext();) {
        Date d = it.next();
        Calendar cal = new GregorianCalendar(Constants.UTC_TZ);
        cal.setTimeInMillis(d.getTime());
        dates.add(cal);
      }

      List<Calendar> convDates = new ArrayList<Calendar>();
      for (Calendar calendar : dates) {
        Calendar cal = null;
        if (recurrence.getEvent().isAllDay()) {
          cal = CalUtil.newCalendar(timeZone, calendar.get(Calendar.YEAR),
              calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0, 0);
          
        } else {
          Calendar startDate = CalUtil.utcLong2UserCalendar(recurrence.getEvent().getStartDate(), timeZone);
          
          cal = CalUtil.newCalendar(timeZone, calendar.get(Calendar.YEAR),
              calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 
              startDate.get(Calendar.HOUR_OF_DAY),
              startDate.get(Calendar.MINUTE),
              startDate.get(Calendar.SECOND),
              startDate.get(Calendar.MILLISECOND));
        }
        convDates.add(cal);
      }      
      
      return convDates;
    } catch (ParseException e) {
      LogFactory.getLog(EventUtil.class).error("getDaysBetween", e);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static List<Calendar> getDays(String rule, Calendar start) {
    
    try {
      Recur recur = new Recur(rule);
      
      DateTime startDateTime = new DateTime(start.getTimeInMillis());

      List<Calendar> dates = new ArrayList<Calendar>();

      if (recur.getCount() > 0) {

        Calendar end = (Calendar)start.clone();

        do {
          end.add(Calendar.YEAR, 1);

          DateTime endDateTime = new DateTime(end.getTimeInMillis());
          DateList dateList = recur.getDates(startDateTime, startDateTime, endDateTime, Value.DATE_TIME);

          for (Iterator<Date> it = dateList.iterator(); it.hasNext();) {
            Date d = it.next();
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(d.getTime());
            dates.add(cal);
          }

        } while (dates.size() < recur.getCount());

        return dates;

      } else if (recur.getUntil() != null) {

        DateTime endDateTime = new DateTime(recur.getUntil().getTime() + (1000 * 60 * 60 * 24));

        DateList dateList = recur.getDates(startDateTime, startDateTime, endDateTime, Value.DATE_TIME);

        for (Iterator<Date> it = dateList.iterator(); it.hasNext();) {
          Date d = it.next();
          Calendar cal = new GregorianCalendar();
          cal.setTimeInMillis(d.getTime());
          dates.add(cal);
        }

        return dates;

      } else {
        Calendar end = (Calendar)start.clone();

        if (recur.getFrequency().equals(Recur.DAILY)) {
          end.add(Calendar.DATE, recur.getInterval() * 2);
        } else if (recur.getFrequency().equals(Recur.WEEKLY)) {
          end.add(Calendar.DATE, recur.getInterval() * 2 * 7);
        } else if (recur.getFrequency().equals(Recur.MONTHLY)) {
          end.add(Calendar.MONTH, recur.getInterval() * 2);
        } else if (recur.getFrequency().equals(Recur.YEARLY)) {
          end.add(Calendar.YEAR, recur.getInterval() * 2);
        }

        DateTime endDateTime = new DateTime(end.getTimeInMillis() + (1000 * 60 * 60 * 24));
        DateList dateList = recur.getDates(startDateTime, startDateTime, endDateTime, Value.DATE_TIME);

        for (Iterator<Date> it = dateList.iterator(); it.hasNext();) {
          Date d = it.next();
          Calendar cal = new GregorianCalendar();
          cal.setTimeInMillis(d.getTime());
          dates.add(cal);
        }

        return dates;

      }

    } catch (ParseException e) {
      LogFactory.getLog(EventUtil.class).error("getDays", e);
    }
    
    return null;
  }



  public static String getReminderTooltip(MessageResources messages, Locale locale, Set<Reminder> reminders) {
    return getReminderTooltip(messages, locale, reminders, true);
  }
  
  public static String getReminderTooltip(MessageResources messages, Locale locale, Set<Reminder> reminders, boolean eventReminder) {
    StringBuilder sb = new StringBuilder();

    sb.append("<table class=\\'list\\'>");
    sb.append("<thead>");
    sb.append("<tr>");
    sb.append("<th align=\\'left\\' width=\\'200\\'>");
    sb.append(messages.getMessage(locale, "user.eMail"));
    sb.append("</th>");
    sb.append("<th align=\\'left\\' width=\\'150\\'>");
    if (eventReminder) {
      sb.append(messages.getMessage(locale, "event.reminder.time"));
    } else {
      sb.append(messages.getMessage(locale, "task.reminder.time"));
    }
    sb.append("</th>");
        
    if (!eventReminder) {
      sb.append("<th align=\\'left\\' width=\\'150\\'>");
      sb.append(messages.getMessage(locale, "task.reminder.before"));
      sb.append("</th>");      
    }
    
    sb.append("</tr>");
    sb.append("</thead>");
    sb.append("<tbody>");
    for (Reminder reminder : reminders) {
      sb.append("<tr>");
      sb.append("<td>");
      sb.append(reminder.getEmail());
      sb.append("</td>");

      sb.append("<td>");
      TimeEnum timeEnum = reminder.getTimeUnit();
      sb.append(reminder.getTime() + " " + messages.getMessage(locale, timeEnum.getKey()));
      sb.append("</td>");

      if (!eventReminder) {
        sb.append("<td>");
        sb.append(messages.getMessage(locale, reminder.getTaskBefore().getKey()));
        sb.append("</td>");
      }
      
      sb.append("</tr>");

    }
    sb.append("</tbody>");
    sb.append("</table>");

    return sb.toString();

  }

  public static String getRecurrenceDescription(MessageResources messages, Locale locale, Recurrence recurrence) {
    StringBuilder result = new StringBuilder();

    switch (recurrence.getType()) {
      case DAILY :

        result.append(messages.getMessage(locale, "event.recurrence.daily.every"));
        result.append(" ");
        result.append(recurrence.getInterval());
        result.append(" ");
        result.append(messages.getMessage(locale, "event.recurrence.days"));

        break;
      case WEEKLY :
        result.append(messages.getMessage(locale, "event.recurrence.recurevery"));
        result.append(" ");
        result.append(recurrence.getInterval());
        result.append(" ");
        result.append(messages.getMessage(locale, "event.recurrence.weekson"));
        result.append(": ");

        
        Integer[] weekdays = getWeekdayArray(recurrence.getDayOfWeekMask());
        StringBuilder sb = new StringBuilder();
        for (int weekdayIndex : weekdays) {
          if (sb.length() > 0) {
            sb.append(",");
          }
          sb.append(getWeekdayName(locale, weekdayIndex));
        }
        result.append(sb);
        break;
      case MONTHLY :
        result.append(messages.getMessage(locale, "event.recurrence.day"));
        result.append(" ");
        result.append(recurrence.getDayOfMonth());
        result.append(" ");
        result.append(messages.getMessage(locale, "event.recurrence.ofevery"));
        result.append(" ");
        result.append(recurrence.getInterval());
        result.append(" ");
        result.append(messages.getMessage(locale, "event.recurrence.months"));

        break;
      case MONTHLY_NTH :

        result.append(messages.getMessage(locale, "event.recurrence.the"));
        result.append(" ");
        result.append(messages.getMessage(locale, recurrence.getInstance().getKey()));
        result.append(" ");
        result.append(getWeekdayName(locale, getWeekday(recurrence.getDayOfWeekMask())));
        result.append(" ");
        result.append(messages.getMessage(locale, "event.recurrence.ofevery"));
        result.append(" ");
        result.append(recurrence.getInterval());
        result.append(" ");
        result.append(messages.getMessage(locale, "event.recurrence.months"));

        break;

      case YEARLY :
        result.append(messages.getMessage(locale, "event.recurrence.yearly.every"));
        result.append(" ");
        result.append(recurrence.getDayOfMonth());
        result.append(" ");
        result.append(getMonthName(locale, recurrence.getMonthOfYear()));
        break;

      case YEARLY_NTH :

        result.append(messages.getMessage(locale, "event.recurrence.the"));
        result.append(" ");
        result.append(messages.getMessage(locale, recurrence.getInstance().getKey()));
        result.append(" ");
        result.append(getWeekdayName(locale, getWeekday(recurrence.getDayOfWeekMask())));
        result.append(" ");
        result.append(messages.getMessage(locale, "event.recurrence.of"));
        result.append(" ");
        result.append(getMonthName(locale, recurrence.getMonthOfYear()));
        break;

      case DATES :
        // not implemented yet
        break;
    }

    result.append(". ");

    if (recurrence.isAlways()) {
      result.append(messages.getMessage(locale, "event.recurrence.range.noend"));
    } else if (recurrence.getOccurrences() != null) {
      result.append(messages.getMessage(locale, "event.recurrence.range.endafter"));
      result.append(" ");
      result.append(recurrence.getOccurrences());
      result.append(" ");
      result.append(messages.getMessage(locale, "event.recurrence.range.occurrences"));
    } else if (recurrence.getUntil() != null) {
      result.append(messages.getMessage(locale, "event.recurrence.range.endby"));
      result.append(" ");
      Calendar tmp = CalUtil.utcLong2UserCalendar(recurrence.getUntil().longValue(), Constants.UTC_TZ);
      result.append(CalUtil.userCalendar2String(tmp));
    }

    return result.toString();

  }

  private static String getWeekdayName(Locale locale, int weekdayIndex) {
    DateFormatSymbols symbols = new DateFormatSymbols(locale);
    String[] weekdays = symbols.getWeekdays();
    return weekdays[weekdayIndex];
  }

  private static String getMonthName(Locale locale, int monthIndex) {
    DateFormatSymbols symbols = new DateFormatSymbols(locale);

    String[] months = symbols.getMonths();
    return months[monthIndex];
  }

  public static boolean isMyEvent(User user, Event event) {

    if (user == null) {
      return false;
    }

    Set<User> userSet = event.getUsers();
    if (userSet.contains(user)) {
      return true;
    }

    if (event.getGroup() != null) {
      if (user.getGroups().contains(event.getGroup())) {
        return true;
      }
    }

    return false;
  }

  public static String getTaskTooltip(Task task) {
    StringBuilder sb = new StringBuilder();
    if (StringUtils.isNotBlank(task.getDescription())) {
      sb.append("<table class=\\'tasktooltip\\'>");
      sb.append("<tbody><tr><td>");
      String description = task.getDescription();
      description = StringUtils.replace(description, "\r\n", "<br>");
      description = StringUtils.replace(description, "\r", "<br>");
      description = StringUtils.replace(description, "\n", "<br>");
      sb.append(description);      
      sb.append("</td></tr></tbody>");
    }
    
    return sb.toString();
  }
  
  public static StringLength getTooltip(User user, List<Event> eventList, TimeZone timezone,
      TranslationService translationService, Locale locale, Font tooltipFont, MessageResources messages) {

    if (eventList != null) {
      StringBuilder sb = new StringBuilder();
      sb.append("<table class=\\'simplelist\\'>");

      sb.append("<tbody>");

      int maxLength = 0;

      for (Event event : eventList) {

        int length = 0;
        sb.append("<tr>");

        Calendar startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), timezone);
        Calendar endCal = CalUtil.utcLong2UserCalendar(event.getEndDate(), timezone);

        if (event.isAllDay()) {
          if (CalUtil.isSameDay(startCal, endCal)) {
            sb.append("<td></td>");
          } else {
            sb.append("<td>");

            String start = CalUtil.userCalendar2String(startCal, Constants.getDateFormatPattern());
            String end = CalUtil.userCalendar2String(endCal, Constants.getDateFormatPattern());
            length = Util.getStrLenPcx(start + "-" + end, tooltipFont);

            sb.append(start);
            sb.append("-");
            sb.append(end);
            sb.append("</td>");
          }
        } else {
          if (CalUtil.isSameDay(startCal, endCal)) {
            sb.append("<td>");

            String start = CalUtil.userCalendar2String(startCal, Constants.getTimeFormatPattern());
            String end = CalUtil.userCalendar2String(endCal, Constants.getTimeFormatPattern());
            length = Util.getStrLenPcx(start + "-" + end, tooltipFont);

            sb.append(start);
            sb.append("-");
            sb.append(end);
            sb.append("</td>");
          } else {
            sb.append("<td>");

            String start = CalUtil.userCalendar2String(startCal, Constants.getDateTimeFormatPattern());
            String end = CalUtil.userCalendar2String(endCal, Constants.getDateTimeFormatPattern());
            length = Util.getStrLenPcx(start + "-" + end, tooltipFont);

            sb.append(start);
            sb.append("-");
            sb.append(end);
            sb.append("</td>");
          }
        }
        sb.append("<td>");

        if ((event.getSensitivity() != SensitivityEnum.PRIVATE)
            || ((event.getSensitivity() == SensitivityEnum.PRIVATE) && isMyEvent(user, event))) {

          StringBuilder sbDescr = new StringBuilder();

          sbDescr.append(StringEscapeUtils.escapeJavaScript(event.getSubject()));
          if (StringUtils.isNotBlank(event.getLocation())) {
            sbDescr.append(", ");
            sbDescr.append(event.getLocation());
          }
          EventCategory eventCategory = event.getEventCategories().iterator().next();
          sbDescr.append("&nbsp;(");
          sbDescr.append(translationService.getText(eventCategory.getCategory(), locale));
          sbDescr.append(")");

          String descr = sbDescr.toString();
          length += Util.getStrLenPcx(descr, tooltipFont);
          sb.append(descr);
        } else {
          String privateStr = messages.getMessage(locale, "event.private");          
          length += Util.getStrLenPcx(privateStr, tooltipFont);
          sb.append(privateStr);
        }
        sb.append("</td>");

        if (event.getReminders().size() > 0) {
          sb.append("<td>");
          sb.append("<img src=\\'images/reminder.gif\\' width=\\'20\\' height=\\'12\\'/>");
          sb.append("</td>");
          length += 20;
        } else {
          sb.append("<td></td>");
        }

        if (event.getRecurrences().size() > 0) {
          sb.append("<td>");
          sb.append("<img src=\\'images/repeat.gif\\' width=\\'13\\' height=\\'12\\'/>");
          sb.append("</td>");
          length += 13;
        } else {
          sb.append("<td></td>");
        }

        sb.append("</tr>");
        length += 10;
        maxLength = Math.max(maxLength, length);
      }
      sb.append("</tbody>");
      sb.append("</table>");

      StringLength stringLength = new StringLength(sb.toString(), maxLength);
      return stringLength;
    }
    return null;
  }

  public static void getEvents(EventDao eventDao, User user, User myUser, Calendar firstDay, Calendar lastDay,
      List<Event>[] eventsArray, boolean monthView) {

    getNormalEvents(eventDao, user, myUser, firstDay, lastDay, eventsArray, monthView);
    getRecurEvents(eventDao, user, myUser, firstDay, lastDay, eventsArray, monthView);
  }

  public static void getNormalEvents(EventDao eventDao, User user, User myUser, Calendar firstDay, Calendar lastDay,
      List<Event>[] eventsArray, boolean monthView) {
    List<Event> events = eventDao.getUserNormalEvents(user, firstDay, lastDay);

    for (Event ev : events) {

      if ((ev.getSensitivity() != SensitivityEnum.CONFIDENTIAL)
          || ((ev.getSensitivity() == SensitivityEnum.CONFIDENTIAL) && isMyEvent(myUser, ev))) {

        Calendar start = CalUtil.utcLong2UserCalendar(ev.getStartDate(), firstDay.getTimeZone());
        Calendar end = CalUtil.utcLong2UserCalendar(ev.getEndDate(), firstDay.getTimeZone());

        if (start.getTimeInMillis() < firstDay.getTimeInMillis()) {
          start = firstDay;
        }

        if (end.getTimeInMillis() > lastDay.getTimeInMillis()) {
          end = lastDay;
        }

        if (monthView) {
          int day = start.get(Calendar.DATE);
          do {
  
            List<Event> eventList = eventsArray[day];
            if (eventList == null) {
              eventList = new ArrayList<Event>();
              eventsArray[day] = eventList;
            }
            eventList.add(ev);
  
            day++;
          } while (day <= end.get(Calendar.DATE));
        } else {
          int day = start.get(Calendar.DAY_OF_YEAR);
          do {
  
            List<Event> eventList = eventsArray[day];
            if (eventList == null) {
              eventList = new ArrayList<Event>();
              eventsArray[day] = eventList;
            }
            eventList.add(ev);
  
            day++;
          } while (day <= end.get(Calendar.DAY_OF_YEAR));        
        }
      }
    }
  }

  public static void getRecurEvents(EventDao eventDao, User user, User myUser, Calendar firstDay, Calendar lastDay,
      List<Event>[] eventsArray, boolean monthView) {
    // Start Recur Events
    List<Object[]> events = eventDao.getUserRecurEvents(user, firstDay, lastDay);

    for (Object[] obj : events) {

      Event ev = (Event)obj[0];

      if ((ev.getSensitivity() != SensitivityEnum.CONFIDENTIAL)
          || ((ev.getSensitivity() == SensitivityEnum.CONFIDENTIAL) && isMyEvent(myUser, ev))) {

        Recurrence recur = (Recurrence)obj[1];
        List<Calendar> dates = EventUtil.getDaysBetween(recur, firstDay, lastDay, user.getTimeZone());

        for (Calendar startRecur : dates) {

          Calendar endRecur = (Calendar)startRecur.clone();
          endRecur.add(Calendar.MINUTE, recur.getDuration().intValue());

          if (!ev.isAllDay()) {
            Calendar endTime = CalUtil.utcLong2UserCalendar(recur.getEvent().getEndDate(), user.getTimeZone());
            endRecur.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
            endRecur.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
            endRecur.set(Calendar.SECOND, 0);
            endRecur.set(Calendar.MILLISECOND, 0);
          }

          ev.setStartDate(startRecur.getTimeInMillis());
          ev.setEndDate(endRecur.getTimeInMillis());


          if (monthView) {
            int day = startRecur.get(Calendar.DATE);
            do {
              
              List<Event> eventList = eventsArray[day];
              if (eventList == null) {
                eventList = new ArrayList<Event>();
                eventsArray[day] = eventList;
              }
              eventList.add(ev);
              
              day++;
              
            } while (day <= endRecur.get(Calendar.DATE));
          } else {
            int day = startRecur.get(Calendar.DAY_OF_YEAR);
            do {
              
              List<Event> eventList = eventsArray[day];
              if (eventList == null) {
                eventList = new ArrayList<Event>();
                eventsArray[day] = eventList;
              }
              eventList.add(ev);
              
              day++;
              
            } while (day <= endRecur.get(Calendar.DAY_OF_YEAR));          
          }
        }
      }
    }
    // End Recur Events
  }

  
  public static List<Event> getEvents(EventDao eventDao, User user, User myUser, Calendar firstDay, Calendar lastDay) {

    List<Event> eventsList = new ArrayList<Event>();
    
    List<Event> events = eventDao.getUserNormalEvents(user, firstDay, lastDay);       
    for (Event ev : events) {

      if ((ev.getSensitivity() != SensitivityEnum.CONFIDENTIAL)
          || ((ev.getSensitivity() == SensitivityEnum.CONFIDENTIAL) && isMyEvent(myUser, ev))) {
        eventsList.add(ev);
      }
    }
    
    
    List<Object[]> eventsObj = eventDao.getUserRecurEvents(user, firstDay, lastDay);

    for (Object[] obj : eventsObj) {

      Event ev = (Event)obj[0];

      if ((ev.getSensitivity() != SensitivityEnum.CONFIDENTIAL)
          || ((ev.getSensitivity() == SensitivityEnum.CONFIDENTIAL) && isMyEvent(myUser, ev))) {

        eventsList.add(ev);
        //Recurrence r = ev.getRecurrences().iterator().next();
      }
    }
    
    
    return eventsList;

  }

}
