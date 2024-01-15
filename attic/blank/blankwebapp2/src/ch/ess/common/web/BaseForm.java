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
 * @version $Revision: 1.4 $ $Date: 2004/05/22 16:51:14 $
 */
public class BaseForm extends ValidatorForm {

  private Locale locale;
  private MessageResources messages;

  public void initForm() {
    HttpServletRequest request = WebContext.currentContext().getRequest();
    locale = RequestUtils.getUserLocale(request, null);
    messages = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
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

}