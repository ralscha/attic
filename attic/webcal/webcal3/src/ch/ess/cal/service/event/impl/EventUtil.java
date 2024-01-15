package ch.ess.cal.service.event.impl;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.Constants;
import ch.ess.cal.Util;
import ch.ess.cal.enums.PosEnum;
import ch.ess.cal.enums.RecurrenceTypeEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.enums.TimeEnum;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.EventDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.service.impl.UserConfig;

import com.managestar.recurrance.Recurrance;

public class EventUtil {

  public static Calendar getTodayCalendar(User user, Config config) {
    Calendar cal = new GregorianCalendar(user.getTimeZone());
    cal.setMinimalDaysInFirstWeek(config.getIntegerProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK, 4));
    cal.setFirstDayOfWeek(config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY));
    return cal;
  }

  public static Integer getDayOfWeekMask(int[] wd) {
    int result = 0;
    for (int i = 0; i < wd.length; i++) {
      result += getDayOfWeekMask(wd[i]);
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
      Calendar cal = Util.utcLong2UserCalendar(recur.getUntil().longValue(), Constants.UTC_TZ);
      String calStr = Util.userCalendar2String(cal, "yyyyMMdd");

      rule += ";UNTIL=" + calStr;
    }

    return rule;

  }

  public static List<Calendar> getDaysBetween(String rule, Recurrence recur, Calendar start, Calendar end,
      TimeZone timeZone) {

    Calendar patternStart = Util.utcLong2UserCalendar(recur.getPatternStartDate().longValue(), timeZone);

    Recurrance r = new Recurrance(rule, patternStart, end);
    List<Calendar> dates = r.getAllMatchingDates();

    for (ListIterator<Calendar> it = dates.listIterator(); it.hasNext();) {
      Calendar c = it.next();

      if (c.getTimeInMillis() < start.getTimeInMillis()) {
        it.remove();
      }

    }
    return dates;

  }

  public static List<Calendar> getDays(String rule, Recurrence recur, Calendar start) {

    if (recur.getOccurrences() != null) {
      int occ = recur.getOccurrences();

      Calendar end = (Calendar)start.clone();
      List<Calendar> dates;

      do {
        end.add(Calendar.YEAR, 1);
        Recurrance r = new Recurrance(rule, start, end);
        dates = r.getAllMatchingDates();

      } while (dates.size() < occ);

      return dates;

    }
    Recurrance r = new Recurrance(rule, start, null);
    List<Calendar> dates = r.getAllMatchingDates();
    return dates;

  }

  public static String getReminderTooltip(MessageResources messages, Locale locale, Set<Reminder> reminders) {
    StringBuilder sb = new StringBuilder();

    sb.append("<table class=\\'list\\'>");
    sb.append("<thead>");
    sb.append("<tr>");
    sb.append("<th align=\\'left\\' width=\\'200\\'>");
    sb.append(messages.getMessage(locale, "user.eMail"));
    sb.append("</th>");
    sb.append("<th align=\\'left\\' width=\\'150\\'>");
    sb.append(messages.getMessage(locale, "event.reminder.time"));
    sb.append("</th>");
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
        //not implemented yet
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
      Calendar tmp = Util.utcLong2UserCalendar(recurrence.getUntil().longValue(), Constants.UTC_TZ);
      result.append(Util.userCalendar2String(tmp));
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

    Set<User> userSet = event.getUsers();
    if (userSet.contains(user)) {
      return true;
    }

    if (event.getGroup() != null) {
      if (user.getGroups().contains(event.getGroup())) {
        return true;
      }
    }

    //event.getGroup()
    return false;
  }

  public static String getTooltip(User user, List<Event> eventList, TimeZone timezone,
      TranslationService translationService, Locale locale) {

    if (eventList != null) {
      StringBuilder sb = new StringBuilder();
      sb.append("<table class=\\'simplelist\\'>");

      sb.append("<tbody>");

      for (Event event : eventList) {
        sb.append("<tr>");

        Calendar startCal = Util.utcLong2UserCalendar(event.getStartDate(), timezone);
        Calendar endCal = Util.utcLong2UserCalendar(event.getEndDate(), timezone);

        if (event.isAllDay()) {
          if (Util.isSameDay(startCal, endCal)) {
            sb.append("<td></td>");
          } else {
            sb.append("<td>");
            sb.append(Util.userCalendar2String(startCal, Constants.getDateFormatPattern()));
            sb.append("-");
            sb.append(Util.userCalendar2String(endCal, Constants.getDateFormatPattern()));
            sb.append("</td>");
          }
        } else {
          if (Util.isSameDay(startCal, endCal)) {
            sb.append("<td>");
            sb.append(Util.userCalendar2String(startCal, Constants.getTimeFormatPattern()));
            sb.append("-");
            sb.append(Util.userCalendar2String(endCal, Constants.getTimeFormatPattern()));
            sb.append("</td>");
          } else {
            sb.append("<td>");
            sb.append(Util.userCalendar2String(startCal, Constants.getDateTimeFormatPattern()));
            sb.append("-");
            sb.append(Util.userCalendar2String(endCal, Constants.getDateTimeFormatPattern()));
            sb.append("</td>");
          }
        }
        sb.append("<td>");

        if ((event.getSensitivity() != SensitivityEnum.PRIVATE)
            || ((event.getSensitivity() == SensitivityEnum.PRIVATE) && isMyEvent(user, event))) {

          sb.append(event.getSubject());
          if (StringUtils.isNotBlank(event.getLocation())) {
            sb.append(", ");
            sb.append(event.getLocation());
          }
          EventCategory eventCategory = event.getEventCategories().iterator().next();
          sb.append("&nbsp;(");
          sb.append(translationService.getText(eventCategory.getCategory(), locale));
          sb.append(")");
        } else {
          //todo translate
          sb.append("private");
        }
        sb.append("</td>");

        if (event.getReminders().size() > 0) {
          sb.append("<td>");
          sb.append("<img src=\\'images/reminder.gif\\' width=\\'20\\' height=\\'12\\'/>");
          sb.append("</td>");
        } else {
          sb.append("<td></td>");
        }

        if (event.getRecurrences().size() > 0) {
          sb.append("<td>");
          sb.append("<img src=\\'images/repeat.gif\\' width=\\'13\\' height=\\'12\\'/>");
          sb.append("</td>");
        } else {
          sb.append("<td></td>");
        }

        sb.append("</tr>");
      }
      sb.append("</tbody>");
      sb.append("</table>");
      return sb.toString();
    }
    return null;
  }

  public static void getEvents(EventDao eventDao, User user, User myUser, Calendar firstDay, Calendar lastDay,
      List<Event>[] eventsArray) {

    getNormalEvents(eventDao, user, myUser, firstDay, lastDay, eventsArray);
    getRecurEvents(eventDao, user, myUser, firstDay, lastDay, eventsArray);
  }

  public static void getNormalEvents(EventDao eventDao, User user, User myUser, Calendar firstDay, Calendar lastDay,
      List<Event>[] eventsArray) {
    List<Event> events = eventDao.getUserNormalEvents(user, firstDay, lastDay);

    for (Event ev : events) {

      if ((ev.getSensitivity() != SensitivityEnum.CONFIDENTIAL)
          || ((ev.getSensitivity() == SensitivityEnum.CONFIDENTIAL) && isMyEvent(myUser, ev))) {

        Calendar start = Util.utcLong2UserCalendar(ev.getStartDate(), firstDay.getTimeZone());
        Calendar end = Util.utcLong2UserCalendar(ev.getEndDate(), firstDay.getTimeZone());

        if (start.getTimeInMillis() < firstDay.getTimeInMillis()) {
          start = firstDay;
        }

        if (end.getTimeInMillis() > lastDay.getTimeInMillis()) {
          end = lastDay;
        }

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
      }
    }
  }

  public static void getRecurEvents(EventDao eventDao, User user, User myUser, Calendar firstDay, Calendar lastDay,
      List<Event>[] eventsArray) {
    //Start Recur Events
    List<Object[]> events = eventDao.getUserRecurEvents(user, firstDay, lastDay);

    for (Object[] obj : events) {

      Event ev = (Event)obj[0];

      if ((ev.getSensitivity() != SensitivityEnum.CONFIDENTIAL)
          || ((ev.getSensitivity() == SensitivityEnum.CONFIDENTIAL) && isMyEvent(myUser, ev))) {

        Recurrence recur = (Recurrence)obj[1];
        List<Calendar> dates = EventUtil.getDaysBetween(recur.getRfcRule(), recur, firstDay, lastDay, user
            .getTimeZone());

        for (Calendar startRecur : dates) {

          Calendar endRecur = (Calendar)startRecur.clone();
          endRecur.add(Calendar.MINUTE, recur.getDuration().intValue());

          if (!ev.isAllDay()) {
            Calendar startTime = Util.utcLong2UserCalendar(recur.getStartTime().longValue(), user.getTimeZone());
            Calendar endTime = Util.utcLong2UserCalendar(recur.getEndTime().longValue(), user.getTimeZone());
            startRecur.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
            startRecur.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
            endRecur.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
            endRecur.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
          }

          ev.setStartDate(startRecur.getTimeInMillis());
          ev.setEndDate(endRecur.getTimeInMillis());

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

        }
      }
    }
    //End Recur Events 
  }

}