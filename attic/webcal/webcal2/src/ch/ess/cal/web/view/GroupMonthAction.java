package ch.ess.cal.web.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.Constants;
import ch.ess.cal.db.Department;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.Recurrence;
import ch.ess.cal.db.User;
import ch.ess.cal.db.UserComparator;
import ch.ess.cal.event.EventDistribution;
import ch.ess.cal.event.EventUtil;
import ch.ess.cal.resource.HolidayRegistry;
import ch.ess.cal.resource.UserConfig;
import ch.ess.cal.web.WebUtils;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;

import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.util.Calendar;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * 
  * @struts.action path="/groupMonth" name="groupMonthForm" input=".group.month" scope="session" validate="true"  
  * @struts.action-forward name="success" path=".group.month"
  */
public class GroupMonthAction extends HibernateAction {

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    GroupMonthForm monthForm = (GroupMonthForm)ctx.getForm();

    User user = WebUtils.getUser(ctx.getRequest());
    UserConfig config = UserConfig.getUserConfig(user);

    Locale locale = getLocale(ctx.getRequest());

    Calendar today = EventUtil.getTodayCalendar(user, config);

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

    //Holidays      
    String[] holidays = new String[length];
    Map result = HolidayRegistry.getMonthHolidays(locale, today);

    for (Iterator iter = result.entrySet().iterator(); iter.hasNext();) {
      Map.Entry entry = (Map.Entry)iter.next();

      Integer element = (Integer)entry.getKey();
      String h = (String)entry.getValue();

      h = Util.javascriptEscape(h);
      holidays[element.intValue() - 1] = h;
    }
    monthForm.setHolidays(holidays);

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

    String[] weekdayNames = new String[length];
    for (int i = 0; i < length; i++) {

      int weekday = today.get(Calendar.DAY_OF_WEEK);
      int weekno = today.get(Calendar.WEEK_OF_YEAR);

      if (weekno == mweekno) {
        colspan++;
      } else {
        monthForm.getWeekColspan().add(new Integer(colspan));
        monthForm.getWeekNos().add(new Integer(mweekno));
        mweekno = weekno;
        colspan = 1;
      }

      weekdayNames[i] = shortWeekdays[weekday];

      today.add(Calendar.DATE, +1);

    }

    today.add(Calendar.DATE, -1);

    monthForm.setWeekdayNames(weekdayNames);

    if (colspan > 0) {
      monthForm.getWeekColspan().add(new Integer(colspan));
      monthForm.getWeekNos().add(new Integer(mweekno));
    }

    //Users

    Long departmentId = monthForm.getDepartmentId();
    Department dep = null;
    Set usersSet = new TreeSet(new UserComparator());

    if (departmentId == null) {
      dep = (Department)user.getDepartments().iterator().next();
      if (dep == null) {
        dep = (Department)user.getAccessDepartments().iterator().next();
      }
    } else {
      dep = Department.load(departmentId);
    }

    if (dep != null) {
      usersSet.addAll(dep.getUsers());
    } else {
      usersSet.add(user);
    }

    int year = today.get(Calendar.YEAR);
    int month = today.get(Calendar.MONTH);
    int lastday = today.getActualMaximum(Calendar.DAY_OF_MONTH);

    Calendar firstDay = Util.newCalendar(today.getTimeZone(), year, month, 1, 0, 0, 0, 0);
    Calendar lastDay = Util.newCalendar(today.getTimeZone(), year, month, lastday, 23, 59, 59, 999);

