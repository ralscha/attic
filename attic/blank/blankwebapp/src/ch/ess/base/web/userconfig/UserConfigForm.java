package ch.ess.base.web.userconfig;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.annotation.Validator;
import org.apache.struts.annotation.ValidatorField;
import org.apache.struts.annotation.Variable;

import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractForm;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/07/03 10:25:04 $
 */
public class UserConfigForm extends AbstractForm {

  private String name;
  private String firstName;
  
  private String timeUnit;
  private String time;

  private String oldPassword;
  private String newPassword;
  private String retypeNewPassword;
  private String firstDayOfWeek;

  private String localeStr;
  private String noRows;

  private String email;

  private String rememberMeValidUntil;

  private String tabset = "general";
  

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    oldPassword = null;
    newPassword = null;
    retypeNewPassword = null;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public String getRetypeNewPassword() {
    return retypeNewPassword;
  }

  public String getTime() {
    return time;
  }

  public String getLocaleStr() {
    return localeStr;
  }

  public String getTimeUnit() {
    return timeUnit;
  }

  @ValidatorField(key="userconfig.newPassword", 
                  validators=@Validator(name="identical",                              
                                vars=@Variable(key="user.retypePassword", name="secondProperty", value="retypeNewPassword"))                   
                  )  
  public void setNewPassword(final String string) {
    newPassword = string;
  }

  public void setOldPassword(final String string) {
    oldPassword = string;
  }

  public void setRetypeNewPassword(final String string) {
    retypeNewPassword = string;
  }

  @ValidatorField(key="userconfig.loginRemember", required=true, validators= {@Validator(name="integer"),@Validator(name="positive")})
  public void setTime(final String str) {
    time = str;
  }

  @ValidatorField(key="user.language", required=true) 
  public void setLocaleStr(final String string) {
    localeStr = string;
  }

  @ValidatorField(key="userconfig.loginRemember", required=true) 
  public void setTimeUnit(final String string) {
    timeUnit = string;
  }

  @ValidatorField(key="user.name", required=true) 
  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getFirstName() {
    return firstName;
  }

  @ValidatorField(key="user.firstName", required=true) 
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }  

  public String getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  @ValidatorField(key="userconfig.firstDayOfWeek", required=true) 
  public void setFirstDayOfWeek(final String firstDay) {
    firstDayOfWeek = firstDay;
  }


  public String getRememberMeValidUntil() {
    return rememberMeValidUntil;
  }

  public void setRememberMeValidUntil(final String rememberMeValidUntil) {
    this.rememberMeValidUntil = rememberMeValidUntil;
  }

  public String getEmail() {
    return email;
  }

  @ValidatorField(key="user.eMail", required=true, validators=@Validator(name="email"))  
  public void setEmail(String email) {
    this.email = email;
  }

  public String getNoRows() {
    return noRows;
  }

  @ValidatorField(key="userconfig.noRows", required=true, validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setNoRows(final String noRows) {
    this.noRows = noRows;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public ActionErrors validate(final User user, final Config appConfig, final ActionMapping mapping,
      final HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }


    if (!GenericValidator.isBlankOrNull(newPassword)) {

      if (!GenericValidator.isBlankOrNull(newPassword)) {
        if (newPassword.length() < appConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0)) {
          errors.add("newPassword", new ActionMessage("userconfig.passwordTooShort"));
        }
      }

      if (user != null) {
        if (!validatePassword(user.getPasswordHash(), oldPassword)) {
          errors.add("oldPassword", new ActionMessage("userconfig.oldPasswordNotCorrect"));
        }
      }

    }

    return errors;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  private boolean validatePassword(final String hashPassword, final String clearTextPassword) {

    if (clearTextPassword == null) {
      return false;
    }

    String digest = DigestUtils.shaHex(clearTextPassword);
    return digest.equals(hashPassword);
  }


}