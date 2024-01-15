package ch.ess.cal.web.contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.base.Constants;
import ch.ess.base.web.AbstractForm;

public class ContactForm extends AbstractForm {

  private Map<String, Object> valueMap = new HashMap<String, Object>();
  private boolean showMap;
  private boolean showHomeMap;
  private String tabset;
  private boolean privateContact;
  private String googleApiKey;

  public ContactForm() {
    tabset = "generalTab";
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public Map<String, Object> getValueMap() {
    return valueMap;
  }

  public boolean isShowMap() {
    return showMap;
  }

  public void setShowMap(boolean showMap) {
    this.showMap = showMap;
  }

  public boolean isShowHomeMap() {
    return showHomeMap;
  }

  public void setShowHomeMap(boolean showHomeMap) {
    this.showHomeMap = showHomeMap;
  }

  public String getGoogleApiKey() {
    return googleApiKey;
  }

  public void setGoogleApiKey(String googleApiKey) {
    this.googleApiKey = googleApiKey;
  }

  public boolean isPrivateContact() {
    return privateContact;
  }

  public void setPrivateContact(boolean privateContact) {
    this.privateContact = privateContact;
  }

  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    if (request.getParameter("btnSearch.x") != null) {
      valueMap.clear();
    }
    privateContact = false;
  }

  public void setValueMap(final Map<String, Object> valueMap) {
    this.valueMap = valueMap;
  }

  public void setValue(final String key, final Object value) {

    Object tmpVal = value;

    if (tmpVal instanceof String) {
      if (StringUtils.isBlank((String)tmpVal)) {
        tmpVal = null;
      }
    }
    getValueMap().put(key, tmpVal);
  }

  public Object getValue(final String key) {
    return getValueMap().get(key);
  }

  public String getStringValue(final String key) {
    return (String)getValue(key);
  }

  @Override
  public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    String birthDay = getStringValue("birthday");
    if (StringUtils.isNotBlank(birthDay)) {
      SimpleDateFormat format = new SimpleDateFormat(Constants.getParseDateFormatPattern());
      format.setLenient(false);
      format.setTimeZone(Constants.UTC_TZ);

      try {
        format.parse(birthDay);
      } catch (ParseException pe) {
        errors.add("value(birthday)", new ActionMessage("errors.date", translate(request, "contact.birthday")));
      }
    }

    String email = getStringValue("email");
    if (StringUtils.isNotBlank(email)) {
      if (!GenericValidator.isEmail(email)) {
        errors.add("value(email)", new ActionMessage("errors.email", translate(request, "contact.email")));
      }
    }

    if (errors.isEmpty()) {
      return null;
    }
    return errors;

  }

}