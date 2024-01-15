package ch.ess.cal.util;


import java.text.ParseException;
import java.util.Date;

import ch.ess.cal.Constants;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;


public class Util {

  public static String javascriptEscape(String str) {

    if (str == null) {
      return null;
    }

    StringBuffer sb = new StringBuffer(str.length());

    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != '\'') {
        sb.append(str.charAt(i));
      } else {
        sb.append("\\'");
      }
    }

    return sb.toString();
  }  
  
  
  public static Calendar convertToUTCCalendar(String calString, TimeZone tz) throws ParseException {
    if (calString == null) {
      return null;     
    }    
    
    Calendar result = null;
    if (calString.length() == 8) {
      Date tmp = Constants.DATE_UTC_SHORT_FORMAT.parse(calString);
      result = new GregorianCalendar(Constants.UTC_TZ);
      result.setTime(tmp);      
    } else if (calString.length() == 16) {
      Date tmp = ch.ess.cal.Constants.DATE_UTC_FORMAT.parse(calString);
      result = new GregorianCalendar(Constants.UTC_TZ);
      result.setTime(tmp);      
    } else if (calString.length() == 15) {
      if (tz == null) {
        tz = TimeZone.getDefault();
      }
            
      Date tmp = ch.ess.cal.Constants.DATE_LOCAL_FORMAT.parse(calString);
      result = new GregorianCalendar(tz);
      result.setTime(tmp);            
      
      result.setTimeZone(Constants.UTC_TZ);
    } else {
      throw new IllegalArgumentException("wrong parameter :"+calString);      
    }
    
    return result;
  
  }
  
  public static Calendar convertToCalendar(String calString, TimeZone tz) throws ParseException {

    if (calString == null) {
      return null;     
    }    
    
    if (calString.length() == 8) {

      if (tz == null) {
        tz = TimeZone.getDefault();
      }
            
      Date tmp = Constants.DATE_SHORT_FORMAT.parse(calString);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(tmp);
      return cal;
    } else if (calString.length() == 16) {
      Date tmp = ch.ess.cal.Constants.DATE_UTC_FORMAT.parse(calString);
      Calendar cal = new GregorianCalendar(Constants.UTC_TZ);
      cal.setTime(tmp);
      return cal;
    } else if (calString.length() == 15) {
      if (tz == null) {
        tz = TimeZone.getDefault();
      }
      Date tmp = ch.ess.cal.Constants.DATE_LOCAL_FORMAT.parse(calString);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(tmp);   
      return cal;   
    } else {
      throw new IllegalArgumentException("wrong parameter :"+calString);      
    }
  
  }
  

}
