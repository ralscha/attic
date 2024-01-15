import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Trigger;

/**
 * @author sr
 */
public class ImportTest {

  public static void main(String[] args) {

    try {
      InputStream is = new FileInputStream("C:\\Documents and Settings\\sr\\Desktop\\test.ics");
      CalendarBuilder builder = new CalendarBuilder();
      Calendar calendar = builder.build(is);

      ComponentList componentList = calendar.getComponents();

      Iterator<Component> it = componentList.iterator();
      while (it.hasNext()) {
        Component component = it.next();

        if (component.getName().equals(Component.VEVENT)) {
          VEvent event = (VEvent)component;
          ComponentList cl = event.getAlarms();
          for (Iterator it2 = cl.iterator(); it2.hasNext();) {
            Component al = (Component)it2.next();
            if (al.getName().equals(Component.VALARM)) {
              VAlarm alarm = (VAlarm)al;
              PropertyList pl = alarm.getProperties();
              for (Iterator it3 = pl.iterator(); it3.hasNext();) {
                Property ap = (Property)it3.next();
                System.out.println(ap.getClass().getName());
                if (ap.getName().equals(Property.TRIGGER)) {
                  Trigger trigger = (Trigger)ap;
                  System.out.println(trigger.getValue());

                }
              }
            }
            System.out.println(al);

          }
        }
        System.out.println("Component [" + component.getName() + "]");

        for (Iterator j = component.getProperties().iterator(); j.hasNext();) {

          Property property = (Property)j.next();

          if (property.getName().equals(Property.DTSTART)) {
            DtStart start = (DtStart)property;
            System.out.println(property.getValue());
            System.out.println(start.getTime());
          }

          System.out.println(property.getClass().getName());
          //          if (property.getName().equals(Property.TRANSP)) {
          //            System.out.println(property.getValue());
          //            System.out.println(property.getClass().getName());
          //            //Transp transp = (Transp)property;
          //            //System.out.println(transp.getValue());
          //          }

          //System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
        }

      }

      //System.out.println(calendar);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
