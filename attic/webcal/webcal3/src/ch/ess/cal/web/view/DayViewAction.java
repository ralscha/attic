package ch.ess.cal.web.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.Util;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.EventDao;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.event.impl.EventDayDistribution;
import ch.ess.cal.service.event.impl.EventUtil;
import ch.ess.cal.service.impl.TranslationService;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/04/04 11:31:11 $ 
 * 
 * @struts.action path="/dayView" name="dayViewForm" roles="all" input="/dayview.jsp" scope="session" validate="false"  
 * 
 * @spring.bean name="/dayView" lazy-init="true" 
 */
public class DayViewAction extends Action {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private MonthBeanUtil monthBeanUtil;
  private EventDao eventDao;
  private TranslationService translationService;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * @spring.property reflocal="userConfigurationDao" 
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  /**
   * @spring.property reflocal="monthBeanUtil" 
   */
  public void setMonthBeanUtil(MonthBeanUtil monthBeanUtil) {
    this.monthBeanUtil = monthBeanUtil;
  }

  /**
   * @spring.property reflocal="eventDao" 
   */
  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(final TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DayViewForm dayForm = (DayViewForm)form;

    HttpSession session = request.getSession();

    User user = Util.getUser(session, userDao);
    Config config = userConfigurationDao.getUserConfig(user);

    Locale locale = getLocale(request);

    MessageResources messages = getResources(request);

    Calendar thisDay = EventUtil.getTodayCalendar(user, config);

    if (dayForm.getInputMonth() != null) {
      thisDay.set(Calendar.MONTH, Integer.parseInt(dayForm.getInputMonth()));
    }

    if (dayForm.getInputYear() != null) {
      thisDay.set(Calendar.YEAR, Integer.parseInt(dayForm.getInputYear()));
    }

    if (dayForm.getInputDate() != null) {
      thisDay.set(Calendar.DATE, Integer.parseInt(dayForm.getInputDate()));
    }

    dayForm.setDayString(Util.userCalendar2String(thisDay));

    EventDayDistribution ed = getDayEvents(user, thisDay);

    String[] hours = new String[24];
    Arrays.fill(hours, "");

    for (int i = 0; i < hours.length; i++) {
      if (i < 10) {
        hours[i] += "0";
      }
      hours[i] += i;
      hours[i] += ":00";
    }
    dayForm.setHours(hours);

    int month = thisDay.get(Calendar.MONTH);
    int year = thisDay.get(Calendar.YEAR);

    MonthBean[] monthBeanArray = new MonthBean[3];

    MonthBean monthBean = monthBeanUtil.createMonthBean(locale, user, user, month, year, false);
    monthBeanArray[1] = monthBean;

    thisDay.add(Calendar.MONTH, -1);
    monthBean = monthBeanUtil.createMonthBean(locale, user, user, thisDay.get(Calendar.MONTH), thisDay
        .get(Calendar.YEAR), false);
    monthBeanArray[0] = monthBean;

    thisDay.add(Calendar.MONTH, +2);
    monthBean = monthBeanUtil.createMonthBean(locale, user, user, thisDay.get(Calendar.MONTH), thisDay
        .get(Calendar.YEAR), false);
    monthBeanArray[2] = monthBean;

    dayForm.setMonthBean(monthBeanArray);

    //Events

    if (!ed.getEvents().isEmpty()) {

      String[][] distribution = new String[96][ed.getEvents().size()];
      int[][] colspan = new int[96][ed.getEvents().size()];
      int[][] rowspan = new int[ed.getEvents().size()][96];

      for (int i = 0; i < ed.getEvents().size(); i++) {

        Integer[] arr = (ed.getEvents().get(i)).getArray();
        for (int j = 0; j < arr.length; j++) {
          if (arr[j] != null) {
            distribution[j][i] = "hallo";
          } else {
            distribution[j][i] = null;
          }
        }
      }

      //      for (int i = 0; i < distribution.length; i++) {        
      //        colspan[i] = getSpan(distribution[i]);        
      //      }
      //      for (int i = 0; i < distribution[0].length; i++) {
      //        String[] dcopy = new String[96];
      //        for (int j = 0; j < dcopy.length; j++) {
      //          dcopy[j] = distribution[j][i];  
      //        }
      //         
      //        rowspan[i] = getNotEmptySpan(dcopy);
      //      }
      //      
      //      dayForm.setRowspan(rowspan);
      //      dayForm.setColspan(colspan);
      //      dayForm.setCols(ed.getEvents().size()-1);
      //      dayForm.setDistribution(distribution);

    } else {
      //      dayForm.setCols(0);
      //      dayForm.setDistribution(new String[96][0]);
    }

    List<Event> allDayEvents = ed.getAlldayEvents();
    List<DayAllDayDescription> allDayEventDescr = new ArrayList<DayAllDayDescription>();

    for (Event event : allDayEvents) {

      DayAllDayDescription descr = new DayAllDayDescription();

      Calendar startCal = Util.utcLong2UserCalendar(event.getStartDate(), user.getTimeZone());
      Calendar endCal = Util.utcLong2UserCalendar(event.getEndDate(), user.getTimeZone());

      if (!Util.isSameDay(startCal, endCal)) {
        descr.setTime(Util.userCalendar2String(startCal) + "-" + Util.userCalendar2String(endCal));
      }

      if ((event.getSensitivity() != SensitivityEnum.PRIVATE)
          || ((event.getSensitivity() == SensitivityEnum.PRIVATE) && EventUtil.isMyEvent(user, event))) {

        descr.setSubject(event.getSubject());
        descr.setLocation(event.getLocation());

      } else {
        //todo translate
        descr.setSubject("private");
      }

      EventCategory eventCategory = event.getEventCategories().iterator().next();
      descr.setCategory(translationService.getText(eventCategory.getCategory(), locale));
      descr.setColour(eventCategory.getCategory().getColour());

      if (event.getReminders().size() > 0) {
        descr.setReminder(EventUtil.getReminderTooltip(messages, locale, event.getReminders()));
      } else {
        descr.setReminder(null);
      }

      if (event.getRecurrences().size() > 0) {
        String tooltip = "<span class=\\'smalltext\\'>";
        tooltip += EventUtil.getRecurrenceDescription(messages, locale, event.getRecurrences().iterator().next());
        tooltip += "</span>";
        descr.setRecurrence(tooltip);
      } else {
        descr.setRecurrence(null);
      }

      allDayEventDescr.add(descr);

    }

    dayForm.setAllDayEvents(allDayEventDescr);

    return mapping.getInputForward();

  }

