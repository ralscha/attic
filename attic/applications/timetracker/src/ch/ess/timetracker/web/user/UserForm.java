package ch.ess.timetracker.web.user;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.Util;
import ch.ess.common.web.PersistentForm;
import ch.ess.timetracker.db.Customer;
import ch.ess.timetracker.db.Role;
import ch.ess.timetracker.db.User;
import ch.ess.timetracker.db.UserRole;
import ch.ess.timetracker.resource.AppConfig;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:20 $
  *  
  * @struts.form name="userForm"
  */
public class UserForm extends PersistentForm {

  private static final Log LOG = LogFactory.getLog(UserForm.class);

  private static final String FAKE_PASSWORD = "hasPassword";

  private String password;
  private String retypePassword;
  private Long[] roleIds;
  private Long[] customerIds;
  private String localeStr;




  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="login.userName"
   */
  public void setUserName(String userName) {
    ((User)getPersistent()).setUserName(getTrimmedString(userName));
  }

  public String getUserName() {
    return ((User)getPersistent()).getUserName();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.lastName"
   */
  public void setName(String name) {
    ((User)getPersistent()).setName(getTrimmedString(name));
  }

  public String getName() {
    return ((User)getPersistent()).getName();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.firstName"
   */
  public void setFirstName(String firstName) {
    ((User)getPersistent()).setFirstName(getTrimmedString(firstName));
  }

  public String getFirstName() {
    return ((User)getPersistent()).getFirstName();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.shortName"
   */  
  public void setShortName(String shortName) {
    ((User)getPersistent()).setShortName(getTrimmedString(shortName));
  }

  public String getShortName() {
    return ((User)getPersistent()).getShortName();
  }  
  
  /**   
   * @struts.validator type="required,email"
   * @struts.validator-args arg0resource="user.eMail"
   */
  public void setEmail(String email) {
    ((User)getPersistent()).setEmail(getTrimmedString(email));
  }

  public String getEmail() {
    return ((User)getPersistent()).getEmail();
  }

  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }


  /**   
   * @struts.validator type="required,identical"
   * @struts.validator-args arg0resource="login.password" arg1resource="user.retypePassword"
   * @struts.validator-var name="secondProperty" value="retypePassword"
   */
  public void setPassword(String string) {
    password = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.retypePassword"
   */
  public void setRetypePassword(String string) {
    retypePassword = string;
  }

  public Long[] getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(Long[] ids) {
    roleIds = ids;
  }
   
  public Long[] getCustomerIds() {
    return customerIds;
  }
  
  public void setCustomerIds(Long[] customerIds) {
    this.customerIds = customerIds;
  }

  public String getLocaleStr() {
    return localeStr;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="user.language"
   */
  public void setLocaleStr(String string) {
    localeStr = string;
  }
  
  public String getHourRate() {
    BigDecimal bd = ((User)getPersistent()).getHourRate();
    if (bd != null) {
      return ch.ess.timetracker.Constants.DECIMAL_FORMAT.format(bd);    
    }
    
    return null;
  }

  /**   
   * @struts.validator type="float"
   * @struts.validator-args arg0resource="user.hourRate"  
   */
  public void setHourRate(String hourRate) {
    if (!GenericValidator.isBlankOrNull(hourRate)) {
      try {
        BigDecimal bd = new BigDecimal(hourRate);
        ((User)getPersistent()).setHourRate(bd);
      } catch (NumberFormatException e) {
        ((User)getPersistent()).setHourRate(null);
      }
    } else {
      ((User)getPersistent()).setHourRate(null);
    }
  }

        
  protected void fromForm() throws HibernateException {
    
    User user = (User)getPersistent();
    user.getUserRoles().clear();    

    if (roleIds != null) {
      Session sess = HibernateSession.currentSession();
      
      for (int i = 0; i < roleIds.length; i++) {
        if (roleIds[i].intValue() > 0) {
          Role r = (Role)sess.get(Role.class, roleIds[i]);          
          if (r != null) {            
            user.addRole(r);
          }
        }
      }
    }
    
    user.getCustomers().clear();
    if (customerIds != null) {
      Session sess = HibernateSession.currentSession();
      for (int i = 0; i < customerIds.length; i++) {
        if (customerIds[i].intValue() > 0) {
          Customer c = (Customer)sess.get(Customer.class, customerIds[i]);          
          if (c != null) {            
            user.getCustomers().add(c);
          }
        }
      }
      
    
    }

    if (!FAKE_PASSWORD.equals(getPassword())) {
      user.setPasswordHash(DigestUtils.shaHex(password));
    }

    user.setLocale(Util.getLocale(getLocaleStr()));

  }

  protected void toForm() throws HibernateException {
    
    User user = (User)getPersistent();
    
    if (user != null) {
      //ROLES
      Set roles = user.getUserRoles();
      if (!roles.isEmpty()) {
        roleIds = new Long[roles.size()];

        int ix = 0;
        for (Iterator it = roles.iterator(); it.hasNext();) {
          UserRole userRole = (UserRole)it.next();
          Hibernate.initialize(userRole.getRole());
          roleIds[ix++] = userRole.getRole().getId();
        }
      } else {
        roleIds = null;
      }
      
      //CUSTOMERS
      Set customers = user.getCustomers();
      if (!customers.isEmpty()) {
        customerIds = new Long[customers.size()];

        int ix = 0;
        for (Iterator it = customers.iterator(); it.hasNext();) {
          Customer c = (Customer)it.next();
          customerIds[ix++] = c.getId();
        }
      } else {
        customerIds = null;
      }
      

      //PASSWORD
      if (user.getPasswordHash() != null) {
        password = FAKE_PASSWORD;
        retypePassword = FAKE_PASSWORD;
      } else {
        password = null;
        retypePassword = null;
      }

      if (user.getLocale() == null) {
        user.setLocale(Locale.ENGLISH);
      }

      setLocaleStr(user.getLocale().toString());

    }
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    
    User user = (User)getPersistent();

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();
      
      if (password.length() < AppConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0)) {
        errors.add("password", new ActionMessage("userconfig.passwordTooShort"));
      }      
              
      if (User.findExcludeId(user.getUserName(), user.getId()) != null) {
        errors.add("userName", new ActionMessage("error.username.exists"));
      }

      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      LOG.error("validate", e);
    } finally {
      HibernateSession.closeSession();
    }

    if (errors.isEmpty()) {
      return null;
    } else {
      return errors;
    }
  }

}
