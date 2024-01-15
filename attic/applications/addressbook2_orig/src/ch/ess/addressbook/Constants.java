package ch.ess.addressbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {

  public static final String COOKIE_REMEMBER = "ab_remember";

  public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  public static final DateFormat PARSE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
  
  static {
    PARSE_DATE_FORMAT.setLenient(false);
  }

}
