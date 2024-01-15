package ch.ess.addressbook;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.4 $ $Date: 2003/11/11 18:56:32 $  
  */
public class Constants {
  
public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  public static final DateFormat PARSE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
  

  //Cookie
  public static final String COOKIE_REMEMBER = "addressbook_remember";
  
  //Datasource Name
  public static final String DATASOURCE_NAME = "java:comp/env/jdbc/addressbook";
}
