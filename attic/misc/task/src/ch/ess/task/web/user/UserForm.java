package ch.ess.task.web.user;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.util.Util;
import ch.ess.common.web.PersistentForm;
import ch.ess.task.db.Role;
import ch.ess.task.db.User;
import ch.ess.task.db.UserRole;
import ch.ess.task.resource.AppConfig;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:29 $
  *  
  * @struts.form name="userForm"
  */
public class UserForm extends PersistentForm {

  private static final Log LOG = LogFactory.getLog(UserForm.class);

  private static final String FAKE_PASSWORD = "hasPassword";

  private String password;
  private String retypePassword;
  private Long[] roleIds;
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

    if (!FAKE_PASSWORD.equals(getPassword())) {
      user.setPasswordHash(PasswordDigest.getDigestString(password));
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
