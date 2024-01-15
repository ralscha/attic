package ch.ess.cal;

import java.text.DecimalFormat;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;

public class Constants {

  //Cookie
  public static final String COOKIE_REMEMBER = "cal_remember";

  //Datasource Name
  public static final String DATASOURCE_NAME = "java:comp/env/jdbc/cal";

  //Date
  public static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");

  public static final SimpleDateFormat DATE_LOCAL_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

  public static SimpleDateFormat DATE_UTC_FORMAT;
  static {
    DATE_UTC_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    DATE_UTC_FORMAT.setTimeZone(UTC_TZ);
  }

  public static final SimpleDateFormat DATE_SHORT_FORMAT = new SimpleDateFormat("yyyyMMdd");

  public static final SimpleDateFormat DATE_UTC_SHORT_FORMAT = new SimpleDateFormat("yyyyMMdd");
  static {
    DATE_UTC_SHORT_FORMAT.setTimeZone(UTC_TZ);
  }

  public static final DecimalFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");

  //Numbers
  public static final Integer INTEGER_ONE = new Integer(1);
  public static final Integer INTEGER_ZERO = new Integer(0);
}