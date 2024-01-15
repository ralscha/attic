package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Util;

public class HelpMessage {
  public String getMessage(String key, HttpServletRequest request) {    
    MessageResources messages = ((MessageResources) request.getSession().getServletContext().getAttribute(Globals.MESSAGES_KEY));    
    String message = messages.getMessage(Util.getLocale(request), key);
    if (message != null) {
      return message;
    }
    return key;
  }
}