    Map userEvents = new HashMap();
    List userList = new ArrayList();
    Iterator resultIt = usersSet.iterator();
    while (resultIt.hasNext()) {
      User u = (User)resultIt.next();

      boolean[] hasEvents = new boolean[length];

      Arrays.fill(hasEvents, false);

      Map eventsMap = null;

      //Start Normal Events
      List events = Event.getMonthUserNormalEvents(u, firstDay, lastDay);
      if (!events.isEmpty()) {
        eventsMap = new HashMap();
      }

      for (Iterator it = events.iterator(); it.hasNext();) {
        Event ev = (Event)it.next();

        Calendar start = Util.utcLong2UserCalendar(ev.getStartDate(), today.getTimeZone());
        Calendar end = Util.utcLong2UserCalendar(ev.getEndDate(), today.getTimeZone());

        if (start.getTimeInMillis() < firstDay.getTimeInMillis()) {
          start = firstDay;
        }

        if (end.getTimeInMillis() > lastDay.getTimeInMillis()) {
          end = lastDay;
        }

        int day = start.get(Calendar.DATE);
        do {

          EventDistribution ed = (EventDistribution)eventsMap.get(String.valueOf(day));
          if (ed == null) {

            Calendar now = Util.newCalendar(today.getTimeZone(), year, month, day, 0, 0, 0, 0);
            ed = new EventDistribution(now);
            eventsMap.put(String.valueOf(day), ed);
            hasEvents[day - 1] = true;
          }

          ed.addEvent(ev);

          day++;
        } while (day <= end.get(Calendar.DATE));

      }
      //End Normal Events

      //Start Recur Events
      events = Event.getMonthUserRecurEvents(u, firstDay, lastDay);
      if (!events.isEmpty()) {
        if (eventsMap == null) {
          eventsMap = new HashMap();
        }
      }
      for (Iterator it = events.iterator(); it.hasNext();) {
        Object[] obj = (Object[])it.next();

        Event ev = ((Event)obj[0]).miniCopy();
        Recurrence recur = (Recurrence)obj[1];
        List dates = EventUtil.getDaysBetween(recur.getRfcRule(), recur, firstDay, lastDay);

        for (Iterator it2 = dates.iterator(); it2.hasNext();) {
          Calendar startRecur = (Calendar)it2.next();
          System.out.println(Constants.DATE_LOCAL_FORMAT.format(startRecur));

          Calendar endRecur = (Calendar)startRecur.clone();
          endRecur.add(Calendar.MINUTE, recur.getDuration().intValue());

          if (!ev.isAllDay()) {
            Calendar startTime = Util.utcLong2UserCalendar(recur.getStartTime().longValue(), ev.getTimeZoneObj());
            Calendar endTime = Util.utcLong2UserCalendar(recur.getEndTime().longValue(), ev.getTimeZoneObj());
            startRecur.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
            startRecur.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
            endRecur.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
            endRecur.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
          }

          ev.setStartDate(startRecur.getTimeInMillis());
          ev.setEndDate(endRecur.getTimeInMillis());

          int day = startRecur.get(Calendar.DATE);
          do {

            EventDistribution ed = (EventDistribution)eventsMap.get(String.valueOf(day));
            if (ed == null) {
              Calendar now = Util.newCalendar(today.getTimeZone(), year, month, day, 0, 0, 0, 0);
              ed = new EventDistribution(now);
              eventsMap.put(String.valueOf(day), ed);
              hasEvents[day - 1] = true;
            }

            ed.addEvent(ev);

            day++;

          } while (day <= endRecur.get(Calendar.DATE));

        }

      }
      //End Recur Events    

      if (eventsMap != null) {
        for (Iterator it = eventsMap.values().iterator(); it.hasNext();) {
          EventDistribution ed = (EventDistribution)it.next();
          ed.compact();
        }

        userEvents.put(u.getId().toString(), eventsMap);
      }

      UserEvents ue = new UserEvents();
      ue.setId(u.getId());
      ue.setHasEvents(hasEvents);

      if (u.getShortName() != null) {
        ue.setName(u.getShortName());
      } else {
        ue.setName(u.getFirstName() + " " + u.getName());
      }

      userList.add(ue);

    }
    monthForm.setUsers(userList);
    monthForm.setUserEvents(userEvents);

    String height = config.getProperty(UserConfig.OVERVIEW_PIC_HEIGHT, "26");
    String width = config.getProperty(UserConfig.OVERVIEW_PIC_WIDTH, "24");
    HttpSession session = ctx.getSession();
    
    session.setAttribute("height", height);
    session.setAttribute("width", width);

    return findForward(ch.ess.common.Constants.FORWARD_SUCCESS);
  }

}
