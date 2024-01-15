package ch.ess.cal.web.userconfig;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.model.User;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.impl.AppConfig;
import ch.ess.cal.web.AbstractForm;
import ch.ess.cal.web.DynaListDataModel;

import com.cc.framework.ui.control.SimpleListControl;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/24 05:07:09 $
 *  
 * @struts.form name="userConfigForm"
 */
public class UserConfigForm extends AbstractForm {

  private String lastName;
  private String firstName;
  private String shortName;

  private String timeUnit;
  private String time;

  private String oldPassword;
  private String newPassword;
  private String retypeNewPassword;
  private String firstDayOfWeek;
  private String minDaysInFirstWeek;

  private String localeStr;
  private String noRows;
  private String timezone;

  private SimpleListControl emailList;
  private String addEmail;

  private String rememberMeValidUntil;

  private String tabset = "general";

  private boolean highlightWeekends;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    oldPassword = null;
    newPassword = null;
    retypeNewPassword = null;
    highlightWeekends = false;
  }

  public boolean isHighlightWeekends() {
    return highlightWeekends;
  }

  public void setHighlightWeekends(boolean highlightWeekends) {
    this.highlightWeekends = highlightWeekends;
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

  public String getTimezone() {
    return timezone;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userconfig.timezone"
   */
  public void setTimezone(final String string) {
    timezone = string;
  }

  /**   
   * @struts.validator type="identical"
   * @struts.validator-arg position="0" key="userconfig.newPassword"
   * @struts.validator-arg position="1" key="user.retypePassword"
   * @struts.validator-var name="secondProperty" value="retypeNewPassword"
   */
  public void setNewPassword(final String string) {
    newPassword = string;
  }

  public void setOldPassword(final String string) {
    oldPassword = string;
  }

  public void setRetypeNewPassword(final String string) {
    retypeNewPassword = string;
  }

  /**   
   * @struts.validator type="required,integer,positive"
   * @struts.validator-arg position="0" key="userconfig.loginRemember"
   */
  public void setTime(final String str) {
    time = str;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.language"
   */
  public void setLocaleStr(final String string) {
    localeStr = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userconfig.loginRemember"
   */
  public void setTimeUnit(final String string) {
    timeUnit = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.lastName"
   */
  public void setLastName(final String name) {
    this.lastName = name;
  }

  public String getLastName() {
    return lastName;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.firstName"
   */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userconfig.firstDayOfWeek"
   */
  public void setFirstDayOfWeek(final String firstDay) {
    firstDayOfWeek = firstDay;
  }

  public String getMinDaysInFirstWeek() {
    return minDaysInFirstWeek;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-arg position="0" key="userconfig.minDaysInFirstWeek"
   */
  public void setMinDaysInFirstWeek(String minDaysInFirstWeek) {
    this.minDaysInFirstWeek = minDaysInFirstWeek;
  }

  public String getRememberMeValidUntil() {
    return rememberMeValidUntil;
  }

  public void setRememberMeValidUntil(final String rememberMeValidUntil) {
    this.rememberMeValidUntil = rememberMeValidUntil;
  }

  /**   
   * @struts.validator type="email"
   * @struts.validator-arg position="0" key="user.eMail"
   */
  public void setAddEmail(final String email) {
    this.addEmail = email;
  }

  public String getAddEmail() {
    return addEmail;
  }

  public SimpleListControl getEmailList() {
    return emailList;
  }

  public void setEmailList(SimpleListControl emailList) {
    this.emailList = emailList;
  }

  public String getNoRows() {
    return noRows;
  }

  /**   
   * @struts.validator type="required,integer,positive"
   * @struts.validator-arg position="0" key="userconfig.noRows"
   */
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

    if (GenericValidator.isBlankOrNull(getAddEmail())) {
      DynaListDataModel dataModel = (DynaListDataModel)getEmailList().getDataModel();
      if (dataModel.size() == 0) {
        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("errors.required", translate(request, "user.eMail")));
      }
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
