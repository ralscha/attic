package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class BaseAction extends Action {


  protected void addOneError(HttpServletRequest request, ActionMessage err) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, err);
    saveErrors(request, messages);
  }

  protected void addOneMessage(HttpServletRequest request, ActionMessage message) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionErrors.GLOBAL_MESSAGE, message);
    saveMessages(request, messages);
  }

}