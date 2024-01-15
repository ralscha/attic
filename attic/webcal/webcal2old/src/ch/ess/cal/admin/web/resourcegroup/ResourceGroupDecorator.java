package ch.ess.cal.admin.web.resourcegroup;

import java.net.*;
import java.util.*;

import org.apache.struts.util.*;
import org.apache.taglibs.display.*;

import ch.ess.cal.db.*;

public class ResourceGroupDecorator extends TableDecorator {

  public String getAction() throws MalformedURLException {
    ResourceGroup obj = (ResourceGroup)this.getObject();
    String name = obj.getName();
    Long id = obj.getResourceGroupId();

    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    String editURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/editResourceGroup.do", null, params, null, true);

    params.remove("action");
    String deleteURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/deleteResourceGroup.do", null, params, null, true);

    return "<a class=\"tableAction\" href=\""
      + editURL
      + "\">Edit</a> | "
      + "<a onclick=\"return confirmRequest('"
      + name
      + "')\" class=\"tableAction\" href=\""
      + deleteURL
      + "\">Delete</a>";
  }

}
