package ch.ess.base;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:22 $
 */
public final class Constants {

  //Locales
  public static final Locale GERMAN_LOCALE = new Locale("de");
  public static final Locale ENGLISH_LOCALE = new Locale("en");


  public static final String USER_SESSION = "user.session";

  public static final NumberFormat SIMPLE_FORMAT = new DecimalFormat("0");
  public static final NumberFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");

  private static String javascriptDateFormatPattern;
  private static String datePattern;
  private static String parseDatePattern;

  public static void setDefaultDateFormat(final String pattern, final String parseDatePattern) {

    Constants.datePattern = pattern;
    Constants.parseDatePattern = parseDatePattern;

    javascriptDateFormatPattern = StringUtils.replace(pattern, "dd", "%d");
    javascriptDateFormatPattern = StringUtils.replace(javascriptDateFormatPattern, "MM", "%m");
    javascriptDateFormatPattern = StringUtils.replace(javascriptDateFormatPattern, "yyyy", "%Y");
    javascriptDateFormatPattern = StringUtils.replace(javascriptDateFormatPattern, "yy", "%Y");
  }

  public static String getJavascriptDateFormatPattern() {
    return javascriptDateFormatPattern;
  }

  public static String getDateFormatPattern() {
    return datePattern;
  }

  public static String getParseDateFormatPattern() {
    return parseDatePattern;
  }



}