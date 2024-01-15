package ch.ess.cal.event;

import java.util.Arrays;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ch.ess.cal.db.Category;
import ch.ess.cal.db.Event;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

public class EventDistributionItem {

  private String colour;
  private String[] colourArr;
  
  public EventDistributionItem(Event e, Calendar cal) {
    
    Category cat = (Category)e.getCategories().iterator().next();
    colour = cat.getColour();        
    colourArr = new String[24];
    
    if (e.isAllDay()) {      
      Arrays.fill(colourArr, colour);
    } else {
      Calendar start = new GregorianCalendar(cal.getTimeZone());    
      Calendar end = new GregorianCalendar(cal.getTimeZone());
      start.setTimeInMillis(e.getStartDate());
      end.setTimeInMillis(e.getEndDate());
      
      int startH, endH;
      
      if (!sameDay(start, end)) {
        if (sameDay(cal, start)) {
          startH = start.get(Calendar.HOUR_OF_DAY);
          endH = 23;
        } else if (sameDay(cal, end)) {
          startH = 0;
          endH = end.get(Calendar.HOUR_OF_DAY);
          int endM = end.get(Calendar.MINUTE);
          if (endM == 0) {
            endH--;
          }
        } else {
          startH = 0;
          endH = 23;
        }
                
      } else {
      
        startH = start.get(Calendar.HOUR_OF_DAY);
        endH = end.get(Calendar.HOUR_OF_DAY);
        int endM = end.get(Calendar.MINUTE);
        if (endM == 0) {
          endH--;
        }
      }
      
      for (int i = startH; i <= endH; i++) {        
        colourArr[i] = colour;
      }
      
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

  public String[] getArray() {
    return colourArr;
  }
  
  public String getColour() {
    return colour;
  }

  public void clear() {
    colour = null;
  }
  
  public boolean isFree() {
    return colour != null;
  }



}
