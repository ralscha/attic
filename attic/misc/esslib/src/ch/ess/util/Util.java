package ch.ess.util;

import java.beans.*;
import java.lang.reflect.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.beanutils.*;
import org.apache.commons.lang.*;

import ch.ess.*;
import cmp.business.*;

public class Util {

  //  private static final Log LOG = LogFactory.getLog(Util.class.getPackage().getName());
  private static final DecimalFormat f = new DecimalFormat("0.00");
  //private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
  private static final java.text.DecimalFormat formatter = new java.text.DecimalFormat("0.#");

  private static Pattern pattern;
  private static final String EMAIL_REGEX =
    "^([A-Za-z0-9_]|\\-|\\.)+@([A-Za-z0-9_.]|\\-)+\\.\\w{2,4}([;,]([A-Za-z0-9_.]|\\-|\\.)+@([A-Za-z0-9_]|\\-)+\\.\\w{2,4})*$";

  static {
    pattern = Pattern.compile(EMAIL_REGEX);
  }

  public static String blank2null(String str) {
    if ((str != null) && (str.trim().equals(Constants.BLANK_STRING))) {
      return null;
    }

    return str;
  }

  public static void blank2nullBean(Object o) {
    try {
      Object[] nullObject = new Object[0];
      PropertyDescriptor descriptors[] = PropertyUtils.getPropertyDescriptors(o);

      for (int i = 0; i < descriptors.length; i++) {
        if (descriptors[i].getPropertyType() == String.class) {
          Method read = descriptors[i].getReadMethod();
          Method write = descriptors[i].getWriteMethod();
          if ((read != null) && (write != null)) {
            String value = (String) read.invoke(o, nullObject);
            if ((value != null) && (value.trim().equals(Constants.BLANK_STRING))) {
              write.invoke(o, new Object[] { null });
            }
          }
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public static String leftJustify(String s, int len, char pad) {
    int ls = s.length();
    int ln = len < ls ? len : ls;

    if (len < 1) {
      return "";
    }

    char[] buf = new char[len];

    s.getChars(0, ln, buf, 0);

    while (ln < len) {
      buf[ln++] = pad;
    }

    return new String(buf);
  }

  public static boolean notEmpty(String str) {
    if (str != null) {
      return (!str.trim().equals(Constants.BLANK_STRING));
    }
    return false;
  }

  public static boolean isInPeriod(Calendar testCal, Calendar fromCal, Calendar toCal) {
    return (afterOrEquals(testCal, fromCal) && beforeOrEquals(testCal, toCal));
  }

  public static boolean isInYear(Calendar from, Calendar to, int year) {

    long fromTime = from.getTime().getTime();
    long toTime = to.getTime().getTime();
    long first = new GregorianCalendar(year, Calendar.JANUARY, 1).getTime().getTime();
    long last = new GregorianCalendar(year, Calendar.DECEMBER, 31).getTime().getTime();

    return (((fromTime <= first) && (toTime >= first)) || ((fromTime >= first) && (fromTime <= last)));
  }

  public static int daysInMonth(Calendar from, Calendar to, int year, int month) {

    BigDate fromBD = new BigDate(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, from.get(Calendar.DATE));
    BigDate toBD = new BigDate(to.get(Calendar.YEAR), to.get(Calendar.MONTH) + 1, to.get(Calendar.DATE));
    int fromTime = fromBD.getOrdinal();
    int toTime = toBD.getOrdinal();
    Calendar firstCal = new GregorianCalendar(year, month, 1);
    int first = (new BigDate(firstCal.get(Calendar.YEAR), firstCal.get(Calendar.MONTH) + 1, firstCal.get(Calendar.DATE))).getOrdinal();
    Calendar lastCal = new GregorianCalendar(year, month, firstCal.getActualMaximum(Calendar.DATE));
    int last = (new BigDate(lastCal.get(Calendar.YEAR), lastCal.get(Calendar.MONTH) + 1, lastCal.get(Calendar.DATE))).getOrdinal();

    if (((fromTime <= first) && (toTime >= first)) || ((fromTime >= first) && (fromTime <= last))) {
      int r1 = fromTime - first;
      int r2 = last - toTime;

      if ((r1 < 0) && (r2 < 0)) {
        return firstCal.getActualMaximum(Calendar.DATE);
      } else if ((r1 >= 0) && (r2 < 0)) {
        if (r1 == 0) {
          return firstCal.getActualMaximum(Calendar.DATE);
        } else {
          return firstCal.getActualMaximum(Calendar.DATE) - from.get(Calendar.DATE) + 1;
        }
      } else if ((r1 < 0) && (r2 >= 0)) {
        return to.get(Calendar.DATE);
      } else if ((r1 >= 0) && (r2 >= 0)) {
        return toTime - fromTime + 1;
      } else {
        System.out.println("verdammt");

        return -1;
      }
    } else {
      return 0;
    }
  }

  public static String suppressNull(String str) {

    if (str == null) {
      return Constants.BLANK_STRING;
    } else {
      return str;
    }
  }

  public static String toHex(byte[] bytes) {

    StringBuffer sb = new StringBuffer();
    char c;

    for (int i = 0; i < bytes.length; i++) {

      // First nibble
      c = (char) ((bytes[i] >> 4) & 0xf);

      if (c > 9) {
        c = (char) ((c - 10) + 'A');
      } else {
        c = (char) (c + '0');
      }

      sb.append(c);

      // Second nibble
      c = (char) (bytes[i] & 0xf);

      if (c > 9) {
        c = (char) ((c - 10) + 'A');
      } else {
        c = (char) (c + '0');
      }

      sb.append(c);
    }

    return sb.toString();
  }

  public static String cut(String s, int len) {

    if (len < 4) {
      len = 4;
    }

    if (s != null) {
      if (s.length() > len) {
        return (s.substring(0, len - 3) + "...");
      }
    }

    return s;
  }

  public static int dayDiff(Calendar c1, Calendar c2) {

    BigDate bd1 = new BigDate(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH) + 1, c1.get(Calendar.DATE));
    BigDate bd2 = new BigDate(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH) + 1, c2.get(Calendar.DATE));

    if (c1.before(c2)) {
      return (bd2.getOrdinal() - bd1.getOrdinal() + 1);
    } else {
      return (bd1.getOrdinal() - bd2.getOrdinal() + 1);
    }
  }

  public static Date addDay(Date date, int days) {

    Calendar cal = new GregorianCalendar();

    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.add(Calendar.DATE, days);

    return cal.getTime();
  }

  public static Date addMonth(Date date, int months) {

    Calendar cal = new GregorianCalendar();

    cal.setTime(date);
    cal.set(Calendar.DATE, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.add(Calendar.MONTH, months);

    return cal.getTime();
  }

  public static boolean equals(java.lang.Comparable o1, java.lang.Comparable o2) {

    boolean c;

    if (((o1 == null) && (o2 != null)) || ((o1 != null) && (o2 == null))) {
      c = false;
    } else {
      if (o1 == null) {
        c = true;
      } else {
        c = o1.equals(o2);
      }
    }

    return c;
  }

  public static String filterNewline(String s) {

    String t;

    if (s != null) {
      StringBuffer sb = new StringBuffer();
      StringTokenizer st = new StringTokenizer(s, "\n", true);

      while (st.hasMoreTokens()) {
        t = st.nextToken();

        sb.append((t.equals("\n") ? "<br>\n" : t));
      }

      return sb.toString();
    }

    return null;
  }

  /*
  public static String filterSingleQuotes(String s) {
  
    String r = null;
    String t;
  
    if (s != null) {
      r = BLANK_STRING;
  
      StringTokenizer st = new StringTokenizer(s, "'\\", true);
  
      while (st.hasMoreTokens()) {
        t = st.nextToken();
        r = r + ((t.equals("'")) ? "\\'" : ((t.equals("\\")) ? "\\\\" : t));
      }
    }
  
    return r;
  }
  */

  public static String filterUmlaute(String value) {

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

  public static String inWorten(BigDecimal d) {

    if (d == null) {
      return (Constants.BLANK_STRING);
    }

    String s = formatter.format(d);
    StringBuffer r = new StringBuffer(s.length() * 6);
    char[] content = s.toCharArray();
    int len = content.length;

    for (int i = 0; i < len; i++) {
      switch (content[i]) {

        case '.' :
          r.append("komma");
          break;

        case '0' :
          r.append("null");
          break;

        case '1' :
          r.append("eins");
          break;

        case '2' :
          r.append("zwei");
          break;

        case '3' :
          r.append("drei");
          break;

        case '4' :
          r.append("vier");
          break;

        case '5' :
          r.append("fünf");
          break;

        case '6' :
          r.append("sechs");
          break;

        case '7' :
          r.append("sieben");
          break;

        case '8' :
          r.append("acht");
          break;

        case '9' :
          r.append("neun");
          break;
      }
    }

    return r.toString();
  }

  ////////////////////
  public static BigDecimal toHM(BigDecimal x) {

    BigDecimal h = x.setScale(0, BigDecimal.ROUND_DOWN);
    BigDecimal m = (x.subtract(h)).multiply(Constants.BIGDECIMAL_SIX).divide(Constants.BIGDECIMAL_TEN, 3, BigDecimal.ROUND_HALF_UP);

    return h.add(m);
  }

  public static String toHMString(BigDecimal x) {

    String hm;

    hm = f.format(toHM(x));
    hm = hm.replace('.', ':');

    return hm;
  }

  //////////////
  public static void clearTime(Calendar cal) {

    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
  }

  public static java.util.Date clearTime(java.util.Date d) {

    Calendar cal = new GregorianCalendar();

    cal.setTime(d);
    clearTime(cal);

    return cal.getTime();
  }

  public static Date addYear(Date date, int years) {

    Calendar cal = new GregorianCalendar();

    cal.setTime(date);
    cal.set(Calendar.MONTH, 0);
    cal.set(Calendar.DATE, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.add(Calendar.YEAR, years);

    return cal.getTime();
  }

  public static boolean isDigits(String str) {

    char[] carray = str.toCharArray();

    for (int i = 0; i < carray.length; i++) {
      if (!Character.isDigit(carray[i])) {
        return false;
      }
    }

    return true;
  }

  public static boolean containsDigits(String s) {

    char[] carray = s.toCharArray();

    for (int i = 0; i < carray.length; i++) {
      if (Character.isDigit(carray[i])) {
        return true;
      }
    }

    return false;
  }

  public static boolean isCurrency(String str) {

    char[] carray = str.toCharArray();

    for (int i = 0; i < carray.length; i++) {
      if (!Character.isDigit(carray[i])) {
        if (carray[i] != '.') {
          return false;
        }
      }
    }

    return true;
  }

  public static boolean isValidEmail(String email) {

    if (email == null) {
      return false;
    }

    Matcher matcher = pattern.matcher(email);

    if (matcher.matches()) {
      return true;
    } else {
      return false;
    }
  }

  public static String doubleApostrophe(String str) {

    if (str == null) {
      return null;
    }

    char[] carray = str.toCharArray();
    StringBuffer sb = new StringBuffer(str.length());

    for (int i = 0; i < carray.length; i++) {
      if (carray[i] != '\'') {
        sb.append(carray[i]);
      } else {
        sb.append("''");
      }
    }

    return sb.toString();
  }

  public static boolean beforeOrEquals(Calendar c1, Calendar c2) {
    return c1.getTimeInMillis() <= c2.getTimeInMillis();
  }

  public static boolean afterOrEquals(Calendar c1, Calendar c2) {
    return c1.getTimeInMillis() >= c2.getTimeInMillis();
  }

  public static Date rectifyDate(Date d, Date twodigit) {

    Calendar c = null;

    if (d != null) {
      c = new GregorianCalendar();

      c.setTime(twodigit);

      int twoy = c.get(Calendar.YEAR);

      c.setTime(d);

      int y = c.get(Calendar.YEAR);

      if ((y < 1753) || (y > 9999)) {
        y = (y % 100);
        y = (y < (twoy % 100)) ? 2000 + y : 1900 + y;

        c.set(Calendar.YEAR, y);
      }

      return c.getTime();
    }

    return null;
  }

  //  entfernt \r aus \r\n
  public static String removeLineFeed(String string) {

    if (string == null) {
      return null;
    }

    char[] carray = string.toCharArray();
    StringBuffer sb = new StringBuffer(carray.length);

    for (int i = 0; i < carray.length; i++) {
      if (carray[i] != '\r') {
        sb.append(carray[i]);
      }
    }

    return sb.toString();
  }

  public static String removeApostroph(String string) {

    if (string == null) {
      return null;
    }

    char[] carray = string.toCharArray();
    StringBuffer sb = new StringBuffer(carray.length);

    for (int i = 0; i < carray.length; i++) {
      if (carray[i] != '\'') {
        sb.append(carray[i]);
      }
    }

    return sb.toString();
  }

  public static String removeNonDigits(String string) {

    if (string == null) {
      return null;
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

  public static String replaceBlank2HTMLBlank(String string) {
    if (string == null) {
      return string;
    }

    return StringUtils.replace(string, " ", "&nbsp;");
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

    String result = filterUmlaute(string);
    result = StringUtils.replace(result, " ", "&nbsp;");
    result = StringUtils.replace(result, "\"", "&quot;");
    result = StringUtils.replace(result, "<", "&lt;");
    result = StringUtils.replace(result, ">", "&gt;");

    return result;

  }
  
    

  public static String toUsAscii(String string) {

    if (string == null) {
      return null;
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
    }

    return string;
  }

  public static String getSprache(Locale locale) {

    String localeStr = locale.toString();
    StringBuffer sb = new StringBuffer();
    char[] arr = localeStr.toCharArray();

    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != '_') {
        sb.append(arr[i]);
      }
    }

    return sb.toString();
  }

  public static Locale getLocale(String sprache) {

    if (sprache != null) {
      Locale locale = Locale.getDefault();

      if (sprache.length() == 4) {
        locale = new Locale(sprache.substring(0, 2), sprache.substring(2));
      } else {
        if (sprache.length() == 2) {
          locale = new Locale(sprache.substring(0, 2), Constants.BLANK_STRING);
        } else {
          if (sprache.length() > 4) {
            locale = new Locale(sprache.substring(0, 2), sprache.substring(2, 4), sprache.substring(4));
          }
        }
      }

      return locale;
    } else {
      return Locale.getDefault();
    }
  }

  public static String hexDigit(byte x) {

    StringBuffer sb = new StringBuffer();
    char c;

    // First nibble
    c = (char) ((x >> 4) & 0xf);

    if (c > 9) {
      c = (char) ((c - 10) + 'a');
    } else {
      c = (char) (c + '0');
    }

    sb.append(c);

    // Second nibble
    c = (char) (x & 0xf);

    if (c > 9) {
      c = (char) ((c - 10) + 'a');
    } else {
      c = (char) (c + '0');
    }

    sb.append(c);

    return sb.toString();
  }

  public static BigDecimal roundFive(BigDecimal bd) {

    bd = bd.divide(Constants.BIGDECIMAL_ONE, BigDecimal.ROUND_HALF_UP);
    bd = bd.multiply(Constants.BIGDECIMAL_TWENTY);
    bd = bd.setScale(0, BigDecimal.ROUND_DOWN);
    bd = bd.setScale(2);
    bd = bd.divide(Constants.BIGDECIMAL_TWENTY, BigDecimal.ROUND_HALF_UP);

    return bd;
  }

  public static BigDecimal roundOne(BigDecimal bd) {

    bd = bd.multiply(Constants.BIGDECIMAL_ONE_HUNDRED);
    bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
    bd = bd.setScale(2);
    bd = bd.divide(Constants.BIGDECIMAL_ONE_HUNDRED, BigDecimal.ROUND_HALF_UP);

    return bd;
  }

  public static String truncateNicely(String str, int lower, int upper, String appendToEnd, boolean htmlSubst) {
    
    if (str == null) {
      return null;
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
      str = str + appendToEnd;
    }

    if (htmlSubst) {
      str = replaceToHTMLEntities(str);
    }
    return str;
  }

  /**
   * Remove any xml tags from a String.
   * Same as HtmlW's method.
   */
  static public String removeXml(String str) {

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


  public static List getStringParts(String str, int len) {
    if (str == null) {
      return null;
    }
    
    
    List result = new ArrayList();
    while(str.length() >= len) {
      String part = str.substring(0, len);
      result.add(part);
      str = str.substring(len);
    }    
    result.add(str);          
    
    return result;
    
  }
}
