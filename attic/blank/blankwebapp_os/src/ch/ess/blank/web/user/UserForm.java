package ch.ess.blank.web.user;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.blank.db.Role;
import ch.ess.blank.db.User;
import ch.ess.blank.db.UserRole;
import ch.ess.blank.resource.AppConfig;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.Util;
import ch.ess.common.web.PersistentForm;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.17 $ $Date: 2004/05/22 15:38:28 $
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

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    password = null;
    retypePassword = null;
    roleIds = null;
    localeStr = "";
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="login.userName"
   */
  public void setUserName(String userName) {
    ((User) getPersistent()).setUserName(getTrimmedString(userName));
  }

  public String getUserName() {
    return ((User) getPersistent()).getUserName();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.lastName"
   */
  public void setLastName(String name) {
    ((User) getPersistent()).setName(getTrimmedString(name));
  }

  public String getLastName() {
    return ((User) getPersistent()).getName();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.firstName"
   */
  public void setFirstName(String firstName) {
    ((User) getPersistent()).setFirstName(getTrimmedString(firstName));
  }

  public String getFirstName() {
    return ((User) getPersistent()).getFirstName();
  }

  /**   
   * @struts.validator type="required,email"
   * @struts.validator-arg position="0" key="user.eMail"
   */
  public void setEmail(String email) {
    ((User) getPersistent()).setEmail(getTrimmedString(email));
  }

  public String getEmail() {
    return ((User) getPersistent()).getEmail();
  }

  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

  /**   
   * @struts.validator type="required,identical"
   * @struts.validator-arg position="0" key="login.password"
   * @struts.validator-arg position="1" key=""user.retypePassword"
   * @struts.validator-var name="secondProperty" value="retypePassword"
   */
  public void setPassword(String string) {
    password = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.retypePassword"
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
   * @struts.validator-arg position="0" key="user.language"
   */
  public void setLocaleStr(String string) {
    localeStr = string;
  }

  protected void fromForm() throws HibernateException {

    User user = (User) getPersistent();
    user.getUserRoles().clear();

    if (roleIds != null) {
      Session sess = HibernateSession.currentSession();

      for (int i = 0; i < roleIds.length; i++) {
        if (roleIds[i].intValue() > 0) {
          Role r = (Role) sess.get(Role.class, roleIds[i]);
          if (r != null) {
            user.addRole(r);
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

    User user = (User) getPersistent();

    if (user != null) {
      //ROLES
      Set roles = user.getUserRoles();
      if (!roles.isEmpty()) {
        roleIds = new Long[roles.size()];

        int ix = 0;
        for (Iterator it = roles.iterator(); it.hasNext();) {
          UserRole userRole = (UserRole) it.next();
          Hibernate.initialize(userRole.getRole());
          roleIds[ix++] = userRole.getRole().getId();
        }
      } else {
        roleIds = null;
      }

      //PASSWORD
      if (user.getPasswordHash() != null) {
        password = FAKE_PASSWORD;
        retypePassword = FAKE_PASSWORD;
      } else {
        password = null;
        retypePassword = null;
      }

      if (user.getLocale() != null) {
        setLocaleStr(user.getLocale().toString());
      }

    }
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    User user = (User) getPersistent();

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
    }
    return errors;

  }

}