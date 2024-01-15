package ch.ess.cal.web.view;

import java.util.Comparator;

import ch.ess.cal.model.Event;

public class EventComparator implements Comparator<Event> {

  public int compare(Event event1, Event event2) {

    if (event1.getStartDate() < event2.getStartDate()) {
      return -1;
    } else if (event1.getStartDate() > event2.getStartDate()) {
      return 1;
    }
    return 0;
  }

}