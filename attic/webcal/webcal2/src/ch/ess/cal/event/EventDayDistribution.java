package ch.ess.cal.event;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import ch.ess.cal.db.Event;

import com.ibm.icu.util.Calendar;

public class EventDayDistribution {
  private List events;
  private List allDayEvents;
  
  private Calendar cal;

  public EventDayDistribution(Calendar cal) {
    this.cal = cal;

    events = new ArrayList();
    allDayEvents = new ArrayList();
  }

  public void addEvent(Event e) {
    
    if (e.isAllDay()) {
      allDayEvents.add(e);
    } else {    
      EventDayDistributionItem edi = new EventDayDistributionItem(e, cal);
      events.add(edi);
    }
  }

  public List getEvents() {
    return events;
  }
  
  public List getAlldayEvents() {
    return allDayEvents;
  }

  public void compact() {
    if (events.size() > 1) {
      for (int i = 1; i < events.size(); i++) {
        EventDayDistributionItem bt = (EventDayDistributionItem)events.get(i);

        for (int j = 0; j < i; j++) {
          EventDayDistributionItem btc = (EventDayDistributionItem)events.get(j);
          if (btc.isFree()) {
            if (hasSpace(btc.getArray(), bt.getArray())) {

              Long[] ca = bt.getArray();
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

    for (ListIterator it = events.listIterator(); it.hasNext(); ) {
      EventDayDistributionItem edi = (EventDayDistributionItem)it.next();
      if (!edi.isFree()) {
        it.remove();
      }

    }
  }

  private boolean hasSpace(Long[] cont, Long[] check) {
    for (int i = 0; i < check.length; i++) {
      if ((check[i] != null) && (cont[i] != null)) {
        return false;
      }
    }
    return true;
  }

}