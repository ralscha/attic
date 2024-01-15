package ch.ess.cal.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author sr
 */
public abstract class AbstractForm extends ValidatorForm {

  private String modelId;
  private String version;

  public String getModelId() {
    return modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  protected Locale getLocale(final HttpServletRequest request) {
    return RequestUtils.getUserLocale(request, null);
  }

  protected MessageResources getMessages(final HttpServletRequest request) {
    return ((MessageResources)request.getAttribute(Globals.MESSAGES_KEY));
  }

  protected String translate(final HttpServletRequest request, final String key) {
    return getMessages(request).getMessage(getLocale(request), key);
  }

}
