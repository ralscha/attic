package ch.ess.base.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.ValidatorForm;

public abstract class AbstractForm extends ValidatorForm {

  private String modelId;
  private String version;
  private boolean deletable;

  public String getModelId() {
    return modelId;
  }

  public void setModelId(final String modelId) {
    this.modelId = modelId;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  public boolean isDeletable() {
    return deletable;
  }

  public void setDeletable(final boolean deletable) {
    this.deletable = deletable;
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
