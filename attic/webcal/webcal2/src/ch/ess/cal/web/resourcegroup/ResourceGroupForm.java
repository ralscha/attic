package ch.ess.cal.web.resourcegroup;

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
import ch.ess.cal.db.Resource;
import ch.ess.cal.db.ResourceGroup;
import ch.ess.cal.db.User;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.NameEntry;
import ch.ess.common.web.PersistentForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 30.09.2003 
  * @struts.form name="resourceGroupForm"
  */
public class ResourceGroupForm extends PersistentForm {

  private NameEntry[] entries;
  private String name;

  private List availableResources;
  private List resources;
  private String[] availableResourcesId;
  private String[] resourcesId;

  //Users
  private List availableUsers;
  private List users;
  private String[] availableUsersId;
  private String[] usersId;  
  
  //Departments
  private List availableDepartments;
  private List departments;
  private String[] availableDepartmentsId;
  private String[] departmentsId;  

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

  public List getAvailableResources() {
    return availableResources;
  }

  public String[] getAvailableResourcesId() {
    return availableResourcesId;
  }

  public List getResources() {
    return resources;
  }

  public String[] getResourcesId() {
    return resourcesId;
  }

  public void setAvailableResources(List list) {
    availableResources = list;
  }

  public void setAvailableResourcesId(String[] strings) {
    availableResourcesId = strings;
  }

  public void setResources(List list) {
    resources = list;
  }

  public void setResourcesId(String[] strings) {
    resourcesId = strings;
  }

  public List getAvailableDepartments() {
    return availableDepartments;
  }

  public String[] getAvailableDepartmentsId() {
    return availableDepartmentsId;
  }

  public List getAvailableUsers() {
    return availableUsers;
  }

  public String[] getAvailableUsersId() {
    return availableUsersId;
  }

  public List getDepartments() {
    return departments;
  }

  public String[] getDepartmentsId() {
    return departmentsId;
  }

  public List getUsers() {
    return users;
  }

  public String[] getUsersId() {
    return usersId;
  }

  public void setAvailableDepartments(List list) {
    availableDepartments = list;
  }

  public void setAvailableDepartmentsId(String[] strings) {
    availableDepartmentsId = strings;
  }

  public void setAvailableUsers(List list) {
    availableUsers = list;
  }

  public void setAvailableUsersId(String[] strings) {
    availableUsersId = strings;
  }

  public void setDepartments(List list) {
    departments = list;
  }

  public void setDepartmentsId(String[] strings) {
    departmentsId = strings;
  }

  public void setUsers(List list) {
    users = list;
  }

  public void setUsersId(String[] strings) {
    usersId = strings;
  }

  protected void fromForm() throws HibernateException {

    Map translations = new HashMap();

    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      Locale l = Util.getLocale(entry.getLocale());
      String na = entry.getName();

      translations.put(l, na);

    }
    ResourceGroup resourceGroup = (ResourceGroup)getPersistent();

    resourceGroup.setTranslations(translations);
                
    for (Iterator it = resources.iterator(); it.hasNext();) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();      
      Resource res = Resource.load(new Long(id));
      res.setResourceGroup(resourceGroup);            
    }    


    //Departments
    resourceGroup.getDepartments().clear();
    for (Iterator it = departments.iterator(); it.hasNext();) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();
      Department dep = Department.load(new Long(id));
      resourceGroup.getDepartments().add(dep);       
    }
    
    //Users
    resourceGroup.getUsers().clear();
    for (Iterator it = users.iterator(); it.hasNext();) {
      LabelValueBean lb = (LabelValueBean)it.next();
      String id = lb.getValue();
      User user = User.load(new Long(id));
      resourceGroup.getUsers().add(user);       
    }    

  }

  protected void toForm() throws HibernateException {

    ResourceGroup resourceGroup = (ResourceGroup)getPersistent();
    Map translations = resourceGroup.getTranslations();
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

    setEntries(entriesL);

    name = (String)resourceGroup.getTranslations().get(getLocale());

    
    resources = new ArrayList();
    availableResources = new ArrayList();
    resourcesId = new String[0];
    availableResourcesId = new String[0];
    
    Set selId = new HashSet();
        
    for (it = resourceGroup.getResources().iterator(); it.hasNext();) {
      Resource res = (Resource)it.next();
      selId.add(res.getId());
      resources.add(new LabelValueBean((String)res.getTranslations().get(getLocale()), res.getId().toString()));      
    }

    it = Resource.find();
    while (it.hasNext()) {
      Resource res = (Resource)it.next();
      if (!selId.contains(res.getId())) {
        availableResources.add(new LabelValueBean((String)res.getTranslations().get(getLocale()), res.getId().toString()));
      }            
    }    
    

    //Users
    users = new ArrayList();
    availableUsers = new ArrayList();
    usersId = new String[0];
    availableUsersId = new String[0];
    
    selId = new HashSet();
        
    for (it = resourceGroup.getUsers().iterator(); it.hasNext();) {
      User user = (User)it.next();
      selId.add(user.getId());
      users.add(new LabelValueBean(user.getFirstName() + " " + user.getName(), user.getId().toString()));      
    }

    it = User.find();
    while (it.hasNext()) {
      User user = (User)it.next();
      if (!selId.contains(user.getId())) {
        availableUsers.add(new LabelValueBean(user.getFirstName() + " " + user.getName(), user.getId().toString()));
      }                       
    }
    
    //Departments
    departments = new ArrayList();
    availableDepartments = new ArrayList();
    departmentsId = new String[0];
    availableDepartmentsId = new String[0];
    
    selId = new HashSet();
        
    for (it = resourceGroup.getDepartments().iterator(); it.hasNext();) {
      Department dep = (Department)it.next();
      selId.add(dep.getId());
      departments.add(new LabelValueBean((String)dep.getTranslations().get(getLocale()), dep.getId().toString()));      
    }


    it = Department.find();
    while (it.hasNext()) {
      Department dep = (Department)it.next();
      if (!selId.contains(dep.getId())) {
        availableDepartments.add(new LabelValueBean((String)dep.getTranslations().get(getLocale()), dep.getId().toString()));
      }            
    }    
    

    Collections.sort(departments);
    Collections.sort(availableDepartments);    
    Collections.sort(users);
    Collections.sort(availableUsers);    
    Collections.sort(resources);
    Collections.sort(availableResources);

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
