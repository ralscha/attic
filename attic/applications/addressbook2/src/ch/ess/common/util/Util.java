package ch.ess.common.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.hibernate.util.StringHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:30 $ 
  */
public class Util {

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

    String result = StringUtils.replace(string, "&uuml;", "�");
    result = StringUtils.replace(result, "&nbsp;", " ");
    result = StringUtils.replace(result, "&auml;", "�");
    result = StringUtils.replace(result, "&ouml;", "�");
    result = StringUtils.replace(result, "&Uuml;", "�");
    result = StringUtils.replace(result, "&Auml;", "�");
    result = StringUtils.replace(result, "&Ouml;", "�");

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

        case '�' :
          sb.append("oe");
          break;

        case '�' :
          sb.append("ue");
          break;

        case '�' :
          sb.append("ae");
          break;

        case '�' :
          sb.append("Oe");
          break;

        case '�' :
          sb.append("Ue");
          break;

        case '�' :
          sb.append("Ae");
          break;

        case '�' :
          sb.append('e');
          break;

        case '�' :
          sb.append('e');
          break;

        case '�' :
          sb.append('E');
          break;

        case '�' :
          sb.append('E');
          break;

        case '�' :
          sb.append('a');
          break;

        case '�' :
          sb.append('A');
          break;

        case '�' :
          sb.append('c');
          break;

        case '�' :
          sb.append('o');
          break;

        case '�' :
          sb.append('a');
          break;

        case '�' :
          sb.append('u');
          break;

        case '�' :
          sb.append('e');
          break;

        case '�' :
          sb.append('i');
          break;

        case '�' :
          sb.append('n');
          break;

        case '�' :
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

        case '�' :
          result.append("&auml;");
          break;

        case '�' :
          result.append("&ouml;");
          break;

        case '�' :
          result.append("&uuml;");
          break;

        case '�' :
          result.append("&Auml;");
          break;

        case '�' :
          result.append("&Ouml;");
          break;

        case '�' :
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
