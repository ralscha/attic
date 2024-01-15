package ch.ess.blank.web.userconfig;

import java.util.Calendar;
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
import org.apache.struts.validator.ValidatorForm;

import ch.ess.blank.db.User;
import ch.ess.blank.resource.AppConfig;
import ch.ess.blank.resource.UserConfig;
import ch.ess.blank.web.WebUtils;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.util.Util;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.16 $ $Date: 2004/05/22 15:38:30 $
 *  
 * @struts.form name="userConfigForm"
 */
public class UserConfigForm extends ValidatorForm {

  private static final Log LOG = LogFactory.getLog(UserConfigForm.class);

  private String lastName;
  private String firstName;
  private String email;

  private String timeUnit;
  private int time;

  private String oldPassword;
  private String newPassword;
  private String retypeNewPassword;
  private Integer firstDayOfWeek;

  private String locale;

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

  public String getLocale() {
    return locale;
  }

  public String getTimeUnit() {
    return timeUnit;
  }

  /**   
   * @struts.validator type="requiredif,identical"
   * @struts.validator-arg position="0" key="userconfig.newPassword"
   * @struts.validator-arg position="1" key="user.retypePassword"
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
   * @struts.validator-arg position="0" key="userconfig.loginRemember"
   */
  public void setTime(int i) {
    time = i;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.language"
   */
  public void setLocale(String string) {
    locale = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userconfig.loginRemember"
   */
  public void setTimeUnit(String string) {
    timeUnit = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.lastName"
   */
  public void setLastName(String name) {
    this.lastName = name;
  }

  public String getLastName() {
    return lastName;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.firstName"
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  /**   
   * @struts.validator type="required,email"
   * @struts.validator-arg position="0" key="user.eMail"
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public Integer getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userconfig.firstDayOfWeek"
   */
  public void setFirstDayOfWeek(Integer i) {
    firstDayOfWeek = i;
  }

  public void toForm(User user) throws HibernateException {

    UserConfig config = UserConfig.getUserConfig(user);
    setTime(config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_NO).intValue());
    setTimeUnit(config.getStringProperty(UserConfig.LOGIN_REMEMBER_UNIT));
    setFirstDayOfWeek(config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, new Integer(Calendar.MONDAY)));

    Locale loc = user.getLocale();
    setLocale(loc.toString());

    setLastName(user.getName());
    setFirstName(user.getFirstName());
    setEmail(user.getEmail());

  }

  public void fromForm(User user) throws HibernateException {
    UserConfig config = UserConfig.getUserConfig(user);

    config.storeProperty(UserConfig.LOGIN_REMEMBER_NO, getTime());
    config.storeProperty(UserConfig.LOGIN_REMEMBER_UNIT, getTimeUnit());

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
    config.storeProperty(UserConfig.FIRST_DAY_OF_WEEK, firstDayOfWeek);

    user.setLocale(Util.getLocale(getLocale()));

    user.setName(getLastName());
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
        errors.add("newPassword", new ActionMessage("userconfig.passwordTooShort"));
      }

      Transaction tx = null;
      try {
        tx = HibernateSession.currentSession().beginTransaction();

        User user = WebUtils.getUser(request);

        if (user != null) {
          if (!PasswordDigest.validatePassword(user.getPasswordHash(), oldPassword)) {
            errors.add("oldPassword", new ActionMessage("userconfig.oldPasswordNotCorrect"));
          }
        }

        tx.commit();

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