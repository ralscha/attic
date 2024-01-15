package ch.ess.addressbook.web.userconfig;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.ValidatorForm;

import ch.ess.addressbook.db.User;
import ch.ess.addressbook.resource.AppConfig;
import ch.ess.addressbook.resource.UserConfig;
import ch.ess.addressbook.web.LocaleOptions;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.util.Util;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * @struts.form name="userConfigForm"
  */
public class UserConfigForm extends ValidatorForm {

  private static final Log LOG = LogFactory.getLog(UserConfigForm.class);

  private String name;
  private String firstName;
  private String email;
  private int noRows;

  private String timeUnit;
  private int time;

  private String oldPassword;
  private String newPassword;
  private String retypeNewPassword;

  private String locale;

  private List localeOptions;
  private List timeOptions;

  public String getNewPassword() {
    return newPassword;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public String getRetypeNewPassword() {
    return retypeNewPassword;
  }

  public int getTime() {
    return time;
  }

  public List getTimeOptions() {
    return timeOptions;
  }

  public List getLocaleOptions() {
    return localeOptions;
  }

  public String getLocale() {
    return locale;
  }

  public String getTimeUnit() {
    return timeUnit;
  }

  /**   
   * @struts.validator type="requiredif,identical"
   * @struts.validator-args arg0resource="NewPassword" arg1resource="RetypePassword"
   * @struts.validator-var name="secondProperty" value="retypeNewPassword"
   * @struts.validator-var name="field[0]" value="oldPassword" 
   * @struts.validator-var name="fieldTest[0]" value="NOTNULL"
   */
  public void setNewPassword(String string) {
    newPassword = string;
  }

  public void setOldPassword(String string) {
    oldPassword = string;
  }

  public void setRetypeNewPassword(String string) {
    retypeNewPassword = string;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-args arg0resource="LoginRemember"
   */
  public void setTime(int i) {
    time = i;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="Language"
   */
  public void setLocale(String string) {
    locale = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="LoginRemember"
   */
  public void setTimeUnit(String string) {
    timeUnit = string;
  }
  
  
  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="LastName"
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="FirstName"
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  /**   
   * @struts.validator type="required,email"
   * @struts.validator-args arg0resource="EMail"
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
  

  public int getNoRows() {
    return noRows;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-args arg0resource="NoRows"
   */
  public void setNoRows(int i) {
    noRows = i;
  }
  

  public void toForm(MessageResources messages, User user) throws HibernateException {

    timeOptions = new TimeOptions(user.getLocale(), messages).getLabelValue();

    UserConfig config = UserConfig.getUserConfig(user);
    setTime(config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_NO).intValue());
    setTimeUnit(config.getStringProperty(UserConfig.LOGIN_REMEMBER_UNIT));
    setNoRows(config.getIntegerProperty(UserConfig.NO_ROWS, 10));

    Locale loc = user.getLocale();
    setLocale(loc.toString());
    localeOptions = new LocaleOptions(loc, messages).getLabelValue();
    
    setName(user.getName());
    setFirstName(user.getFirstName());
    setEmail(user.getEmail());
    
  }

  public void fromForm(User user) throws HibernateException {
    UserConfig config = UserConfig.getUserConfig(user);

    config.storeProperty(UserConfig.LOGIN_REMEMBER_NO, getTime());
    config.storeProperty(UserConfig.LOGIN_REMEMBER_UNIT, getTimeUnit());
    config.storeProperty(UserConfig.NO_ROWS, getNoRows());
    
    TimeEnum te = TimeEnum.getEnum(getTimeUnit());
    int seconds = 0;
    if (te.equals(TimeEnum.MINUTE)) {
      seconds = getTime() * 60;
    } else if (te.equals(TimeEnum.HOUR)) {
      seconds = getTime() * 60 * 60;
    } else if (te.equals(TimeEnum.DAY)) {
      seconds = getTime() * 60 * 60 * 24;
    } else if (te.equals(TimeEnum.WEEK)) {
      seconds = getTime() * 60 * 60 * 24 * 7;
    } else if (te.equals(TimeEnum.MONTH)) {
      seconds = getTime() * 60 * 60 * 24 * 31;
    } else if (te.equals(TimeEnum.YEAR)) {
      seconds = getTime() * 60 * 60 * 24 * 365;
    }

    config.storeProperty(UserConfig.LOGIN_REMEMBER_SECONDS, seconds);

    user.setLocale(Util.getLocale(getLocale()));
    
    user.setName(getName());
    user.setFirstName(getFirstName());
    user.setEmail(getEmail());

  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    if (!GenericValidator.isBlankOrNull(newPassword)) {
      if (newPassword.length() < AppConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0)) {
        errors.add("newPassword", new ActionMessage("passwordTooShort"));
      }

      Transaction tx = null;
      try {
        tx = HibernateSession.currentSession().beginTransaction();

        String userName = request.getUserPrincipal().getName();

        User user = User.find(userName);
        if (user != null) {
          if (!PasswordDigest.validatePassword(user.getPasswordHash(), oldPassword)) {
            errors.add("oldPassword", new ActionMessage("oldPasswordNotCorrect"));
          }
        }

      } catch (HibernateException e) {
        HibernateSession.rollback(tx);
        LOG.error("validate", e);
      } finally {
        HibernateSession.closeSession();
      }
    }

    return errors;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
