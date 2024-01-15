package ch.ess.common;

import org.apache.struts.action.ActionMessage;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class Constants {

  //Session Keys
  public static final String CONCURRENT_ID = "concurrent.id";
  public static final String OBJECT_ID = "id";
  public static final String RESULT_ID = "result";

  //STRUTS  

  //Forward
  public static final String FORWARD_SUCCESS = "success";
  public static final String FORWARD_LIST = "list";
  public static final String FORWARD_EDIT = "edit";
  public static final String FORWARD_RELOAD = "reload";

  //ActionMessages
  public static final ActionMessage ACTION_MESSAGE_DELETE_OK = new ActionMessage("DeleteOK");
  public static final ActionMessage ACTION_MESSAGE_CONCURRENT = new ActionMessage("ConcurrentAccess");
  public static final ActionMessage ACTION_MESSAGE_UPDATE_OK = new ActionMessage("UpdateOK");
  public static final ActionMessage ACTION_MESSAGE_NO_RESULTS = new ActionMessage("NoResultsfound");

  //Lucene
  public static final String SEARCH_ID = "id";
  public static final String SEARCH_TEXT = "text";

}
