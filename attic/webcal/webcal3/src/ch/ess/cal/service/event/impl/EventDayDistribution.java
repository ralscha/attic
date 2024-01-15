package ch.ess.cal.service.event.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

import ch.ess.cal.model.Event;

public class EventDayDistribution implements Serializable {
  private List<EventDayDistributionItem> events;
  private List<Event> allDayEvents;

  private Calendar cal;

  public EventDayDistribution(Calendar cal) {
    this.cal = cal;

    events = new ArrayList<EventDayDistributionItem>();
    allDayEvents = new ArrayList<Event>();
  }

  public void addEvent(Event event) {

    if (event.isAllDay()) {
      allDayEvents.add(event);
    } else {
      EventDayDistributionItem edi = new EventDayDistributionItem(event, cal);
      events.add(edi);
    }
  }

  public List<EventDayDistributionItem> getEvents() {
    return events;
  }

  public List<Event> getAlldayEvents() {
    return allDayEvents;
  }

  public void compact() {
    if (events.size() > 1) {
      for (int i = 1; i < events.size(); i++) {
        EventDayDistributionItem bt = events.get(i);

        for (int j = 0; j < i; j++) {
          EventDayDistributionItem btc = events.get(j);
          if (btc.isFree()) {
            if (hasSpace(btc.getArray(), bt.getArray())) {

              Integer[] ca = bt.getArray();
              for (int n = 0; n < ca.length; n++) {
                if (ca[n] != null) {
                  btc.getArray()[n] = bt.getEvent().getId();
                }
              }
              bt.clear();
              break;
            }
          }
        }
      }
    }

    for (ListIterator<EventDayDistributionItem> it = events.listIterator(); it.hasNext();) {
      EventDayDistributionItem edi = it.next();
      if (!edi.isFree()) {
        it.remove();
      }

    }
  }

  private boolean hasSpace(Integer[] cont, Integer[] check) {
    for (int i = 0; i < check.length; i++) {
      if ((check[i] != null) && (cont[i] != null)) {
        return false;
      }
    }
    return true;
  }

}