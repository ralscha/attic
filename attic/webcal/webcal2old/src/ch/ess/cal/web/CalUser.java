package ch.ess.cal.web;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import ch.ess.cal.db.Permission;
import ch.ess.cal.db.User;
import ch.ess.cal.db.UserGroup;
import ch.ess.cal.resource.UserConfig;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;


public class CalUser {

  private Long userId;
  private Set permissions;  
  private Locale locale;
  private String name, firstName;
  private UserConfig config;  
  private TimeZone timezone;

  private CalUser(User user) {
    userId = user.getUserId();
    name = user.getName();
    firstName = user.getFirstName();
    
    setPermissions(user);  
    config = UserConfig.getUserConfig(user); 
    locale = user.getLocale(); 
    
    timezone = TimeZone.getTimeZone(config.getStringProperty(UserConfig.TIMEZONE));

  }

  public Calendar getTodayCalendar() {
    Calendar cal = new GregorianCalendar(timezone);
    cal.setMinimalDaysInFirstWeek(config.getIntegerProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK).intValue());
    cal.setFirstDayOfWeek(config.getIntegerProperty(UserConfig.START_OF_WEEK).intValue());    
    return cal;
  }

  public static CalUser createUser(User user) {

    if (user == null) {
      return null;
    }

    CalUser newUser = new CalUser(user);    
      
    return newUser;
  }

  private void setPermissions(User user) {
    
    UserGroup group = user.getUserGroup();
    if (group != null) {
      Set perms = group.getPermissions();
      if (perms != null) {
        permissions = new HashSet();
        for (Iterator iter = perms.iterator(); iter.hasNext();) {
          Permission perm = (Permission)iter.next();
          permissions.add(perm.getName());
        }
      }
    }
    
  }

  public Long getUserId() {
    return userId;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getName() {
    return name;
  }

  public UserConfig getConfig() {
    return config;
  } 
 
  public TimeZone getTimezone() {
    return timezone;
  }

  public void setTimezone(TimeZone zone) {
    timezone = zone;
  } 
 
  public boolean hasPermission(String permission) {

    if (permissions != null) {
      return permissions.contains(permission);
    }

    return false;
  }

}