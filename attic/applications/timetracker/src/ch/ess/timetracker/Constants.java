package ch.ess.timetracker;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:21 $  
  */
public class Constants {

  public static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal(0);

  //Cookie
  public static final String COOKIE_REMEMBER = "timetracker_remember";

  //Datasource Name
  public static final String DATASOURCE_NAME = "java:comp/env/jdbc/timetracker";
  
  
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  static {
    DATE_FORMAT.setLenient(false);
  }
  
  
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("de", "CH")));
  
}
