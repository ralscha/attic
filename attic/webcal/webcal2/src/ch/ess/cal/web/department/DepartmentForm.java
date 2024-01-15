package ch.ess.cal.web.department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.Department;
import ch.ess.cal.db.ResourceGroup;
import ch.ess.cal.db.User;
import ch.ess.cal.web.EmailUtil;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.NameEntry;
import ch.ess.common.web.PersistentForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 29.09.2003 
  * @struts.form name="departmentForm"
  */
public class DepartmentForm extends PersistentForm {

  private List emails;
  private String email;
  
  //Belongs users
  private List availableUsers;
  private List users;
  private String[] availableUsersId;
  private String[] usersId;

  //Access users
  private List availableAccessUsers;
  private List accessUsers;
  private String[] availableAccessUsersId;
  private String[] accessUsersId;
  
  //Resource Groups
  private List availableResGroups;
  private List resGroups;
  private String[] availableResGroupsId;
  private String[] resGroupsId;  

  private NameEntry[] entries;
  private String name;

  public NameEntry[] getEntries() {
    return entries;
  }

  public void setEntries(NameEntry[] entries) {
    this.entries = entries;
  }
  
  public void setEntry(int i, NameEntry entry) {
    entries[i] = entry;
  }

  public NameEntry getEntry(int i) {
    return this.entries[i];
  }


  public String getName() {
    return name;
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


  public List getAvailableUsers() {
    return availableUsers;
  }

  public String[] getAvailableUsersId() {
    return availableUsersId;
  }

  public List getUsers() {
    return users;
  }

  public String[] getUsersId() {
    return usersId;
  }

  public void setAvailableUsers(List list) {
    availableUsers = list;
  }

  public void setAvailableUsersId(String[] str) {
    availableUsersId = str;
  }

  public void setName(String string) {
    name = string;
  }

  public void setUsers(List list) {
    users = list;
  }

  public void setUsersId(String[] str) {
    usersId = str;
  }  
  

  public List getAccessUsers() {
    return accessUsers;
  }

  public String[] getAccessUsersId() {
    return accessUsersId;
  }

  public List getAvailableAccessUsers() {
    return availableAccessUsers;
  }

  public String[] getAvailableAccessUsersId() {
    return availableAccessUsersId;
  }

  public void setAccessUsers(List list) {
    accessUsers = list;
  }

  public void setAccessUsersId(String[] strings) {
    accessUsersId = strings;
  }

  public void setAvailableAccessUsers(List list) {
    availableAccessUsers = list;
  }

  public void setAvailableAccessUsersId(String[] strings) {
    availableAccessUsersId = strings;
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
    
    Map translations = new HashMap();

    
    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      Locale l = Util.getLocale(entry.getLocale());
      String na = entry.getName(); 
      
      translations.put(l, na);
        
    }
    Department department = (Department)getPersistent();

    department.setTranslations(translations);
    
    EmailUtil.addEmail(department, getEmails());
    
    //Belongs users
    Set belongUsers = new HashSet();
    department.getUsers().clear();
    for (Iterator it = users.iterator(); it.hasNext();) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();
      belongUsers.add(id);
      User user = User.load(new Long(id));
      department.getUsers().add(user);
       
    }

    //Access users
    department.getAccessUsers().clear();
    for (Iterator it = accessUsers.iterator(); it.hasNext();) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();
      if (!belongUsers.contains(id)) {
        User user = User.load(new Long(id));
        department.getAccessUsers().add(user);
      } 
    }

    
    //Resource Groups
    department.getResourceGroups().clear();
    for (Iterator it = resGroups.iterator(); it.hasNext();) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();      
      ResourceGroup rg = ResourceGroup.load(new Long(id));
      department.getResourceGroups().add(rg);
    }      
  }

  protected void toForm() throws HibernateException {
    
    Department department = (Department)getPersistent();
    Map translations = department.getTranslations();
    NameEntry[] entriesL = new NameEntry[translations.size()];
    
    Iterator it = translations.entrySet().iterator();
    int ix = 0;
    while (it.hasNext()) {
      Map.Entry en = (Map.Entry)it.next();
                  
      NameEntry entry = new NameEntry();
      String localeStr = ((Locale)en.getKey()).toString();
      entry.setLanguage(getMessages().getMessage(getLocale(), localeStr));
      entry.setLocale(localeStr);
      entry.setName((String)en.getValue());

      entriesL[ix] = entry;
      ix++;
    }
    
    name = (String)translations.get(getLocale());     
      
    setEntries(entriesL);
    
    emails = EmailUtil.getEmails(department);
    
    //users
    users = new ArrayList();
    availableUsers = new ArrayList();
    accessUsers = new ArrayList();
    availableAccessUsers = new ArrayList();
    
    Set depUsers = department.getUsers();
    Set depAccessUsers = department.getAccessUsers();
    Set selId = new HashSet();
    Set selAccessId = new HashSet();
    usersId = new String[0];
    availableUsersId = new String[0];
    accessUsersId = new String[0];
    availableAccessUsersId = new String[0];
        
    for (it = depUsers.iterator(); it.hasNext();) {
      User user = (User)it.next();
      selId.add(user.getId());
      users.add(new LabelValueBean(user.getFirstName() + " " + user.getName(), user.getId().toString()));      
    }

    for (it = depAccessUsers.iterator(); it.hasNext();) {
      User user = (User)it.next();
      selAccessId.add(user.getId());
      accessUsers.add(new LabelValueBean(user.getFirstName() + " " + user.getName(), user.getId().toString()));      
    }


    it = User.find();
    while (it.hasNext()) {
      User user = (User)it.next();
      if (!selId.contains(user.getId())) {
        availableUsers.add(new LabelValueBean(user.getFirstName() + " " + user.getName(), user.getId().toString()));
      }            
      if (!selAccessId.contains(user.getId())) {
        availableAccessUsers.add(new LabelValueBean(user.getFirstName() + " " + user.getName(), user.getId().toString()));
      }            

    }    
    
    //Resource Groups     
    resGroups = new ArrayList();
    availableResGroups = new ArrayList();
    resGroupsId = new String[0];
    availableResGroupsId = new String[0];
      
    selId = new HashSet();
             
    for (it = department.getResourceGroups().iterator(); it.hasNext();) {
      ResourceGroup rg = (ResourceGroup)it.next();
      selId.add(rg.getId());
      resGroups.add(new LabelValueBean((String)rg.getTranslations().get(getLocale()), rg.getId().toString()));      
    }

    it = ResourceGroup.find();
    while (it.hasNext()) {
      ResourceGroup rg = (ResourceGroup)it.next();
      if (!selId.contains(rg.getId())) {
        availableResGroups.add(new LabelValueBean((String)rg.getTranslations().get(getLocale()), rg.getId().toString()));
      }            
    }    
    
    Collections.sort(users);
    Collections.sort(availableUsers);
    Collections.sort(accessUsers);
    Collections.sort(availableAccessUsers);
    Collections.sort(resGroups);
    Collections.sort(availableResGroups);

    
    
  }
  
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    
    
    boolean ok = true;
    
    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      if (GenericValidator.isBlankOrNull(entry.getName())) {
        ok = false;
        break;
      }
    }

    if (emails.isEmpty() && (request.getParameter("change.addemail") == null)) {
      ok = false;
    }

    
    if (!ok) {
      errors.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_FILL_ALL);
    }


    if (errors.isEmpty()) {
      return null;
    } else {
      return errors;
    }
  }





}
