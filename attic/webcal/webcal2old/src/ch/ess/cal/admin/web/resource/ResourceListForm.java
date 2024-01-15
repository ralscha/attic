package ch.ess.cal.admin.web.resource;

import java.util.*;

import org.apache.struts.util.*;

import net.sf.hibernate.*;
import ch.ess.cal.admin.web.*;
import ch.ess.cal.common.*;

public class ResourceListForm extends MapForm {

  private List resourceGroupOptions;
  
  public void init(Session sess, AdminUser adminUser, MessageResources messages) {
    resourceGroupOptions = new ResourceGroupOptions(sess, adminUser.getLocale(), messages).getLabelValue();
  }
  
  public List getResourceGroupOptions() {
    return resourceGroupOptions;
  }

}
