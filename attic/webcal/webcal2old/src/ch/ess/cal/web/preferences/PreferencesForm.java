package ch.ess.cal.web.preferences;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.ValidatorForm;

import ch.ess.cal.Constants;
import ch.ess.cal.db.User;
import ch.ess.cal.resource.HibernateManager;
import ch.ess.cal.resource.UserConfig;
import ch.ess.cal.util.PasswordDigest;
import ch.ess.cal.web.CalUser;

import com.ibm.icu.util.TimeZone;

public class PreferencesForm extends ValidatorForm {

  private static final Log logger = LogFactory.getLog(PreferencesForm.class);

  private int startOfWeek;  
  private String timezone;
  private String timeUnit;
  private int minimalDays;
  private int time;

  private List timeOptions;
  private List timezoneOptions;
  
  
  
  private String oldPassword;
  private String newPassword;
  private String retypeNewPassword;

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    startOfWeek = -1;
    timezone = null;
            
  }
    
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public int getStartOfWeek() {
    return startOfWeek;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setStartOfWeek(int i) {
    startOfWeek = i;
  }

  public void setTimezone(String string) {
    timezone = string;
  }

  public List getTimeOptions() {
    return timeOptions;
  }

  public int getTime() {
    return time;
  }

  public String getTimeUnit() {
    return timeUnit;
  }

  public void setTime(int string) {
    time = string;
  }

  public void setTimeUnit(String string) {
    timeUnit = string;
  }

  public List getTimezoneOptions() {
    return timezoneOptions;
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

  public void setNewPassword(String string) {
    newPassword = string;
  }

  public void setOldPassword(String string) {
    oldPassword = string;
  }

  public void setRetypeNewPassword(String string) {
    retypeNewPassword = string;
  }
  
  public int getMinimalDays() {
    return minimalDays;
  }

  public void setMinimalDays(int i) {
    minimalDays = i;
  }
  

  public void setToForm(MessageResources messages, CalUser user) {
    
    timeOptions = new TimeOptions(null, user.getLocale(), messages).getLabelValue();
    timezoneOptions = new TimezoneOptions(null, user.getLocale(), messages).getLabelValue();
    
    UserConfig config = user.getConfig();
    setStartOfWeek(config.getIntegerProperty(UserConfig.START_OF_WEEK).intValue());
    setTimezone(config.getStringProperty(UserConfig.TIMEZONE));
    setTime(config.getIntegerProperty(UserConfig.LOGON_REMEMBER_NO).intValue());
    setTimeUnit(config.getStringProperty(UserConfig.LOGON_REMEMBER_UNIT));
    setMinimalDays(config.getIntegerProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK).intValue()); 
  }
  
  public void getFromForm(CalUser user) {
    UserConfig config = user.getConfig();
    
    config.storeProperty(UserConfig.START_OF_WEEK, getStartOfWeek());
    config.storeProperty(UserConfig.TIMEZONE, getTimezone());
    user.setTimezone(TimeZone.getTimeZone(getTimezone()));
    
    config.storeProperty(UserConfig.LOGON_REMEMBER_NO, getTime());
    config.storeProperty(UserConfig.LOGON_REMEMBER_UNIT, getTimeUnit());
    
    
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
    }
    
    config.storeProperty(UserConfig.LOGON_REMEMBER_SECONDS, seconds);
    config.storeProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK, minimalDays);
  }


  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    
    if (!GenericValidator.isBlankOrNull(newPassword)) {
      Session sess = null;
      Transaction tx = null;
      try {
        sess = HibernateManager.open(request);
        tx = sess.beginTransaction(); 
        
        CalUser calUser = (CalUser)request.getSession().getAttribute(Constants.USER_KEY);
        User user = (User)sess.load(User.class, calUser.getUserId());
        
        if (!PasswordDigest.validatePassword(user.getPasswordHash(), oldPassword)) {
          errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("oldPasswordNotCorrect"));
        }
                
      } catch (HibernateException e) {
        HibernateManager.exceptionHandling(tx); 
        logger.error("validate", e);       
      } finally {
        HibernateManager.finallyHandling(sess);
      }
    }    
    
    if (errors.isEmpty()) {
      return null;
    } else {
      return errors;
    }
  }



}
