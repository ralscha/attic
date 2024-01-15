package ch.ess.cal.admin.web.department;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.commons.lang.builder.*;
import org.apache.commons.validator.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.apache.struts.validator.*;

import ch.ess.cal.admin.web.*;
import ch.ess.cal.db.*;
import ch.ess.cal.resource.*;

public class DepartmentForm extends ValidatorForm {

  private Department department;
  private String[] emails;
  private long[] resourceGroupIds;
  
  private List resourceGroupOptions;
  
  
  public DepartmentForm() {
    department = null;
    emails = new String[3];
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
        
    if (department != null) {
      department.setName(null);
      department.setDescription(null);
            
      resourceGroupIds = null;
      emails[0] = null;
      emails[1] = null;
      emails[2] = null;      
    }
  }

  public void setName(String name) {
    if (!GenericValidator.isBlankOrNull(name)) {
      department.setName(name);
    } else {
      department.setName(null);
    }
  }

  public String getName() {
    return department.getName();
  }

  public void setDescription(String description) {
    if (!GenericValidator.isBlankOrNull(description)) {
      department.setDescription(description);
    } else {
      department.setDescription(null);
    }
  }

  public String getDescription() {
    return department.getDescription();
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

  

  public Department getDepartment(Session sess) throws HibernateException {
    
    getFromForm(sess);
        
    return department;
  }

  public void setDepartment(Department department, AdminUser adminUser) {
    setDepartment(department, null, adminUser, null);
  }

  public void setDepartment(Department department, Session sess, AdminUser adminUser, MessageResources messages) {
    this.department = department;

    if (department != null) {
      setToForm(sess, adminUser, messages);
    }
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public List getResourceGroupOptions() {
    return resourceGroupOptions;
  }

  public long[] getResourceGroupIds() {
    return resourceGroupIds;
  }

  public void setResourceGroupIds(long[] ls) {
    resourceGroupIds = ls;
  }
  
  private void setToForm(Session sess, AdminUser adminUser, MessageResources messages) {

    resourceGroupOptions = new ResourceGroupOptions(sess, adminUser.getLocale(), messages).getLabelValue();

    for (int i = 0; i < 3; i++) {
      emails[i] = null;
    }
    
    List emailMap = department.getEmails();
    if (emailMap != null) {
      for (Iterator it = emailMap.iterator(); it.hasNext();) {
        Email email = (Email)it.next();
        emails[email.getSequence()] = email.getEmail();
      }
    }
    
    
    Set resourceGroups = department.getResourceGroups();
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
  
  }

  private void getFromForm(Session sess) throws HibernateException {
    
    
    HibernateManager.deleteCollection(sess, department.getEmails());    
    List emailList = new ArrayList();
        
    int ix = 0;    
    for (int i = 0; i < 3; i++) {
      if (!GenericValidator.isBlankOrNull(emails[i])) {
        Email newEmail = new Email();
        newEmail.setDefaultEmail(false);
        newEmail.setEmail(emails[i].trim());       
        newEmail.setDepartment(department);  
        newEmail.setSequence(ix);      
        emailList.add(newEmail);
        ix++;
      }
    }
    if (!emailList.isEmpty()) {
      department.setEmails(emailList);
    } else {
      department.setEmails(null);
    }
    
        
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
      department.setResourceGroups(resourceGroups);
    } else {
      department.setResourceGroups(null);
    }
    
          
  }


  


}
