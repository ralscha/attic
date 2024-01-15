import java.util.Calendar;
import java.util.GregorianCalendar;

import ch.ess.cal.enums.TimeRangeEnum;
import ch.ess.cal.web.time.CalendarRange;
import ch.ess.cal.web.time.CalendarRangeIterator;



public class TestSec {

  /**
   * @param args
   */
  public static void main(String[] args) {

    Calendar start = new GregorianCalendar(2006, Calendar.JANUARY, 1);
    Calendar end = new GregorianCalendar(2006, Calendar.DECEMBER, 31);
    CalendarRangeIterator ci = new CalendarRangeIterator(start, end, TimeRangeEnum.WEEK);
    
    while(ci.hasNext()) {
      CalendarRange cr = ci.next();
      System.out.println(cr.getFrom().getTime());
      System.out.println(cr.getTo().getTime());
      System.out.println(cr.getDescription());
      System.out.println();
    }
    
  }

}
