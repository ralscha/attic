
package ch.ess.calendar.util;

import java.text.*;

public class Constants {
	
	private final static DateFormatSymbols symbols = new DateFormatSymbols(); 
	
	public final static String[] MONTHNAMES = symbols.getMonths();
	public final static int[] MONTHDAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30 ,31};
	public final static String[] WEEKDAYS = symbols.getWeekdays();
	public final static String[] SHORT_WEEKDAYS = symbols.getShortWeekdays();
	
	public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	public final static SimpleDateFormat sTimeFormat = new SimpleDateFormat("H:m");
  public final static SimpleDateFormat s2TimeFormat = new SimpleDateFormat("HHmm");
	
	public final static SimpleDateFormat sDateFormat = new SimpleDateFormat("d.M.y");

  static {
    for (int i = 0; i < SHORT_WEEKDAYS.length; i++) {
      if (SHORT_WEEKDAYS[i].length() > 2) {
        SHORT_WEEKDAYS[i] = SHORT_WEEKDAYS[i].substring(0,2);
      }
    }
  }
}