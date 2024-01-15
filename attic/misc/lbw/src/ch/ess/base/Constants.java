package ch.ess.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

public final class Constants {

  //Locales
  
  public static final BigDecimal ZERO = new BigDecimal(0);

  public static final NumberFormat SIMPLE_FORMAT = new DecimalFormat("0");
  public static final NumberFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");
  public static final NumberFormat TWO_DECIMAL_FORMAT = new DecimalFormat("0.00");
  public static final NumberFormat ONE_DECIMAL_FORMAT = new DecimalFormat("0.0");
  
  private static String javascriptDateFormatPattern;
  private static String javascriptDateTimeFormatPattern;
  
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
    
    javascriptDateTimeFormatPattern = StringUtils.replace(dateTimePattern, "dd", "%d");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "MM", "%m");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "yyyy", "%Y");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "yy", "%Y");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "HH", "%H");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "mm", "%M");
  }

  public static String getJavascriptDateFormatPattern() {
    return javascriptDateFormatPattern;
  }

  public static String getJavascriptDateTimeFormatPattern() {
    return javascriptDateTimeFormatPattern;
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
}
