package ch.ess.cal;

import java.util.Locale;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessage;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;

public class Constants {

  //Locale
  public final static Locale LOCALE_EN = new Locale("en", "");

  //Session Keys
  public final static String CONCURRENT_ID = "concurrent.id";
  public final static String OBJECT_ID = "id";
  public final static String RESULT_ID = "result";
  
  public final static String ADMIN_USER_KEY = "ch.ess.cal.ADMIN_USER";
  public final static String ADMIN_USER_SESSION = "ch.ess.cal.ADMIN_USER_SESSION";
  
  public final static String USER_KEY = "ch.ess.cal.USER";
  public final static String USER_SESSION = "ch.ess.cal.USER_SESSION";  
  
  //Cookie
  public final static String COOKIE_REMEMBER = "remember";
  
  //Forward
  public final static String FORWARD_SUCCESS = "success";
  public final static String FORWARD_CANCEL = "cancel";
  public final static String FORWARD_RELOAD = "reload";
  public final static String FORWARD_SAVEANDBACK = "saveandback";
  public final static String FORWARD_LOGON = "logon";
  
  //ActionMessages
  public final static ActionMessage ACTION_MESSAGE_DELETE_OK = new ActionMessage("DeleteOK");
  public final static ActionMessage ACTION_MESSAGE_CONCURRENT = new ActionError("ConcurrentAccess");
  public final static ActionMessage ACTION_MESSAGE_UPDATE_OK = new ActionMessage("UpdateOK");
  public final static ActionMessage ACTION_MESSAGE_NO_RESULTS = new ActionError("NoResultsfound");
  
  
  //User Properties
  public final static String USER_PROPERTIES = "logon";
  
  
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
    
}
