package ch.ess.cal.event;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import ch.ess.cal.db.Event;

import com.ibm.icu.util.Calendar;

public class EventDistribution {
  private List events;
  private Calendar cal;

  public EventDistribution(Calendar cal) {
    this.cal = cal;

    events = new ArrayList();
  }

  public void addEvent(Event e) {
    EventDistributionItem edi = new EventDistributionItem(e, cal);
    events.add(edi);
  }

  public List getEvents() {
    return events;
  }

  public void compact() {
    if (events.size() > 1) {
      for (int i = 1; i < events.size(); i++) {
        EventDistributionItem bt = (EventDistributionItem)events.get(i);

        for (int j = 0; j < i; j++) {
          EventDistributionItem btc = (EventDistributionItem)events.get(j);
          if (btc.isFree()) {
            if (hasSpace(btc.getArray(), bt.getArray())) {

              String colour = bt.getColour();

              String[] ca = bt.getArray();
              for (int n = 0; n < ca.length; n++) {
                if (ca[n] != null) {
                  btc.getArray()[n] = colour;
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
      EventDistributionItem edi = (EventDistributionItem)it.next();
      if (!edi.isFree()) {
        it.remove();
      }

    }
  }

  private boolean hasSpace(String[] cont, String[] check) {
    for (int i = 0; i < check.length; i++) {
      if ((check[i] != null) && (cont[i] != null)) {
        return false;
      }
    }
    return true;
  }

}