package ch.ess.addressbook.web.user;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import ch.ess.addressbook.db.Role;
import ch.ess.addressbook.db.User;
import ch.ess.addressbook.db.UserRole;
import ch.ess.addressbook.web.LocaleOptions;
import ch.ess.addressbook.web.RoleOptions;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.util.Util;
import ch.ess.common.web.BaseForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * @struts.form name="userForm"
  */
public class UserForm extends BaseForm {

  private static final Log LOG = LogFactory.getLog(UserForm.class);

  private static final String FAKE_PASSWORD = "hasPassword";

  private User user;

  private String password;
  private String retypePassword;
  private Long[] roleIds;
  private String locale;

  private List roleOptions;
  private List localeOptions;

  public UserForm() {
    user = null;
    reset(null, null);
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {

    if (user != null) {
      user.setUserName(null);
      user.setFirstName(null);
      user.setName(null);
      user.setEmail(null);
    }

    password = null;
    retypePassword = null;
    roleIds = null;
    locale = null;

  }

  public Long getId() {
    if (user != null) {
      return user.getId();
    }

    return null;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="UserName"
   */
  public void setUserName(String userName) {
    user.setUserName(getTrimmedString(userName));
  }

  public String getUserName() {
    return user.getUserName();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="LastName"
   */
  public void setName(String name) {
    user.setName(getTrimmedString(name));
  }

  public String getName() {
    return user.getName();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="FirstName"
   */
  public void setFirstName(String firstName) {
    user.setFirstName(getTrimmedString(firstName));
  }

  public String getFirstName() {
    return user.getFirstName();
  }

  /**   
   * @struts.validator type="required,email"
   * @struts.validator-args arg0resource="EMail"
   */
  public void setEmail(String email) {
    user.setEmail(getTrimmedString(email));
  }

  public String getEmail() {
    return user.getEmail();
  }

  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

  public List getRoleOptions() {
    return roleOptions;
  }

  public List getLocaleOptions() {
    return localeOptions;
  }

  /**   
   * @struts.validator type="required,identical"
   * @struts.validator-args arg0resource="Password" arg1resource="RetypePassword"
   * @struts.validator-var name="secondProperty" value="retypePassword"
   */
  public void setPassword(String string) {
    password = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="RetypePassword"
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

  public String getLocale() {
    return locale;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="Language"
   */
  public void setLocale(String string) {
    locale = string;
  }

  public User getUser() throws HibernateException {
    fromForm();
    return user;

  }

  public void setUser(User user, Locale loc, MessageResources messages) throws HibernateException {
    this.user = user;

    toForm(loc, messages);

  }

  private void fromForm() throws HibernateException {
    
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

    user.setLocale(Util.getLocale(getLocale()));

  }

  private void toForm(Locale loc, MessageResources messages) throws HibernateException {
    if (user != null) {
      //ROLES
      Set roles = user.getUserRoles();
      if (!roles.isEmpty()) {
        roleIds = new Long[roles.size()];

        int ix = 0;
        for (Iterator it = roles.iterator(); it.hasNext();) {
          UserRole role = (UserRole)it.next();
          roleIds[ix++] = role.getRole().getId();
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

      setLocale(user.getLocale().toString());

      roleOptions = new RoleOptions(loc, messages).getLabelValue();
      localeOptions = new LocaleOptions(loc, messages).getLabelValue();
    }
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    if (!GenericValidator.isBlankOrNull(getUserName()) && (user.getId() == null)) {
      Transaction tx = null;
      try {
        tx = HibernateSession.currentSession().beginTransaction();
        
        if (User.find(getUserName()) != null) {
          errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.username.exists"));
        }

        tx.commit();
      } catch (HibernateException e) {
        HibernateSession.rollback(tx);
        LOG.error("validate", e);
      } finally {
        HibernateSession.closeSession();
      }
    }

    if (errors.isEmpty()) {
      return null;
    } else {
      return errors;
    }
  }

}
