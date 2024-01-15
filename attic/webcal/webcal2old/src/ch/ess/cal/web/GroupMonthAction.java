package ch.ess.cal.web;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Session;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.util.Calendar;

import ch.ess.cal.Constants;
import ch.ess.cal.common.HibernateAction;
import ch.ess.cal.resource.HolidayRegistry;
import ch.ess.cal.util.Util;

public class GroupMonthAction extends HibernateAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMessages messages,
    Session sess)
    throws Exception {
      
      GroupMonthForm monthForm = (GroupMonthForm)form;
      
      CalUser calUser = getUser(request);      
      Calendar today = calUser.getTodayCalendar();
      
      
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
      Map result = HolidayRegistry.getMonthHolidays(today);
      
      for (Iterator iter = result.keySet().iterator(); iter.hasNext();) {                
        Integer element = (Integer)iter.next();        
        String h = (String)result.get(element);
        h = getResources(request).getMessage(calUser.getLocale(), h);
        h = Util.javascriptEscape(h);
        holidays[element.intValue()-1] = h;              
      }
      monthForm.setHolidays(holidays);
      

      //Year
      monthForm.setYear(today.get(Calendar.YEAR));
      
      //Month
      monthForm.setMonth(today.get(Calendar.MONTH));

      //Monthnames      
      DateFormatSymbols symbols = new DateFormatSymbols(calUser.getLocale());
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
      
      monthForm.setWeekdayNames(weekdayNames);
      
      if (colspan > 0) {
        monthForm.getWeekColspan().add(new Integer(colspan));
        monthForm.getWeekNos().add(new Integer(mweekno));
      }            
      
      request.getSession().setAttribute("monthBean", monthForm);
      
      return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

}