  //  private static int[] getNotEmptySpan(Object[] l) {
  //    int[] span = new int[l.length];
  //    Arrays.fill(span, 0);
  //    
  //    for (int i = 0; i < l.length; i++) {
  //      if (l[i] == null) {
  //        span[i] = 1;
  //        
  //      } else {
  //        int j = i + 1;
  //        
  //        int count = 1;
  //        while (j < l.length) {          
  //          if ((l[j] != null) && (l[j].equals(l[i]))) {
  //
  //            count++;
  //            j++;
  //          } else {              
  //            break;
  //          }
  //        }
  //        span[i] = count;
  //        i = j-1;
  //      }
  //    }
  //    
  //    
  //    return span;
  //  }  
  //  
  //  private int[] getSpan(Object[] l) {
  //    
  //    int[] span = new int[l.length];
  //    Arrays.fill(span, 0);
  //
  //    for (int i = 0; i < l.length; i++) {
  //      if (l[i] != null) {
  //        span[i] = 1;
  //        
  //      } else {
  //        int j = i + 1;
  //        
  //        int count = 1;
  //        while (j < l.length) {          
  //          if (l[j] == null) {
  //
  //            count++;
  //            j++;
  //          } else {              
  //            break;
  //          }
  //        }
  //        span[i] = count;
  //        i = j-1;
  //      }
  //    }
  //    
  //    return span;
  //  } 

  private EventDayDistribution getDayEvents(User user, Calendar thisDay) {

    Calendar thisDayClone = (Calendar)thisDay.clone();
    thisDayClone.set(Calendar.HOUR_OF_DAY, 0);
    thisDayClone.set(Calendar.MINUTE, 0);
    thisDayClone.set(Calendar.SECOND, 0);
    thisDayClone.set(Calendar.MILLISECOND, 0);

    Calendar start = (Calendar)thisDayClone.clone();

    thisDayClone.set(Calendar.HOUR_OF_DAY, 23);
    thisDayClone.set(Calendar.MINUTE, 59);
    thisDayClone.set(Calendar.SECOND, 59);
    thisDayClone.set(Calendar.MILLISECOND, 999);

    Calendar end = (Calendar)thisDayClone.clone();

    //Normal Events
    List<Event> events = eventDao.getUserNormalEvents(user, start, end);
    EventDayDistribution ed = new EventDayDistribution(thisDay);
    for (Event event : events) {
      ed.addEvent(event);
    }

    List eventsWithRecur = eventDao.getUserRecurEvents(user, start, end);

    for (Iterator it = eventsWithRecur.iterator(); it.hasNext();) {
      Object[] obj = (Object[])it.next();
      Event ev = (Event)obj[0];
      ed.addEvent(ev);
    }

    ed.compact();
    return ed;

  }
}
