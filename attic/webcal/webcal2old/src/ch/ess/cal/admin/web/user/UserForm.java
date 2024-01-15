package ch.ess.cal.admin.web.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.Hibernate;
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

import ch.ess.cal.admin.web.AdminUser;
import ch.ess.cal.admin.web.DepartmentOptions;
import ch.ess.cal.admin.web.ResourceGroupOptions;
import ch.ess.cal.db.Department;
import ch.ess.cal.db.Email;
import ch.ess.cal.db.ResourceGroup;
import ch.ess.cal.db.User;
import ch.ess.cal.db.UserGroup;
import ch.ess.cal.resource.HibernateManager;
import ch.ess.cal.util.PasswordDigest;

public class UserForm extends ValidatorForm {

  private static final Log logger = LogFactory.getLog(UserForm.class);

  private static final String FAKE_PASSWORD = "hasPassword";

  private User user;
  private String[] emails;
  
  private long[] resourceGroupIds;
  private long[] departmentIds;
  private long[] accessDepartmentIds;
  
  private boolean admin;
  private String password;
  private String retypePassword;
  
  private List resourceGroupOptions;
  private List departmentOptions;
  
  
  public UserForm() {
    user = null;
    admin = false;
    emails = new String[3];
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
        
    if (user != null) {
      user.setUserName(null);
      
      password = null;
      retypePassword = null;
             
      resourceGroupIds = null;
      departmentIds = null;
      accessDepartmentIds = null;
      
      emails[0] = null;
      emails[1] = null;
      emails[2] = null; 
      
      admin = false;     
    }
  }

  public void setUserName(String userName) {
    if (!GenericValidator.isBlankOrNull(userName)) {
      user.setUserName(userName);
    } else {
      user.setUserName(null);
    }
  }

  public String getUserName() {
    return user.getUserName();
  }
  

  public void setName(String name) {
    if (!GenericValidator.isBlankOrNull(name)) {
      user.setName(name);
    } else {
      user.setName(null);
    }
  }

  public String getName() {
    return user.getName();
  }
  
  public void setFirstName(String firstName) {
    if (!GenericValidator.isBlankOrNull(firstName)) {
      user.setFirstName(firstName);
    } else {
      user.setFirstName(null);
    }
  }

  public String getFirstName() {
    return user.getFirstName();
  }  
 

  public void setEmail1(String email) {
    this.emails[0] = email;
  }
  
  public String getEmail1() {
    if (emails != null) {
      return emails[0];
    }
    return null;
  }
  
  public void setEmail2(String email) {
    this.emails[1] = email;
  }

  public String getEmail2() {
    if (emails != null) {
      return emails[1];
    }
    return null;
  }

  
  public void setEmail3(String email) {
    this.emails[2] = email;
  }

  public String getEmail3() {
    if (emails != null) {
      return emails[2];
    }
    return null;
  }

  

  public User getUser(Session sess) throws HibernateException {
    
    getFromForm(sess);
        
    return user;
  }

  public void setUser(User user, AdminUser adminUser) {
    setUser(user, null, adminUser, null);
  }

  public void setUser(User user, Session sess, AdminUser adminUser, MessageResources messages) {
    this.user = user;

    if (user != null) {
      setToForm(sess, adminUser, messages);
    }
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public List getResourceGroupOptions() {
    return resourceGroupOptions;
  }
  
  public List getDepartmentOptions() {
    return departmentOptions;
  }

  public long[] getResourceGroupIds() {
    return resourceGroupIds;
  }

  public void setResourceGroupIds(long[] ls) {
    resourceGroupIds = ls;
  }
  
  public long[] getAccessDepartmentIds() {
    return accessDepartmentIds;
  }

  public long[] getDepartmentIds() {
    return departmentIds;
  }

  public void setAccessDepartmentIds(long[] ls) {
    accessDepartmentIds = ls;
  }

  public void setDepartmentIds(long[] ls) {
    departmentIds = ls;
  }  
  
  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean b) {
    admin = b;
  }
  
  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

  public void setPassword(String string) {
    password = string;
  }

  public void setRetypePassword(String string) {
    retypePassword = string;
  }  
  
