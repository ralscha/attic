package ch.ess.common;

import java.util.Locale;

import org.apache.struts.action.ActionMessage;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.12 $ $Date: 2003/11/11 19:07:18 $ 
  */
public class Constants {

  public static final Locale GERMAN_LOCALE = new Locale("de");
  public static final Locale ENGLISH_LOCALE = new Locale("en");

  //Session Keys
  public static final String CONCURRENT_ID = "concurrent.id";
  public static final String OBJECT_ID = "id";
  public static final String RESULT_ID = "result";
  
  public static final String CLIENT_INFO = "clientinfo";

  //STRUTS  

  //Forward
  public static final String FORWARD_SUCCESS = "success";
  public static final String FORWARD_LIST = "list";
  public static final String FORWARD_EDIT = "edit";
  public static final String FORWARD_RELOAD = "reload";

  //ActionMessages
  public static final ActionMessage ACTION_MESSAGE_DELETE_OK = new ActionMessage("common.deleteOK");
  public static final ActionMessage ACTION_MESSAGE_CONCURRENT = new ActionMessage("common.concurrentAccess");
  public static final ActionMessage ACTION_MESSAGE_UPDATE_OK = new ActionMessage("common.updateOK");
  public static final ActionMessage ACTION_MESSAGE_NO_RESULTS = new ActionMessage("common.noResultsfound");

  public static final ActionMessage ACTION_MESSAGE_FILL_ALL = new ActionMessage("error.fillallrequiredfields");

  //Lucene
  public static final String SEARCH_ID = "id";
  public static final String SEARCH_TEXT = "text";

}
