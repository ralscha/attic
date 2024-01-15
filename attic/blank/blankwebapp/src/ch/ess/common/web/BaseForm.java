package ch.ess.common.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.8 $ $Date: 2004/05/22 12:24:23 $
 */
public class BaseForm extends ValidatorForm {

  private Locale locale;
  private MessageResources messages;
  private String requestUserName;

  public void initForm() {
    HttpServletRequest request = WebContext.currentContext().getRequest();
    locale = RequestUtils.getUserLocale(request, null);
    messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    requestUserName = request.getUserPrincipal().getName();
  }

  //Utility method
  protected String getTrimmedString(String value) {
    if (!GenericValidator.isBlankOrNull(value)) {
      return value.trim();
    }
    return null;

  }

  protected Locale getLocale() {
    return locale;
  }

  protected MessageResources getMessages() {
    return messages;
  }

  protected String getRequestUserName() {
    return requestUserName;
  }

}