package ch.ess.cal.web.view;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ch.ess.cal.CalUtil;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.service.EventUtil;

import com.cc.framework.ui.model.imp.AppointmentImp;

public class FixedAppointmentImp extends AppointmentImp {

  private Recurrence recurrence;
  private TimeZone timezone;
  
  public void setRecurrence(Recurrence recurrence) {
    this.recurrence = recurrence;
  }
  public void setTimezone(TimeZone timezone) {
    this.timezone = timezone;
  }


  @Override
  public boolean match(Calendar calendar) {
   
    if(isRecurring()) {   
      int day = calendar.get(Calendar.DATE);
      int month = calendar.get(Calendar.MONTH);
      int year = calendar.get(Calendar.YEAR);
      
      Calendar start = CalUtil.newCalendar(timezone, year, month, day);
      Calendar end = CalUtil.newCalendar(timezone, year, month, day, 23, 59, 59, 999);
      
      List<Calendar> days = EventUtil.getDaysBetween(recurrence, start, end, timezone);
      return !days.isEmpty();
    }
    
    if(isAllDayEvent() && getEndTime() == null) {
      return CalUtil.isSameDay(calendar, getStartTime());
    }
    
    return calendar.getTimeInMillis() >= getStartTime().getTimeInMillis() && calendar.getTimeInMillis() <= getEndTime().getTimeInMillis();
    
  }

}
