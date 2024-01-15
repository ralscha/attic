package ch.ess.base.web.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.annotation.struts.Variable;
import ch.ess.base.dao.UserDao;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractForm;
import ch.ess.base.web.SimpleListDataModel;


public class UserForm extends AbstractForm {

  public static final String FAKE_PASSWORD = "hasPassword";

  private String loginPassword;
  private String retypePassword;
  private String[] userGroupIds;
  private String localeStr;
  private boolean cookieLogin;

  private String loginName;
  private String name;
  private String firstName;
  private String shortName;
  private String addEmail;
  private String timezone;
  private String tabset = "general";
  private boolean enabled;

  private EmailFormListControl emailList;

  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    userGroupIds = null;
    enabled = false;
  }

  public String getTimezone() {
    return timezone;
  }

  @ValidatorField(key = "userconfig.timezone", required = true)
  public void setTimezone(final String string) {
    timezone = string;
  }

  @ValidatorField(key = "login.userName", required = true)
  public void setLoginName(final String userName) {
    this.loginName = userName;
  }

  public String getLoginName() {
    return loginName;
  }

  public String getFirstName() {
    return firstName;
  }

  @ValidatorField(key = "user.firstName", required = true)
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @ValidatorField(key = "user.name", required = true)
  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @ValidatorField(key = "user.eMail", required = false, validators = @Validator(name = "email"))
  public void setAddEmail(final String email) {
    this.addEmail = email;
  }

  public String getAddEmail() {
    return addEmail;
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

  @ValidatorField(key = "login.password", validators = 
      @Validator(name = "identical", vars = @Variable(key = "user.retypePassword", name = "secondProperty", value = "retypePassword")))
  public void setLoginPassword(final String string) {
    loginPassword = string;
  }

  public void setRetypePassword(final String string) {
    retypePassword = string;
  }

  public String[] getUserGroupIds() {
    return (String[])ArrayUtils.clone(userGroupIds);
  }

  public void setUserGroupIds(final String[] ids) {
    userGroupIds = (String[])ArrayUtils.clone(ids);
  }

  public String getLocaleStr() {
    return localeStr;
  }

  @ValidatorField(key = "user.language", required = true)
  public void setLocaleStr(final String string) {
    localeStr = string;
  }

  public boolean isCookieLogin() {
    return cookieLogin;
  }

  public void setCookieLogin(final boolean cookieLogin) {
    this.cookieLogin = cookieLogin;
  }

  public EmailFormListControl getEmailList() {
    return emailList;
  }

  public void setEmailList(EmailFormListControl emailList) {
    this.emailList = emailList;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public ActionErrors validate(final Config appConfig, final UserDao userDao, final ActionMapping mapping,
      final HttpServletRequest request) {

    if (StringUtils.isBlank(getAddEmail())) {

      ActionErrors errors = super.validate(mapping, request);
      if (errors == null) {
        errors = new ActionErrors();
      }

    if (!isCookieLogin()) {
      if (StringUtils.isNotBlank(getLoginPassword()) && !FAKE_PASSWORD.equals(getLoginPassword())) {

        if (getLoginPassword().length() < appConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0)) {
          errors.add("loginPassword", new ActionMessage("userconfig.passwordTooShort"));
        }
      }
    }

      SimpleListDataModel dataModel = (SimpleListDataModel)getEmailList().getDataModel();
      if (dataModel.size() == 0) {
        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("errors.required", translate(request, "user.eMail")));
      }

    if (errors.isEmpty()) {
      if (userDao.findExcludeId(getLoginName(), getModelId()) != null) {
        errors.add("loginName", new ActionMessage("error.username.exists"));
      }
    }

      return errors;
    }

    if (!GenericValidator.isEmail(getAddEmail())) {
      ActionErrors errors = new ActionErrors();
      errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.email", translate(request, "user.eMail")));
      return errors;
    }

    return null;

  }

}
