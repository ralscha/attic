package ch.ess.common.web;

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
public abstract class BaseAction extends Action {

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

  protected void addOneErrorRequest(ActionMessage err) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, err);
    saveErrors(WebContext.currentContext().getRequest(), messages);
  }

  protected void addOneMessageRequest(ActionMessage message) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
    saveMessages(WebContext.currentContext().getRequest(), messages);
  }

  protected void addOneMessageSession(ActionMessage message) {
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
    saveMessages(WebContext.currentContext().getSession(), messages);
  }

  protected ActionForward findForward(String key) {
    return WebContext.currentContext().getMapping().findForward(key);
  }

}