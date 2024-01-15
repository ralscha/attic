package ch.ess.cal.service.event.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ch.ess.cal.model.Category;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;

public class EventDistributionItem implements Serializable {

  private String colour;
  private String[] colourArray;

  public EventDistributionItem(Event event, Calendar cal) {

    EventCategory eventCategory = event.getEventCategories().iterator().next();
    Category category = eventCategory.getCategory();
    colour = category.getColour();
    colourArray = new String[24];

    if (event.isAllDay()) {
      Arrays.fill(colourArray, colour);
    } else {
      Calendar start = new GregorianCalendar(cal.getTimeZone());
      Calendar end = new GregorianCalendar(cal.getTimeZone());
      start.setTimeInMillis(event.getStartDate());
      end.setTimeInMillis(event.getEndDate());

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
        colourArray[i] = colour;
      }

    }

  }

  private boolean sameDay(Calendar cal1, Calendar cal2) {
    return ((cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH))
        && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) && (cal1.get(Calendar.YEAR) == cal2
        .get(Calendar.YEAR)));
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public String[] getArray() {
    return colourArray;
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
