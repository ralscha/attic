package ch.ess.cal;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/17 06:04:25 $
 */
public final class Constants {

  //Locales
  public static final Locale GERMAN_LOCALE = new Locale("de");
  public static final Locale ENGLISH_LOCALE = new Locale("en");

  public static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");

  //Forwards
  public static final String FORWARD_SUCCESS = "success";
  public static final String FORWARD_EDIT = "edit";
  public static final String FORWARD_DELETE = "delete";
  public static final String FORWARD_CREATE = "create";
  public static final String FORWARD_BACK = "back";

  public static final NumberFormat SIMPLE_FORMAT = new DecimalFormat("0");
  public static final NumberFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");

  private static String javascriptDateFormatPattern;
  private static String datePattern;
  private static String parseDatePattern;
  private static String dateTimePattern;
  private static String timePattern;

  public static void setDefaultDateFormat(final String pattern, final String parseDatePattern,
      final String dateTimePattern, final String timePattern) {

    Constants.datePattern = pattern;
    Constants.parseDatePattern = parseDatePattern;
    Constants.dateTimePattern = dateTimePattern;
    Constants.timePattern = timePattern;

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

  public static String getDateTimeFormatPattern() {
    return dateTimePattern;
  }

  public static String getTimeFormatPattern() {
    return timePattern;
  }

  public static SimpleDateFormat DATE_UTC_FORMAT;
  static {
    DATE_UTC_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    DATE_UTC_FORMAT.setTimeZone(UTC_TZ);
  }

  public static final SimpleDateFormat DATE_LOCAL_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

}
