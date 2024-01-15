import java.io.FileOutputStream;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Version;

/**
 * @author sr
 */
public class SimpleTest {

  public static void main(String[] args) {
    try {
      //    VTimeZone tz = VTimeZone.getDefault();
      //
      //    //  create tzid parameter..
      //    TzId tzParam = new TzId(tz.getProperties().getProperty(Property.TZID).getValue());
      //
      //    //  create value parameter..
      //    Value type = new Value(Value.DATE);
      //
      //    //  create event start date..
      //    java.util.Calendar calendar = java.util.Calendar.getInstance();
      //    calendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
      //    calendar.set(java.util.Calendar.DAY_OF_MONTH, 25);
      //
      //    DtStart start = new DtStart(calendar.getTime());
      //    start.getParameters().add(tzParam);
      //    start.getParameters().add(type);
      //
      //    Summary summary = new Summary("Christmas Day");
      //
      //    VEvent christmas = new VEvent();
      //    christmas.getProperties().add(start);
      //    christmas.getProperties().add(summary);
      //    System.out.println(christmas);

      //    try {
      //      FileOutputStream fos = new FileOutputStream("c:\\temp\\test.png");
      //      BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
      //      Graphics gr = image.getGraphics();
      //      gr.setColor(Color.WHITE);
      //
      //      int width = 20;
      //      int height = 20;
      //
      //      gr.fillRect(0, 0, width, height);
      //
      //      
      //      int boxWidth = 3;
      //      int boxHeight = 3;
      //
      //      int curX = 0;
      //      int curY = 0;
      //
      //      gr.setColor(Color.BLACK);
      //
      //      boolean oddrow = false;
      //      while (curY <= height) {
      //        gr.fillRect(curX, curY, boxWidth, boxHeight);
      //        curX = curX + (boxWidth*2);
      //        if (curX > width) {
      //          curY = curY + (boxHeight);
      //          if (oddrow) {
      //            curX = 0;
      //            oddrow = false;
      //          } else {
      //            curX = boxWidth;
      //            oddrow = true;
      //          }
      //        }
      //      }
      //
      //      ImageIO.write(image, "png", fos);
      //      fos.close();
      //    } catch (Exception e) {
      //      e.printStackTrace();
      //    }

      VTimeZone tz = VTimeZone.getDefault();
      //  create tzid parameter..
      TzId tzParam = new TzId(tz.getProperties().getProperty(Property.TZID).getValue());
      //  create value parameter..
      Value type = new Value(Value.DATE);

      //  create event start date..
      java.util.Calendar cal = new GregorianCalendar();
      cal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
      cal.set(java.util.Calendar.DAY_OF_MONTH, 25);

      DtStart start = new DtStart(cal.getTime());
      start.getParameters().add(tzParam);
      start.getParameters().add(type);
      Summary summary = new Summary("Christmas Day");

      VEvent christmas = new VEvent();
      christmas.getProperties().add(start);
      christmas.getProperties().add(summary);

      ComponentList components = new ComponentList();
      components.add(christmas);
      //PropertyList properties = new PropertyList();

      Calendar myICalendar = new Calendar(components);
      myICalendar.getProperties().add(new Version());
      myICalendar.getProperties().add(new ProdId("-//ESS Development AG//WebCal//EN"));

      //PropertyFactory myPropertyFactory = PropertyFactory.getInstance();
      //Property prodId = myPropertyFactory.createProperty(Property.PRODID);
      //prodId.setValue("Prova");
      //myICalendar.getProperties().add(prodId);

      //Property version = myPropertyFactory.createProperty(Property.VERSION);
      //version.setValue("1");
      //myICalendar.getProperties().add(version);

      System.out.println(myICalendar);

      FileOutputStream fout = new FileOutputStream("c:/temp/test.ics");
      CalendarOutputter outputter = new CalendarOutputter();
      outputter.output(myICalendar, fout);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
