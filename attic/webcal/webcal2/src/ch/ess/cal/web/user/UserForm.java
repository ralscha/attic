package ch.ess.cal.web.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.Department;
import ch.ess.cal.db.ResourceGroup;
import ch.ess.cal.db.Role;
import ch.ess.cal.db.User;
import ch.ess.cal.db.UserRole;
import ch.ess.cal.resource.AppConfig;
import ch.ess.cal.web.EmailUtil;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.Util;
import ch.ess.common.web.PersistentForm;

/** 
 * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $
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
  private List emails;
  private String email;

  //Belongs departments
  private List availableDepartments;
  private List departments;
  private String[] availableDepartmentsId;
  private String[] departmentsId;

  //Access departments
  private List availableAccessDepartments;
  private List accessDepartments;
  private String[] availableAccessDepartmentsId;
  private String[] accessDepartmentsId;

  //Resource Groups
  private List availableResGroups;
  private List resGroups;
  private String[] availableResGroupsId;
  private String[] resGroupsId;

  /**   
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="login.userName"
   */
  public void setUserName(String userName) {
    ((User)getPersistent()).setUserName(getTrimmedString(userName));
  }

  public String getUserName() {
    return ((User)getPersistent()).getUserName();
  }

  public void setShortName(String shortName) {
    ((User)getPersistent()).setShortName(getTrimmedString(shortName));
  }

  public String getShortName() {
    return ((User)getPersistent()).getShortName();
  }

  /**   
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="user.lastName"
   */
  public void setName(String name) {
    ((User)getPersistent()).setName(getTrimmedString(name));
  }

  public String getName() {
    return ((User)getPersistent()).getName();
  }

  /**   
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="user.firstName"
   */
  public void setFirstName(String firstName) {
    ((User)getPersistent()).setFirstName(getTrimmedString(firstName));
  }

  public String getFirstName() {
    return ((User)getPersistent()).getFirstName();
  }

  /**   
   * @struts.validator page="0" type="email"
   * @struts.validator-args arg0resource="user.eMail"
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }


  /**   
   * @struts.validator page="0" type="required,identical"
   * @struts.validator-args arg0resource="login.password" arg1resource="user.retypePassword"
   * @struts.validator-var name="secondProperty" value="retypePassword"
   */
  public void setPassword(String string) {
    password = string;
  }

  /**   
   * @struts.validator page="0" type="required"
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
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="user.language"
   */
  public void setLocaleStr(String string) {
    localeStr = string;
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

  public List getAccessDepartments() {
    return accessDepartments;
  }

  public String[] getAccessDepartmentsId() {
    return accessDepartmentsId;
  }

  public List getAvailableAccessDepartments() {
    return availableAccessDepartments;
  }

  public String[] getAvailableAccessDepartmentsId() {
    return availableAccessDepartmentsId;
  }

  public List getAvailableDepartments() {
    return availableDepartments;
  }

  public String[] getAvailableDepartmentsId() {
    return availableDepartmentsId;
  }

  public List getDepartments() {
    return departments;
  }

  public String[] getDepartmentsId() {
    return departmentsId;
  }

  public void setAccessDepartments(List list) {
    accessDepartments = list;
  }

  public void setAccessDepartmentsId(String[] strings) {
    accessDepartmentsId = strings;
  }

  public void setAvailableAccessDepartments(List list) {
    availableAccessDepartments = list;
  }

  public void setAvailableAccessDepartmentsId(String[] strings) {
    availableAccessDepartmentsId = strings;
  }

  public void setAvailableDepartments(List list) {
    availableDepartments = list;
  }

  public void setAvailableDepartmentsId(String[] strings) {
    availableDepartmentsId = strings;
  }

  public void setDepartments(List list) {
    departments = list;
  }

  public void setDepartmentsId(String[] strings) {
    departmentsId = strings;
  }

  public List getAvailableResGroups() {
    return availableResGroups;
  }

  public String[] getAvailableResGroupsId() {
    return availableResGroupsId;
  }

  public List getResGroups() {
    return resGroups;
  }

  public String[] getResGroupsId() {
    return resGroupsId;
  }

  public void setAvailableResGroups(List list) {
    availableResGroups = list;
  }

  public void setAvailableResGroupsId(String[] strings) {
    availableResGroupsId = strings;
  }

  public void setResGroups(List list) {
    resGroups = list;
  }

  public void setResGroupsId(String[] strings) {
    resGroupsId = strings;
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
      user.setPasswordHash(DigestUtils.shaHex(password));
    }

    user.setLocale(Util.getLocale(getLocaleStr()));

    EmailUtil.addEmail(user, getEmails());

    //Belongs departments
    Set belongDepartments = new HashSet();
    user.getDepartments().clear();
    for (Iterator it = departments.iterator(); it.hasNext(); ) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();
      belongDepartments.add(id);
      Department dep = Department.load(new Long(id));
      user.getDepartments().add(dep);

    }

    //Access departments
    user.getAccessDepartments().clear();
    for (Iterator it = accessDepartments.iterator(); it.hasNext(); ) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();
      if (!belongDepartments.contains(id)) {
        Department dep = Department.load(new Long(id));
        user.getAccessDepartments().add(dep);
      }
    }

    //Resource Groups
    user.getResourceGroups().clear();
    for (Iterator it = resGroups.iterator(); it.hasNext(); ) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();
      ResourceGroup rg = ResourceGroup.load(new Long(id));
      user.getResourceGroups().add(rg);
    }

  }

  protected void toForm() throws HibernateException {

    User user = (User)getPersistent();

    if (user != null) {
      //ROLES
      Set roles = user.getUserRoles();
      if (!roles.isEmpty()) {
        roleIds = new Long[roles.size()];

        int ix = 0;
        for (Iterator it = roles.iterator(); it.hasNext(); ) {
          UserRole userRole = (UserRole)it.next();
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

      if (user.getLocale() == null) {
        user.setLocale(Locale.ENGLISH);
      }

      setLocaleStr(user.getLocale().toString());

      emails = EmailUtil.getEmails(user);

      //departments
      departments = new ArrayList();
      availableDepartments = new ArrayList();
      accessDepartments = new ArrayList();
      availableAccessDepartments = new ArrayList();

      Set userDepartments = user.getDepartments();
      Set userAccessDepartments = user.getAccessDepartments();
      Set selId = new HashSet();
      Set selAccessId = new HashSet();
      departmentsId = new String[0];
      availableDepartmentsId = new String[0];
      accessDepartmentsId = new String[0];
      availableAccessDepartmentsId = new String[0];

      for (Iterator it = userDepartments.iterator(); it.hasNext(); ) {
        Department dep = (Department)it.next();
        selId.add(dep.getId());
        departments.add(new LabelValueBean((String)dep.getTranslations().get(getLocale()), dep.getId().toString()));
      }

      for (Iterator it = userAccessDepartments.iterator(); it.hasNext(); ) {
        Department dep = (Department)it.next();
        selAccessId.add(dep.getId());
        accessDepartments.add(
            new LabelValueBean((String)dep.getTranslations().get(getLocale()), dep.getId().toString()));
      }

      Iterator it = Department.find();
      while (it.hasNext()) {
        Department dep = (Department)it.next();
        if (!selId.contains(dep.getId())) {
          availableDepartments.add(new LabelValueBean((String)dep.getTranslations().get(getLocale()), dep.getId()
              .toString()));
        }
        if (!selAccessId.contains(dep.getId())) {
          availableAccessDepartments.add(new LabelValueBean((String)dep.getTranslations().get(getLocale()), dep.getId()
              .toString()));
        }

      }

      //Resource Groups     
      resGroups = new ArrayList();
      availableResGroups = new ArrayList();
      resGroupsId = new String[0];
      availableResGroupsId = new String[0];

      selId = new HashSet();

      for (it = user.getResourceGroups().iterator(); it.hasNext(); ) {
        ResourceGroup rg = (ResourceGroup)it.next();
        selId.add(rg.getId());
        resGroups.add(new LabelValueBean((String)rg.getTranslations().get(getLocale()), rg.getId().toString()));
      }

      it = ResourceGroup.find();
      while (it.hasNext()) {
        ResourceGroup rg = (ResourceGroup)it.next();
        if (!selId.contains(rg.getId())) {
          availableResGroups.add(new LabelValueBean((String)rg.getTranslations().get(getLocale()), rg.getId()
              .toString()));
        }
      }

      Collections.sort(departments);
      Collections.sort(availableDepartments);
      Collections.sort(accessDepartments);
      Collections.sort(availableAccessDepartments);
      Collections.sort(resGroups);
      Collections.sort(availableResGroups);

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

      if (emails.isEmpty() && (request.getParameter("change.addemail") == null)) {
        errors.add("emails", Constants.ACTION_MESSAGE_FILL_ALL);
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