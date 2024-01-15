
package ch.ess.calendar.util;

import cmp.business.*;
import cmp.holidays.*;
import java.util.*;

public class Holidays {

  private static final HolInfo[] holidays = {
    new Christmas(), new ChristmasEve(), 
    new EasterMonday(), new EasterSunday(),  new GoodFriday(),
    new NewYearsDay(), new NewYearsEve(),
    new BoxingDay(),
    new ch.ess.calendar.util.holidays.Ascension(),
    new ch.ess.calendar.util.holidays.WhitMonday(),
    new ch.ess.calendar.util.holidays.WhitSunday() };

  private static Map holidayMap;
  private static int myear;

  public static Map getHolidayMap(int year) {
    
    if ((holidayMap == null) || (year != myear)) {
      myear = year;

      holidayMap = new HashMap();

      for (int i = 0; i < holidays.length; i++) {
        HolInfo h = holidays[i];
  
        BigDate d = new BigDate(h.when(year, false));
        if (d.getOrdinal() == BigDate.NULL_ORDINAL) continue;
  
        Calendar cal = new GregorianCalendar(d.getYYYY(), d.getMM()-1, d.getDD());
        holidayMap.put(cal, h);
      }
      
      holidayMap = Collections.unmodifiableMap(holidayMap);
    }
    return holidayMap;
  }

  public static Map getHolidayMap(int year, int month) {
    Map tmpMap = getHolidayMap(year);
    Map newMap = new TreeMap();
    Iterator it = tmpMap.keySet().iterator();
    while(it.hasNext()) {
      Calendar cal = (GregorianCalendar)it.next();
      if (cal.get(Calendar.MONTH) == month) {
        newMap.put(ch.ess.calendar.util.Constants.dateFormat.format(cal.getTime()),
                   (HolInfo)tmpMap.get(cal));
      }
    } 
    return newMap;
  }


}