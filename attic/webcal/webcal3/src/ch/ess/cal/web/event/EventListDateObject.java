package ch.ess.cal.web.event;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author sr
 */
public class EventListDateObject implements Comparable<EventListDateObject>, Serializable {

  private Calendar calendar;
  private boolean allDay;

  public EventListDateObject(final Calendar cal, final boolean flag) {
    this.calendar = cal;
    this.allDay = flag;
  }

  public boolean isAllDay() {
    return allDay;
  }

  public void setAllDay(final boolean allDay) {
    this.allDay = allDay;
  }

  public Calendar getCalendar() {
    return calendar;
  }

  public void setCalendar(final Calendar calendar) {
    this.calendar = calendar;
  }

  public int compareTo(EventListDateObject o) {
    return calendar.compareTo(o.getCalendar());
  }
}
