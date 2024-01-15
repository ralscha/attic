package ch.ess.cal.web.view;

import java.awt.Font;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Constants;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.TranslationService;
import ch.ess.base.service.UserConfig;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.service.EventUtil;
import ch.ess.cal.service.HolidayRegistry;
import ch.ess.cal.service.StringLength;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.LazyInit;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id="monthBeanUtil", lazyInit=LazyInit.TRUE, autowire=Autowire.BYTYPE)
public class MonthBeanUtil {

  private EventDao eventDao;
  private TranslationService translationService;
  private UserConfigurationDao userConfigurationDao;
  private HolidayRegistry holidayRegistry;

  public void setTranslationService(final TranslationService translationService) {
    this.translationService = translationService;
  }

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  @SuppressWarnings("unchecked")
  public MonthBean createMonthBean(Locale locale, User user, User myUser, int month, int year, boolean longWeekNames, MessageResources messages) {

    Font tooltipFont = new Font("Arial", Font.PLAIN, 9);
    
    Config config = userConfigurationDao.getUserConfig(user);
    TimeZone timeZone = user.getTimeZone();
    boolean highlightWeekends = config.getBooleanProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);

    DateFormatSymbols symbols = new DateFormatSymbols(locale);
    String[] monthNames = symbols.getMonths();

    MonthBean monthBean = new MonthBean();
    monthBean.setMonth(month);
    monthBean.setYear(year);
    monthBean.setMonthName(monthNames[month]);

    int start = config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);

    Calendar currentDay = CalUtil.newCalendar(timeZone, year, month, 1);
    currentDay.setMinimalDaysInFirstWeek(config.getIntegerProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK, 4));
    currentDay.setFirstDayOfWeek(start);

    int maxDays = currentDay.getActualMaximum(Calendar.DATE);

    String[] daytitles = new String[maxDays + 1];
    String[] weekdays;

    if (longWeekNames) {
      weekdays = symbols.getWeekdays();
    } else {
      weekdays = symbols.getShortWeekdays();
    }

    String[] userWeekdays = new String[7];
    boolean[] weekends = new boolean[maxDays + 1];

    String[] holidays = new String[maxDays + 1];
    Map<Integer, String> result = holidayRegistry.getMonthHolidays(locale, currentDay);

    for (Entry<Integer, String> entry : result.entrySet()) {
      Integer element = entry.getKey();
      String h = entry.getValue();

      h = StringEscapeUtils.escapeJavaScript(h);
      holidays[element.intValue()] = h;
    }

    Calendar today = EventUtil.getTodayCalendar(user, config);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);

    if ((month == todayMonth) && (year == todayYear)) {
      monthBean.setToday(today.get(Calendar.DATE));
    } else {
      monthBean.setToday(-1);
    }

    int firstWeekday = currentDay.get(Calendar.DAY_OF_WEEK);
    int firstWeekdayIndex = 0;

    for (int i = 0; i < 7; i++) {
      userWeekdays[i] = weekdays[start];
      if (start == firstWeekday) {
        firstWeekdayIndex = i;
      }
      start++;
      if (start == 8) {
        start = 1;
      }
    }
    monthBean.setWeekdayNames(userWeekdays);
    monthBean.setWeekLength(userWeekdays.length + 1);

    int weekday;
    int day;
    int weekno = currentDay.get(Calendar.WEEK_OF_YEAR);

    int[] days = new int[7];
    for (int i = 0; i < 7; i++) {
      if (i >= firstWeekdayIndex) {

        day = currentDay.get(Calendar.DATE);
        days[i] = day;
        daytitles[day] = CalUtil.userCalendar2String(currentDay);

        if (highlightWeekends) {
        weekday = currentDay.get(Calendar.DAY_OF_WEEK);
        if ((weekday == Calendar.SUNDAY) || (weekday == Calendar.SATURDAY)) {
          weekends[day] = true;
        } else {
          weekends[day] = false;
        }
        }

        currentDay.add(Calendar.DATE, 1);
      }
    }
    monthBean.getWeekNoList().add(weekno);
    monthBean.getWeekList().add(days);

    days = new int[7];
    int ci = 0;
    boolean first = true;

    int cday = currentDay.get(Calendar.DATE);
    while (cday <= maxDays) {

      if ((ci % 7 == 0) && (!first)) {
        ci = 0;
        monthBean.getWeekNoList().add(weekno);
        monthBean.getWeekList().add(days);
        days = new int[7];
      }
      first = false;

      day = currentDay.get(Calendar.DATE);
      daytitles[day] = CalUtil.userCalendar2String(currentDay);
      days[ci] = day;

      if (highlightWeekends) {
      weekday = currentDay.get(Calendar.DAY_OF_WEEK);
      if ((weekday == Calendar.SUNDAY) || (weekday == Calendar.SATURDAY)) {
        weekends[day] = true;
      } else {
        weekends[day] = false;
      }
      }

      ci++;
      cday++;
      weekno = currentDay.get(Calendar.WEEK_OF_YEAR);
      currentDay.add(Calendar.DATE, 1);
    }

    monthBean.getWeekNoList().add(weekno);
    monthBean.getWeekList().add(days);
    monthBean.setWeekends(weekends);
    monthBean.setDaytitle(daytitles);
    monthBean.setHolidays(holidays);

    //Events
    Calendar firstDay = CalUtil.newCalendar(timeZone, year, month, 1);
    Calendar lastDay = CalUtil.newCalendar(timeZone, year, month, maxDays, 23, 59, 59, 999);

    List<Event>[] eventsArray = new ArrayList[maxDays + 1];
    EventUtil.getEvents(eventDao, user, myUser, firstDay, lastDay, eventsArray, true);

    List<EventDescription>[] eventDescription = new ArrayList[maxDays + 1];
    for (int i = 1; i <= maxDays; i++) {
      List<Event> eventsList = eventsArray[i];

      if ((eventsList != null) && !eventsList.isEmpty()) {
        List<EventDescription> eventDescriptionList = new ArrayList<EventDescription>();
        for (Event event : eventsList) {
          EventDescription descr = new EventDescription();

          StringBuilder sb = new StringBuilder();

          if (!event.isAllDay()) {
            Calendar startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), timeZone);
            Calendar endCal = CalUtil.utcLong2UserCalendar(event.getEndDate(), timeZone);

            if (CalUtil.isSameDay(startCal, endCal)) {
              sb.append(CalUtil.userCalendar2String(startCal, Constants.getTimeFormatPattern()));
              sb.append("-");
              sb.append(CalUtil.userCalendar2String(endCal, Constants.getTimeFormatPattern()));
              sb.append("&nbsp;");
            }
          }
          if (sb.length() == 0) {
            sb.append(StringUtils.abbreviate(event.getSubject(), 22));
          } else {
            sb.append(StringUtils.abbreviate(event.getSubject(), 11));
          }

          descr.setEvent(sb.toString());
          descr.setEventId(event.getId().toString());
          EventCategory eventCategory = event.getEventCategories().iterator().next();
          descr.setColour(eventCategory.getCategory().getColour());

          List<Event> oneEventList = new ArrayList<Event>();
          oneEventList.add(event);
          StringLength stringLength = EventUtil.getTooltip(user, oneEventList, timeZone, translationService, locale, tooltipFont, messages);
          String tooltip = stringLength.getString();
          
          descr.setTooltip(tooltip);

          eventDescriptionList.add(descr);
        }
        eventDescription[i] = eventDescriptionList;
      }

    }

    monthBean.setEvents(eventDescription);

    return monthBean;

  }
}
