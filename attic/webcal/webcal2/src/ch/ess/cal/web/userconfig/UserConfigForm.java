package ch.ess.cal.web.userconfig;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.struts.validator.ValidatorForm;

import ch.ess.cal.db.User;
import ch.ess.cal.resource.AppConfig;
import ch.ess.cal.resource.UserConfig;
import ch.ess.cal.web.EmailUtil;
import ch.ess.cal.web.WebUtils;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.util.Util;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:53 $
  *  
  * @struts.form name="userConfigForm"
  */
public class UserConfigForm extends ValidatorForm {

  private static final Log LOG = LogFactory.getLog(UserConfigForm.class);

  private String name;
  private String shortName;
  private String firstName;  

  private String timeUnit;
  private int time;

  private String oldPassword;
  private String newPassword;
  private String retypeNewPassword;
  private Integer firstDayOfWeek;

  private String locale;
  private String timezone;

  private List emails;
  private String email;
  
  private int overviewPicWidth;
  private int overviewPicHeight;

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
   * @struts.validator-args arg0resource="userconfig.newPassword" arg1resource="user.retypePassword"
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
   * @struts.validator-args arg0resource="userconfig.loginRemember"
   */
  public void setTime(int i) {
    time = i;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.language"
   */
  public void setLocale(String string) {
    locale = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="userconfig.loginRemember"
   */
  public void setTimeUnit(String string) {
    timeUnit = string;
  }
  
  
  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.lastName"
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.firstName"
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }


  
  public Integer getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="userconfig.firstDayOfWeek"
   */
  public void setFirstDayOfWeek(Integer i) {
    firstDayOfWeek = i;
  }  


  public String getTimezone() {
    return timezone;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="userconfig.timezone"
   */
  public void setTimezone(String string) {
    timezone = string;
  }  
  
  
  public int getOverviewPicHeight() {
    return overviewPicHeight;
  }

  public int getOverviewPicWidth() {
    return overviewPicWidth;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-args arg0resource="userconfig.overviewPicHeight"
   */
  public void setOverviewPicHeight(int i) {
    overviewPicHeight = i;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-args arg0resource="userconfig.overviewPicWidth"
   */
  public void setOverviewPicWidth(int i) {
    overviewPicWidth = i;
  }  


  public String getShortName() {
    return shortName;
  }

  public void setShortName(String string) {
    shortName = string;
  }

  /**   
   * @struts.validator type="email"
   * @struts.validator-args arg0resource="user.eMail"
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
  
  public List getEmails() {
    if (emails == null) {
      emails = new ArrayList();
    }
    return emails;
  }

  public void setEmails(List list) {
    emails = list;
  }  

  public void toForm(User user) throws HibernateException {


    UserConfig config = UserConfig.getUserConfig(user);
    setTime(config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_NO).intValue());
    setTimeUnit(config.getStringProperty(UserConfig.LOGIN_REMEMBER_UNIT));
    setFirstDayOfWeek(config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, new Integer(Calendar.MONDAY)));
    setOverviewPicHeight(config.getIntegerProperty(UserConfig.OVERVIEW_PIC_HEIGHT, 26));
    setOverviewPicWidth(config.getIntegerProperty(UserConfig.OVERVIEW_PIC_WIDTH, 24));
    

    Locale loc = user.getLocale();
    setLocale(loc.toString());
    setTimezone(user.getTimeZone());
    
    
    setName(user.getName());
    setFirstName(user.getFirstName());

    setShortName(user.getShortName());
    
    emails = EmailUtil.getEmails(user);
    
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
    config.storeProperty(UserConfig.OVERVIEW_PIC_HEIGHT, overviewPicHeight);
    config.storeProperty(UserConfig.OVERVIEW_PIC_WIDTH, overviewPicWidth);
    
    user.setLocale(Util.getLocale(getLocale()));
    user.setTimeZone(timezone);
    
    user.setName(getName());
    user.setFirstName(getFirstName());

    if (!GenericValidator.isBlankOrNull(getShortName())) {
      user.setShortName(getShortName());
    } else {
      user.setShortName(null);
    }
    
    EmailUtil.addEmail(user, getEmails());

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
        
        if (emails.isEmpty() && (request.getParameter("change.addemail") == null)) {
          errors.add("emails", Constants.ACTION_MESSAGE_FILL_ALL);
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
