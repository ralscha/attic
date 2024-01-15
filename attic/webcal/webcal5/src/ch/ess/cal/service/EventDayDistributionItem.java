package ch.ess.cal.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ch.ess.cal.model.Event;

public class EventDayDistributionItem implements Serializable {

  private Integer[] quarterArray;
  private Event event;

  public EventDayDistributionItem(Event event, Calendar cal) {

    this.event = event;

    quarterArray = new Integer[96];

    Calendar start = new GregorianCalendar(cal.getTimeZone());
    Calendar end = new GregorianCalendar(cal.getTimeZone());
    start.setTimeInMillis(event.getStartDate());
    end.setTimeInMillis(event.getEndDate());

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
      quarterArray[i] = event.getId();
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

  public Integer[] getArray() {
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
