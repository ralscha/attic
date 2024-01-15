
import cmp.business.*;
import cmp.holidays.*;
import java.text.*;
import java.util.*;

public class HolidayTester {

  private static final HolInfo[] holidays = {
    new AshWednesday(), new Christmas(), new ChristmasEve(), 
    new EasterMonday(), new EasterSunday(),  new GoodFriday(),
    new MothersDay(), new NewYearsDay(), new NewYearsEve(),
    new PalmSunday(), new ValentinesDay() };

  private static Map holidayMap;

  public static Map getHolidayMap(int year) {
    
    if (holidayMap == null) {
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

	public static void main(String[] args) {
	
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); 
  
    Map holidayMap = getHolidayMap(2000);

    Set holi = holidayMap.keySet();
    Iterator it = holi.iterator();
    while(it.hasNext()) {
      Calendar cal = (Calendar)it.next();
      HolInfo h = (HolInfo)holidayMap.get(cal);
      System.out.print(dateFormat.format(cal.getTime()));
      System.out.print("  ");
      System.out.println(h);
      
    }


	}

}