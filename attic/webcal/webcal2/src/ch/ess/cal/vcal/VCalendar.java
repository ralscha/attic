package ch.ess.cal.vcal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VCalendar implements VCalendarConstants {

  private String strTimeZone;

  List tableEvents = new ArrayList();
  List tableTodo = new ArrayList();

  public List getAllEvents() {
    return tableEvents;
  }

  public List getAllTodo() {
    return tableTodo;
  }

  public synchronized void setTimeZone(String str) {
    this.strTimeZone = str;
  }

  public synchronized void addNewEvent(VEvent evt) {
    tableEvents.add(evt);
  }

  public synchronized void addNewTodo(VTodo tdo) {
    tableTodo.add(tdo);
  }

  //===========================================================================

  public String toString() {
    StringBuffer strbuf = new StringBuffer();

    //// 1. Append the header.
    strbuf.append(BEGIN + ":" + VCALENDAR + "\r\n");

    if (strTimeZone != null) {
      strbuf.append(CAL_TZ + ":" + strTimeZone + "\r\n");
    }

    strbuf.append(CAL_PRODID + ":" + VCAL_PRODUCTIDENTIFIER + "\r\n");
    strbuf.append(CAL_VERSION + ":" + VCAL_VERSION + "\r\n");

    for (Iterator it = tableEvents.iterator(); it.hasNext();) {
      strbuf.append(it.next().toString());
    }

    for (Iterator it = tableTodo.iterator(); it.hasNext();) {
      strbuf.append(it.next().toString());
    }

    strbuf.append(END + ":" + VCALENDAR + "\r\n");

    return (strbuf.toString());

  }
}
