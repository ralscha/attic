package ch.ess.addressbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:30 $  
  */
public class Constants {

  public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  public static final DateFormat PARSE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
  public static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  

  //Cookie
  public static final String COOKIE_REMEMBER = "addressbook_remember";

  //Datasource Name
  public static final String DATASOURCE_NAME = "java:comp/env/jdbc/addressbook";
}
