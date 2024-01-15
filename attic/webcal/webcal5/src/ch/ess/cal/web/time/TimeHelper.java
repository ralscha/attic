package ch.ess.cal.web.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeHelper {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	public static String getActualDay(){
        return dateFormat.format(new Date());
	}
	
	public static Integer getActualWeekNumber() {
		Calendar calendar = new GregorianCalendar();
		Date today = new Date();   
		calendar.setTime(today);     
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static Integer getWeekNumber(String inputDate) throws Exception{
		dateFormat.setLenient(false); //keine Nachsicht!
		Date date = dateFormat.parse(inputDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static boolean checkIsDateValid(String inputDate){
		if(inputDate==null) { return false; }
		//Check date validation
		dateFormat.setLenient(false); //keine Nachsicht!
		try {
			dateFormat.parse(inputDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	public static Date getDateFromString(String inputDate) throws Exception {
		dateFormat.setLenient(false); //keine Nachsicht!
		return dateFormat.parse(inputDate);
	}
	
	public static String getStringFromDate(Date d) throws Exception {
		 String date;
		 date = checkLength(d.getDate()) ? "" : "0";
		 date += d.getDate() +".";
		 date += checkLength(d.getMonth()) ? "" : "0";
		 date += (d.getMonth() + 1) + "." + (d.getYear() + 1900);
		 
		 return date;
	}
	
	//Gibt True zurück wenn eine Zahl mehr als 1 Stelle hat
	public static boolean checkLength(int i){
		boolean b = false;
		if(i>9){
			b = true;
		}
		return b;
	}
	
	public static String getDBQueryDate(String inputDate) throws Exception {
		Date date = getDateFromString(inputDate);
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		String str = format.format(date);
		return str;
	}

}
