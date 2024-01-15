package ch.ess.cal.web.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Event;
import ch.ess.cal.db.Recurrence;
import ch.ess.cal.db.User;
import ch.ess.cal.event.EventDayDistribution;
import ch.ess.cal.event.EventDayDistributionItem;
import ch.ess.cal.web.WebUtils;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * 
  * @struts.action path="/day" name="dayForm" input=".day" scope="session" validate="false"  
  * @struts.action-forward name="success" path=".day"
  */
public class DayAction extends HibernateAction {

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    DayForm dayForm = (DayForm)ctx.getForm();

    if (dayForm.getDay() == 0) {
      Calendar today = new GregorianCalendar();
      dayForm.setDay(today.get(Calendar.DATE));
      dayForm.setMonth(today.get(Calendar.MONTH));
      dayForm.setYear(today.get(Calendar.YEAR));
    } else if ((dayForm.getMonth() == 0) || (dayForm.getYear() == 0)) {
      //from overview
      GroupMonthForm monthForm = (GroupMonthForm)ctx.getSession().getAttribute("groupMonthForm");
      dayForm.setMonth(monthForm.getMonth());
      dayForm.setYear(monthForm.getYear());
    }

    Locale locale = getLocale(ctx.getRequest());

    User user = WebUtils.getUser(ctx.getRequest());
    //UserConfig config = UserConfig.getUserConfig(user);

    DateFormatSymbols symbols = new DateFormatSymbols(locale);
    dayForm.setMonthName(symbols.getMonths()[dayForm.getMonth()]);

    dayForm.setDay(dayForm.getDay() + 1);

    Calendar thisDay = new GregorianCalendar(user.getTimeZoneObj());
    thisDay.set(Calendar.YEAR, dayForm.getYear());
    thisDay.set(Calendar.MONTH, dayForm.getMonth());
    thisDay.set(Calendar.DATE, dayForm.getDay());

    DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG, locale);
    dayForm.setDayString(formatter.format(thisDay));

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
    
    
    EventDayDistribution ed = getDayEvents(user, thisDay);
    
    if (!ed.getEvents().isEmpty()) {
    
      String[][] distribution = new String[96][ed.getEvents().size()];
      int[][] colspan = new int[96][ed.getEvents().size()];
      int[][] rowspan = new int[ed.getEvents().size()][96];
      
      for (int i = 0; i < ed.getEvents().size(); i++) {
        
        Long[] arr = ((EventDayDistributionItem)ed.getEvents().get(i)).getArray();
        for (int j = 0; j < arr.length; j++) {
          if (arr[j] != null) {
            distribution[j][i] = "hallo";
          } else {
            distribution[j][i] = null;
          }
        }
      }
      
      for (int i = 0; i < distribution.length; i++) {        
        colspan[i] = getSpan(distribution[i]);        
      }
      for (int i = 0; i < distribution[0].length; i++) {
        String[] dcopy = new String[96];
        for (int j = 0; j < dcopy.length; j++) {
          dcopy[j] = distribution[j][i];  
        }
         
        rowspan[i] = getNotEmptySpan(dcopy);
      }
      
      dayForm.setRowspan(rowspan);
      dayForm.setColspan(colspan);
      dayForm.setCols(ed.getEvents().size()-1);
      dayForm.setDistribution(distribution);


    } else {
      dayForm.setCols(0);
      dayForm.setDistribution(new String[96][0]);
    }

    
    /*
    List eventDayDistributionItems = ed.getEvents();
    for (Iterator it = eventDayDistributionItems.iterator(); it.hasNext();) {
      EventDayDistributionItem element = (EventDayDistributionItem)it.next();
      
      System.out.println(element);
    }
    */
    
    
    
    List events = ed.getAlldayEvents();
    List alldayEvents = new ArrayList();
    
    for (Iterator it = events.iterator(); it.hasNext();) {
      Event ev = (Event)it.next();
      
      String startDate = formatter.format(Util.utcLong2UserCalendar(ev.getStartDate(), ev.getTimeZoneObj()));
      
      String endDate = "";
      ev = Event.load(ev.getId());
      
      endDate = formatter.format(Util.utcLong2UserCalendar(ev.getEndDate(), ev.getTimeZoneObj()));
      if (!ev.getRecurrences().isEmpty()) {
        Recurrence recur = (Recurrence)ev.getRecurrences().iterator().next();
        if (recur.isAlways()) {
          endDate = "";
        } 
        
      }
      
      alldayEvents.add(startDate + " - " + endDate + " " + ev.getSubject());
    }
    
    dayForm.setAllDayEvents(alldayEvents);
    
    

    return findForward(ch.ess.common.Constants.FORWARD_SUCCESS);
  }
  
  private static int[] getNotEmptySpan(Object[] l) {
    int[] span = new int[l.length];
    Arrays.fill(span, 0);
    
    for (int i = 0; i < l.length; i++) {
      if (l[i] == null) {
        span[i] = 1;
        
      } else {
        int j = i + 1;
        
        int count = 1;
        while (j < l.length) {          
          if ((l[j] != null) && (l[j].equals(l[i]))) {

            count++;
            j++;
          } else {              
            break;
          }
        }
        span[i] = count;
        i = j-1;
      }
    }
    
    
    return span;
  }  
  
  private int[] getSpan(Object[] l) {
    
    int[] span = new int[l.length];
    Arrays.fill(span, 0);

    for (int i = 0; i < l.length; i++) {
      if (l[i] != null) {
        span[i] = 1;
        
      } else {
        int j = i + 1;
        
        int count = 1;
        while (j < l.length) {          
          if (l[j] == null) {

            count++;
            j++;
          } else {              
            break;
          }
        }
        span[i] = count;
        i = j-1;
      }
    }
    
    return span;
  }  

  private EventDayDistribution getDayEvents(User u, Calendar thisDay) throws HibernateException {

    //Normal Events
    List events = Event.getTodayUserNormalEvents(u, thisDay);
    EventDayDistribution ed = new EventDayDistribution(thisDay);
    for (Iterator it = events.iterator(); it.hasNext();) {
      Event ev = (Event)it.next();
      ed.addEvent(ev);
    }

    //Recur Events

    events = Event.getTodayUserRecurEvents(u, thisDay);

    for (Iterator it = events.iterator(); it.hasNext();) {
      Event ev = (Event)it.next();
      ed.addEvent(ev);
    }

    ed.compact();
    return ed;

  }
}
