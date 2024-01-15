package ch.ess.cal.web.view;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.cal.Util;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.EventDao;
import ch.ess.cal.persistence.GroupDao;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.event.impl.EventDistribution;
import ch.ess.cal.service.event.impl.EventUtil;
import ch.ess.cal.service.impl.HolidayRegistry;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.service.impl.UserConfig;

/** 
 * @author sr
 * @version $Revision: 1.12 $ $Date: 2005/04/04 11:31:11 $ 
 * 
 * @struts.action path="/groupMonth" name="groupMonthForm" roles="all" input="/groupmonth.jsp" scope="session" validate="false"  
 * 
 * @spring.bean name="/groupMonth" lazy-init="true" 
 */
public class GroupMonthAction extends Action {

  private UserDao userDao;
  private GroupDao groupDao;
  private EventDao eventDao;
  private TranslationService translationService;
  private UserConfigurationDao userConfigurationDao;
  private HolidayRegistry holidayRegistry;

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(final TranslationService translationService) {
    this.translationService = translationService;
  }

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * @spring.property reflocal="groupDao"
   */
  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  /**
   * @spring.property reflocal="userConfigurationDao" 
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  /**
   * @spring.property reflocal="eventDao" 
   */
  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  /**
   * @spring.property reflocal="holidayRegistry" 
   */
  public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    GroupMonthForm monthForm = (GroupMonthForm)form;
    monthForm.setGraphicTyp(1);

    HttpSession session = request.getSession();

