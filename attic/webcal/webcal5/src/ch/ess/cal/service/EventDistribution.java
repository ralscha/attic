package ch.ess.cal.service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

import ch.ess.cal.model.Event;

public class EventDistribution implements Serializable {
  private List<EventDistributionItem> events;
  private Calendar calendar;
  private byte[] image;

  public EventDistribution(Calendar cal) {
    this.calendar = cal;

    events = new ArrayList<EventDistributionItem>();
  }

  public void addEvent(Event event) {
    EventDistributionItem edi = new EventDistributionItem(event, calendar);
    events.add(edi);
  }
    
  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public List<EventDistributionItem> getEvents() {
    return events;
  }

  public void compact() {
    if (events.size() > 1) {
      for (int i = 1; i < events.size(); i++) {
        EventDistributionItem bt = events.get(i);

        for (int j = 0; j < i; j++) {
          EventDistributionItem btc = events.get(j);
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

    for (ListIterator<EventDistributionItem> it = events.listIterator(); it.hasNext();) {
      EventDistributionItem edi = it.next();
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
