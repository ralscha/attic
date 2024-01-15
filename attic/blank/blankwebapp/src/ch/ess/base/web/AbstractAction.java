package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:13 $
 */
public abstract class AbstractAction extends Action {

  @Override
  public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    WebContext.init(request, response, getServlet().getServletContext(), mapping, form);
    try {
      return execute();
    } finally {
      WebContext.destroy();
    }

  }
  
  public abstract ActionForward execute() throws Exception;
  

  protected void addOneErrorRequest(String errorKey) {
    addOneErrorRequest(new ActionMessage(errorKey));
  }
  
  protected void addOneErrorRequest(ActionMessage err) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, err);
    saveErrors(WebContext.currentContext().getRequest(), messages);
  }

  
  protected void addOneErrorSession(String errorKey) {
    addOneErrorSession(new ActionMessage(errorKey));
  }
  
  protected void addOneErrorSession(ActionMessage err) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, err);
    saveErrors(WebContext.currentContext().getSession(), messages);
  }
  
  
  
  protected void addOneMessageRequest(String messageKey) {
    addOneMessageRequest(new ActionMessage(messageKey));
  }  
  
  protected void addOneMessageRequest(ActionMessage message) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
    saveMessages(WebContext.currentContext().getRequest(), messages);
  }

  
  protected void addOneMessageSession(String messageKey) {
    addOneMessageSession(new ActionMessage(messageKey));
  }    
  
  protected void addOneMessageSession(ActionMessage message) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
    saveMessages(WebContext.currentContext().getSession(), messages);
  }


}