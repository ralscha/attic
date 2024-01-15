package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import ch.ess.base.Util;

@RemoteProxy(name = "helpMessage")
public class HelpMessage {
  
  @RemoteMethod
  public String getMessage(String key, HttpServletRequest request) {    
    MessageResources messages = ((MessageResources)request.getSession().getServletContext().getAttribute(
        Globals.MESSAGES_KEY));
    String message = messages.getMessage(Util.getLocale(request), key);
    if (message != null) {
      return message;
    }
    return key;
  }
}
