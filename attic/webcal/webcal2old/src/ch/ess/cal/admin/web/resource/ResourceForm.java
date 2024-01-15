package ch.ess.cal.admin.web.resource;


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

public class ResourceForm extends ValidatorForm {

  private Resource resource;
  private long resourceGroupId;
  
  private List resourceGroupOptions;
  
  public ResourceForm() {
    resource = null;        
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    
    resourceGroupId = -1;
    
    if (resource != null) {
      resource.setName(null);
      resource.setDescription(null);      
    }
  }

  public void setName(String name) {
    if (!GenericValidator.isBlankOrNull(name)) {
      resource.setName(name);
    } else {
      resource.setName(null);
    }
  }

  public String getName() {
    return resource.getName();
  }

  public void setDescription(String description) {
    if (!GenericValidator.isBlankOrNull(description)) {
      resource.setDescription(description);
    } else {
      resource.setDescription(null);
    }
  }

  public String getDescription() {
    return resource.getDescription();
  }


  public Resource getResource(Session sess) throws HibernateException {    
    getFromForm(sess);        
    return resource;
  }

  public void setResource(Resource resource, AdminUser adminUser) {
    setResource(resource, null, adminUser, null);
  }
  
  public void setResource(Resource resource, Session sess, AdminUser adminUser, MessageResources messages) {
    this.resource = resource;
    
    if (resource != null) {
      setToForm(sess, adminUser, messages);
    }
  }

  public long getResourceGroupId() {
    return resourceGroupId;
  }

  public void setResourceGroupId(long l) {
    resourceGroupId = l;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  
  private void setToForm(Session sess, AdminUser adminUser, MessageResources messages) {
    if (resource.getResourceGroup() != null) {
      resourceGroupId = resource.getResourceGroup().getResourceGroupId().longValue();
    }
    
    resourceGroupOptions = new ResourceGroupOptions(sess, adminUser.getLocale(), messages).getLabelValue();
  }

  private void getFromForm(Session sess) throws HibernateException {  
    
    ResourceGroup rg = (ResourceGroup)sess.load(ResourceGroup.class, new Long(resourceGroupId));
    if (rg != null) {
      resource.setResourceGroup(rg);
    }
  }
  
  public List getResourceGroupOptions() {
    return resourceGroupOptions;
  }

}
