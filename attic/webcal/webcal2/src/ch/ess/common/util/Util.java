package ch.ess.common.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.hibernate.util.StringHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import ch.ess.cal.Constants;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:47 $ 
  */
public class Util {

  public static Calendar newCalendar(TimeZone tz, int year, int month, int day, int hour, int minute, int second, int millis) {
    Calendar c = new GregorianCalendar(tz);
    c.set(Calendar.YEAR, year);
    c.set(Calendar.MONTH, month);
    c.set(Calendar.DATE, day);
    c.set(Calendar.HOUR_OF_DAY, hour);
    c.set(Calendar.MINUTE, minute);
    c.set(Calendar.SECOND, second);
    c.set(Calendar.MILLISECOND, millis);
    return c;
  }
  
  public static Locale getLocale(String str) {

    if (str != null) {
      StringTokenizer tokens = new StringTokenizer(str, "_");
      String language = tokens.hasMoreTokens() ? tokens.nextToken() : StringHelper.EMPTY_STRING;
      String country = tokens.hasMoreTokens() ? tokens.nextToken() : StringHelper.EMPTY_STRING;
      String variant = tokens.hasMoreTokens() ? tokens.nextToken() : StringHelper.EMPTY_STRING;
      return new Locale(language, country, variant);
    } else {
      return Locale.getDefault();
    }

  }

  public static String truncateNicely(String str, int lower, int upper, String appendToEnd, boolean htmlSubst) {

    if (str == null) {
      return str;
    }

    // strip markup from the string
    str = removeXml(str);

    if (htmlSubst) {
      str = replaceHTMLEntities(str);
    }

    // quickly adjust the upper if it is set lower than 'lower'
    if (upper < lower) {
      upper = lower;
    }

    // now determine if the string fits within the upper limit
    // if it does, go straight to return, do not pass 'go' and collect $200
    if (str.length() > upper) {
      // the magic location int
      int loc;

      // first we determine where the next space appears after lower
      loc = str.lastIndexOf(' ', upper);

      // now we'll see if the location is greater than the lower limit
      if (loc >= lower) {
        // yes it was, so we'll cut it off here
        str = str.substring(0, loc);
      } else {
        // no it wasnt, so we'll cut it off at the upper limit
        str = str.substring(0, upper);
      }

      // the string was truncated, so we append the appendToEnd String   
      if (appendToEnd != null) {     
        str = str + appendToEnd;
      }
    }

    if (htmlSubst) {
      str = replaceToHTMLEntities(str);
    }
    return str;
  }

  /**
   * Remove any xml tags from a String.
   */
  public static String removeXml(String str) {

    if (str == null) {
      return null;
    }

    int sz = str.length();
    StringBuffer buffer = new StringBuffer(sz);
    //boolean inString = false;
    boolean inTag = false;
    for (int i = 0; i < sz; i++) {
      char ch = str.charAt(i);
      if (ch == '<') {
        inTag = true;
      } else if (ch == '>') {
        inTag = false;
        continue;
      }
      if (!inTag) {
        buffer.append(ch);
      }
    }
    return buffer.toString();
  }

  public static String replaceHTMLEntities(String string) {
    if (string == null) {
      return string;
    }

    String result = replaceHTMLUml(string);
    result = StringUtils.replace(result, "&amp;", "&");
    result = StringUtils.replace(result, "&quot;", "\"");
    result = StringUtils.replace(result, "&lt;", "<");
    result = StringUtils.replace(result, "&gt;", ">");

    return result;

  }

  public static String replaceToHTMLEntities(String string) {
    if (string == null) {
      return string;
    }

    String result = replaceToHtmlUml(string);
    result = StringUtils.replace(result, " ", "&nbsp;");
    result = StringUtils.replace(result, "\"", "&quot;");
    result = StringUtils.replace(result, "<", "&lt;");
    result = StringUtils.replace(result, ">", "&gt;");

    return result;

  }

  public static String replaceHTMLUml(String string) {

    if (string == null) {
      return string;
    }

    String result = StringUtils.replace(string, "&uuml;", "ü");
    result = StringUtils.replace(result, "&nbsp;", " ");
    result = StringUtils.replace(result, "&auml;", "ä");
    result = StringUtils.replace(result, "&ouml;", "ö");
    result = StringUtils.replace(result, "&Uuml;", "Ü");
    result = StringUtils.replace(result, "&Auml;", "Ä");
    result = StringUtils.replace(result, "&Ouml;", "Ö");

    return result;
  }

  public static String removeNonDigits(String string) {

    if (string == null) {
      return string;
    }

    char[] carray = string.toCharArray();
    StringBuffer sb = new StringBuffer(carray.length);

    for (int i = 0; i < carray.length; i++) {
      if ((carray[i] == '.') || (Character.isDigit(carray[i]))) {
        sb.append(carray[i]);
      }
    }

    return sb.toString();
  }