    User user = Util.getUser(session, userDao);
    Config userConfig = userConfigurationDao.getUserConfig(user);
    boolean highlightWeekends = userConfig.getBooleanProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);

    Locale locale = getLocale(request);

    Calendar today = EventUtil.getTodayCalendar(user, userConfig);

    boolean changed = false;
    if (monthForm.getInputMonth() >= 0) {
      if (monthForm.getInputMonth() != today.get(Calendar.MONTH)) {
        today.set(Calendar.MONTH, monthForm.getInputMonth());
        changed = true;
      }
    }

    if (monthForm.getInputYear() > 0) {
      if (monthForm.getInputYear() != today.get(Calendar.YEAR)) {
        today.set(Calendar.YEAR, monthForm.getInputYear());
        changed = true;
      }
    }

    if (!changed) {
      monthForm.setToday(today.get(Calendar.DATE));
    } else {
      monthForm.setToday(-1);
    }

    //Length of Month
    int length = today.getActualMaximum(Calendar.DATE);
    monthForm.setLength(length);

    //Daytitle
    String[] daytitles = new String[length + 1];

    //Holidays      
    String[] holidays = new String[length + 1];
    Map<Integer, String> result = holidayRegistry.getMonthHolidays(locale, today);

    for (Map.Entry<Integer, String> entry : result.entrySet()) {

      Integer element = entry.getKey();
      String h = entry.getValue();

      h = Util.javascriptEscape(h);
      holidays[element] = h;
    }
    monthForm.setHolidays(holidays);

    //Weekends
    String[] weekends = new String[length + 1];

    //Year
    monthForm.setYear(today.get(Calendar.YEAR));

    //Month
    monthForm.setMonth(today.get(Calendar.MONTH));

    //Monthnames      
    DateFormatSymbols symbols = new DateFormatSymbols(locale);
    monthForm.setMonthName(symbols.getMonths()[today.get(Calendar.MONTH)]);

    String[] shortWeekdays = symbols.getShortWeekdays();
    String[] monthNames = symbols.getMonths();
    monthForm.setMonthNames(monthNames);

    //Weekno, Colspan      
    today.set(Calendar.DATE, 1);
    int mweekno = today.get(Calendar.WEEK_OF_YEAR);
    int colspan = 0;

    String[] weekdayNames = new String[length + 1];
    for (int i = 1; i <= length; i++) {

      daytitles[i] = Util.userCalendar2String(today);

      int weekday = today.get(Calendar.DAY_OF_WEEK);
      int weekno = today.get(Calendar.WEEK_OF_YEAR);

      if (highlightWeekends) {
      if ((weekday == Calendar.SUNDAY) || (weekday == Calendar.SATURDAY)) {
        weekends[i] = "w";
      }
      }

      if (weekno == mweekno) {
        colspan++;
      } else {
        monthForm.getWeekColspan().add(colspan);
        monthForm.getWeekNos().add(mweekno);
        mweekno = weekno;
        colspan = 1;
      }

      weekdayNames[i] = shortWeekdays[weekday];

      today.add(Calendar.DATE, +1);

    }

    today.add(Calendar.DATE, -1);

    monthForm.setWeekends(weekends);
    monthForm.setWeekdayNames(weekdayNames);
    monthForm.setDaytitle(daytitles);

    if (colspan > 0) {
      monthForm.getWeekColspan().add(colspan);
      monthForm.getWeekNos().add(mweekno);
    }

    //Users

    Integer groupId = monthForm.getGroupId();
    Group group = null;
    Set<User> usersSet = new TreeSet<User>(new UserComparator());

    if (groupId == null) {
      if (user.getGroups().size() > 0) {
        group = user.getGroups().iterator().next();
      } else {
        if (user.getAccessGroups().size() > 0) {
          group = user.getAccessGroups().iterator().next();
        }
      }
    } else {
      group = (Group)groupDao.get(groupId.toString());
    }

    if (group != null) {
      usersSet.addAll(group.getUsers());
    } else {
      usersSet.add(user);
    }

    int year = today.get(Calendar.YEAR);
    int month = today.get(Calendar.MONTH);
    int lastday = today.getActualMaximum(Calendar.DAY_OF_MONTH);

    Calendar firstDay = Util.newCalendar(today.getTimeZone(), year, month, 1);
    Calendar lastDay = Util.newCalendar(today.getTimeZone(), year, month, lastday, 23, 59, 59, 999);

    Map<String, Map<String, EventDistribution>> userEvents = new HashMap<String, Map<String, EventDistribution>>();
    List<UserEvents> userList = new ArrayList<UserEvents>();

    EventComparator eventComparator = new EventComparator();

    for (User us : usersSet) {

      List<Event>[] eventsArray = new ArrayList[length + 1];

      Map<String, EventDistribution> eventsMap = new HashMap<String, EventDistribution>();

      EventUtil.getEvents(eventDao, us, user, firstDay, lastDay, eventsArray);

      for (int i = 1; i <= length; i++) {

        List<Event> eventsList = eventsArray[i];
        if ((eventsList != null) && !eventsList.isEmpty()) {
          EventDistribution ed = eventsMap.get(String.valueOf(i));
          if (ed == null) {
            Calendar now = Util.newCalendar(today.getTimeZone(), year, month, i);
            ed = new EventDistribution(now);
            eventsMap.put(String.valueOf(i), ed);
          }

          for (Event ev : eventsList) {
            ed.addEvent(ev);
          }
        }
      }

      for (Iterator it = eventsMap.values().iterator(); it.hasNext();) {
        EventDistribution ed = (EventDistribution)it.next();
        ed.compact();
      }

      userEvents.put(us.getId().toString(), eventsMap);

      UserEvents ue = new UserEvents();
      ue.setId(us.getId());

      String[] tooltips = new String[length + 1];
      for (int i = 1; i <= length; i++) {
        List<Event> eventList = eventsArray[i];
        if (eventList != null) {
          Collections.sort(eventList, eventComparator);
          tooltips[i] = EventUtil.getTooltip(user, eventList, today.getTimeZone(), translationService, locale);
        }
      }

      ue.setTooltip(tooltips);

      if (us.getShortName() != null) {
        ue.setName(us.getShortName());
      } else {
        ue.setName(us.getFirstName() + " " + us.getName());
      }

      userList.add(ue);

    }
    monthForm.setUsers(userList);
    monthForm.setUserEvents(userEvents);

    String height = userConfig.getStringProperty(UserConfig.OVERVIEW_PIC_HEIGHT, "26");
    String width = userConfig.getStringProperty(UserConfig.OVERVIEW_PIC_WIDTH, "26");

    session.setAttribute("height", height);
    session.setAttribute("width", width);

    return mapping.getInputForward();
  }

}
