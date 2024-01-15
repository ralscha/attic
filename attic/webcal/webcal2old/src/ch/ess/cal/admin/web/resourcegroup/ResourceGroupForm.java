package ch.ess.cal.admin.web.resourcegroup;


import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.commons.lang.builder.*;
import org.apache.commons.validator.*;
import org.apache.struts.action.*;
import org.apache.struts.validator.*;

import ch.ess.cal.db.*;

public class ResourceGroupForm extends ValidatorForm {

  private ResourceGroup resourceGroup;
 
  public ResourceGroupForm() {
    resourceGroup = null;    
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    if (resourceGroup != null) {
      resourceGroup.setName(null);
      resourceGroup.setDescription(null);
    }
  }

  public void setName(String name) {
    if (!GenericValidator.isBlankOrNull(name)) {
      resourceGroup.setName(name);
    } else {
      resourceGroup.setName(null);
    }
  }

  public String getName() {
    return resourceGroup.getName();
  }

  public void setDescription(String description) {
    if (!GenericValidator.isBlankOrNull(description)) {
      resourceGroup.setDescription(description);
    } else {
      resourceGroup.setDescription(null);
    }
  }

  public String getDescription() {
    return resourceGroup.getDescription();
  }




  public ResourceGroup getResourceGroup(Session sess) throws HibernateException {    
    return resourceGroup;
  }

  public void setResourceGroup(ResourceGroup resourceGroup) {
    this.resourceGroup = resourceGroup;  
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }


  
}