  private void setToForm(Session sess, AdminUser adminUser, MessageResources messages) {

    resourceGroupOptions = new ResourceGroupOptions(sess, adminUser.getLocale(), messages).getLabelValue();
    departmentOptions = new DepartmentOptions(sess, adminUser.getLocale(), messages).getLabelValue();

    //EMAIL
    for (int i = 0; i < 3; i++) {
      emails[i] = null;
    }
    
    List emailMap = user.getEmails();
    if (emailMap != null) {
      for (Iterator it = emailMap.iterator(); it.hasNext();) {
        Email email = (Email)it.next();
        emails[email.getSequence()] = email.getEmail();
      }
    }
    
    //RESOURCEGROUP
    Set resourceGroups = user.getResourceGroups();
    if ((resourceGroups != null) && (!resourceGroups.isEmpty())) {
      resourceGroupIds = new long[resourceGroups.size()];
      int ix = 0;
      for (Iterator it = resourceGroups.iterator(); it.hasNext();) {
        ResourceGroup rg = (ResourceGroup)it.next();
        resourceGroupIds[ix++] = rg.getResourceGroupId().longValue();
      }
    } else {
      resourceGroupIds = null;
    }
    
    //DEPARTMENT
    Set departments = user.getDepartments();
    if ((departments != null) && (!departments.isEmpty())) {
      departmentIds = new long[departments.size()];
      int ix = 0;
      for (Iterator it = departments.iterator(); it.hasNext();) {
        Department d = (Department)it.next();
        departmentIds[ix++] = d.getDepartmentId().longValue();
      }
    } else {
      departmentIds = null;
    }
    
    //ACCESSDEPARTMENT
    Set accessDepartments = user.getAccessDepartments();
    if ((accessDepartments != null) && (!accessDepartments.isEmpty())) {
      accessDepartmentIds = new long[accessDepartments.size()];
      int ix = 0;
      for (Iterator it = accessDepartments.iterator(); it.hasNext();) {
        Department d = (Department)it.next();
        accessDepartmentIds[ix++] = d.getDepartmentId().longValue();
      }
    } else {
      accessDepartmentIds = null;
    }    
    
    //USERGROUP
    if (user.getUserGroup() != null) {
      admin = true;
    } else {
      admin = false;
    }
    
    //PASSWORD
    if (user.getPasswordHash() != null) {
      password = retypePassword = FAKE_PASSWORD;      
    } else {
      password = retypePassword = null;
    }
  }



  private void getFromForm(Session sess) throws HibernateException {
    
    
    HibernateManager.deleteCollection(sess, user.getEmails());    
    List emailList = new ArrayList();
    
    //EMAIL    
    int ix = 0;    
    for (int i = 0; i < 3; i++) {
      if (!GenericValidator.isBlankOrNull(emails[i])) {
        Email newEmail = new Email();
        newEmail.setDefaultEmail(false);
        newEmail.setEmail(emails[i].trim());       
        newEmail.setUser(user);  
        newEmail.setSequence(ix);      
        emailList.add(newEmail);
        ix++;
      }
    }
    if (!emailList.isEmpty()) {
      user.setEmails(emailList);
    } else {
      user.setEmails(null);
    }
    
    //RESOURCEGROUP    
    Set resourceGroups = new HashSet();
    if (resourceGroupIds != null) {
      for (int i = 0; i < resourceGroupIds.length; i++) {
        if (resourceGroupIds[i] > 0) {
          ResourceGroup rg = (ResourceGroup)sess.load(ResourceGroup.class, new Long(resourceGroupIds[i]));
          resourceGroups.add(rg);
        } 
      }
    }
    
    if (!resourceGroups.isEmpty()) {
      user.setResourceGroups(resourceGroups);
    } else {
      user.setResourceGroups(null);
    }
    
    //DEPARTMENT    
    Set departments = new HashSet();
    if (departmentIds != null) {
      for (int i = 0; i < departmentIds.length; i++) {
        if (departmentIds[i] > 0) {
          Department d = (Department)sess.load(Department.class, new Long(departmentIds[i]));
          departments.add(d);
          
          for (int j = 0; j < accessDepartmentIds.length; j++) {
            if (departmentIds[i] == accessDepartmentIds[j]) {
              accessDepartmentIds[j] = -1;
              break;
            }
          }
        } 
      }
    }
    
    if (!departments.isEmpty()) {
      user.setDepartments(departments);
    } else {
      user.setDepartments(null);
    }    
    

    //ACCESSDEPARTMENT    
    Set accessDepartments = new HashSet();
    if (accessDepartmentIds != null) {
      for (int i = 0; i < accessDepartmentIds.length; i++) {
        if (accessDepartmentIds[i] > 0) {
          Department d = (Department)sess.load(Department.class, new Long(accessDepartmentIds[i]));
          accessDepartments.add(d);
        } 
      }
    }
    
    if (!departments.isEmpty()) {
      user.setAccessDepartments(accessDepartments);
    } else {
      user.setAccessDepartments(null);
    }       
    
    //ADMINISTRATOR
    if (admin) {
      List resultList = sess.find("from UserGroup as ug");
      user.setUserGroup((UserGroup)resultList.get(0));      
    } else {
      user.setUserGroup(null);
    }
    
    //PASSWORD
    password = password.trim();
    if (!password.equals(FAKE_PASSWORD)) {
      user.setPasswordHash(PasswordDigest.getDigestString(password));
    }
          
    user.setLocale(ch.ess.cal.Constants.LOCALE_EN);      
  }


  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    
    if (!GenericValidator.isBlankOrNull(getUserName()) && (user.getUserId() == null)) {
      Session sess = null;
      Transaction tx = null;
      try {
        sess = HibernateManager.open(request);
        tx = sess.beginTransaction(); 
        
        List resultList = sess.find("from User as u where u.userName = ?", getUserName(),
                          Hibernate.STRING);
        if (!resultList.isEmpty()) {        
          errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.username.exists"));
        }
        
        tx.commit();
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