  public static String toUsAscii(String string) {

    if (string == null) {
      return string;
    }

    char[] carray = string.toCharArray();
    StringBuffer sb = new StringBuffer(carray.length);

    for (int i = 0; i < carray.length; i++) {
      switch (carray[i]) {

        case 'ö' :
          sb.append("oe");
          break;

        case 'ü' :
          sb.append("ue");
          break;

        case 'ä' :
          sb.append("ae");
          break;

        case 'Ö' :
          sb.append("Oe");
          break;

        case 'Ü' :
          sb.append("Ue");
          break;

        case 'Ä' :
          sb.append("Ae");
          break;

        case 'é' :
          sb.append('e');
          break;

        case 'è' :
          sb.append('e');
          break;

        case 'É' :
          sb.append('E');
          break;

        case 'È' :
          sb.append('E');
          break;

        case 'à' :
          sb.append('a');
          break;

        case 'À' :
          sb.append('A');
          break;

        case 'ç' :
          sb.append('c');
          break;

        case 'ô' :
          sb.append('o');
          break;

        case 'â' :
          sb.append('a');
          break;

        case 'û' :
          sb.append('u');
          break;

        case 'ê' :
          sb.append('e');
          break;

        case 'ï' :
          sb.append('i');
          break;

        case 'ñ' :
          sb.append('n');
          break;

        case 'ß' :
          sb.append("ss");
          break;

        default :
          sb.append(carray[i]);
      }
    }

    string = sb.toString();

    try {
      return new String(string.getBytes(), "US-ASCII");
    } catch (java.io.UnsupportedEncodingException ue) {
      ue.printStackTrace();
    }

    return string;
  }

  public static String replaceToHtmlUml(String value) {

    if (value == null) {
      return (null);
    }

    char content[] = value.toCharArray();
    StringBuffer result = new StringBuffer(content.length + 20);

    for (int i = 0; i < content.length; i++) {
      switch (content[i]) {

        case 'ä' :
          result.append("&auml;");
          break;

        case 'ö' :
          result.append("&ouml;");
          break;

        case 'ü' :
          result.append("&uuml;");
          break;

        case 'Ä' :
          result.append("&Auml;");
          break;

        case 'Ö' :
          result.append("&Ouml;");
          break;

        case 'Ü' :
          result.append("&Uuml;");
          break;

        case '&' :
          result.append("&amp;");
          break;

        default :
          result.append(content[i]);
      }
    }

    return (result.toString());
  }


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
  
  public static Calendar utcLong2UserCalendar(long time, TimeZone tz) {
    Calendar cal = new GregorianCalendar(tz);
    cal.setTimeInMillis(time);    
    return cal;
  }
  
  
  public static String userCalendar2String(Calendar cal, String pattern) {
    if (cal == null) {
      return null;
    }
    
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    format.setTimeZone(cal.getTimeZone());
    return format.format(cal);
  }
  
  
  public static Calendar string2Calendar(String calStr, TimeZone tz) {
    if (GenericValidator.isBlankOrNull(calStr)) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
    format.setLenient(false);
    format.setTimeZone(tz);
    try {
      Date d = format.parse(calStr);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(d);      
      return cal;
    } catch (ParseException e) {
      return null;
    }
   
    
  }
  
  public static Calendar string2Calendar(String calStr, String hstr, String mstr, TimeZone tz) {
    if (GenericValidator.isBlankOrNull(calStr)) {
      return null;
    }
    
    int h = 0;
    int m = 0;
    if (!GenericValidator.isBlankOrNull(hstr)) {
      h = Integer.parseInt(hstr);
    } 
    
    if (!GenericValidator.isBlankOrNull(mstr)) {
      m = Integer.parseInt(mstr);
    }
    
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
    format.setLenient(false);
    format.setTimeZone(tz);
    try {
      Date d = format.parse(calStr);
      Calendar cal = new GregorianCalendar(tz);
      cal.setTime(d);
      cal.set(Calendar.HOUR_OF_DAY, h);
      cal.set(Calendar.MINUTE, m);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
            
      return cal;
    } catch (ParseException e) {
      return null;
    }
   
    
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

  public static boolean containsString(Map translations, String searchText) {
    if (GenericValidator.isBlankOrNull(searchText)) {
      return true;
    }
    
    searchText = searchText.toLowerCase();
    
    for (Iterator it = translations.values().iterator(); it.hasNext();) {
      String element = (String)it.next();
      if (element.toLowerCase().indexOf(searchText) != -1) {
        return true;
      }
    }
    
    return false;
  }
}
