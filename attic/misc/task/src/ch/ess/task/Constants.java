package ch.ess.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:28 $  
  */
public class Constants {

  public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  public static final DateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
  public static final DateFormat PARSE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");
  
  
  //Cookie
  public static final String COOKIE_REMEMBER = "task_remember";

  //Datasource Name
  public static final String DATASOURCE_NAME = "java:comp/env/jdbc/task";
}
