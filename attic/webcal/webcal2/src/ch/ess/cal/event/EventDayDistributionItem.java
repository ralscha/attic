package ch.ess.cal.event;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ch.ess.cal.db.Event;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

public class EventDayDistributionItem {
  
  private Long[] quarterArray;
  private Event event;
  
  public EventDayDistributionItem(Event e, Calendar cal) {
    
    this.event = e;
    
    quarterArray = new Long[96];
    
   
    Calendar start = new GregorianCalendar(cal.getTimeZone());    
    Calendar end = new GregorianCalendar(cal.getTimeZone());
    start.setTimeInMillis(e.getStartDate());
    end.setTimeInMillis(e.getEndDate());
    
    int startH, endH;
    int startM, endM;
    
    if (!sameDay(start, end)) {
      if (sameDay(cal, start)) {
        startH = start.get(Calendar.HOUR_OF_DAY);
        startM = start.get(Calendar.MINUTE);
        endH = 23;
        endM = 59;
      } else if (sameDay(cal, end)) {
        startH = 0;
        startM = 0;
        endH = end.get(Calendar.HOUR_OF_DAY);
        endM = end.get(Calendar.MINUTE);
      } else {
        startH = 0;
        startM = 0;
        endH = 23;
        endM = 59;
      }
              
    } else {
    
      startH = start.get(Calendar.HOUR_OF_DAY);
      startM = start.get(Calendar.MINUTE);
      endH = end.get(Calendar.HOUR_OF_DAY);
      endM = end.get(Calendar.MINUTE);
    }
    
 
    int startPos = startH * 4;
    if (startM < 15) {
      //no action
    } else if (startM < 30) {
      startPos += 1;
    } else if (startM < 45) {
      startPos += 2;
    } else {
      startPos += 3;
    }
    
    int endPos = endH * 4;
    if (endM == 0) {
      endPos--;
    } else if (endM < 15) {
      //no action
    } else if (endM < 30) {
      endPos += 1;
    } else if (endM < 45) {
      endPos += 2;
    } else {
      endPos += 3;
    }      

    for (int i = startPos; i <= endPos; i++) {
      quarterArray[i] = e.getId();
    }
    
  }

  private boolean sameDay(Calendar cal1, Calendar cal2) {
    return ( (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) &&
    (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) &&
    (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)));
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public Long[] getArray() {
    return quarterArray;
  }
  
  public Event getEvent() {
    return event;
  }

  public void clear() {
    event = null;
  }
  
  public boolean isFree() {
    return event != null;
  }



}
