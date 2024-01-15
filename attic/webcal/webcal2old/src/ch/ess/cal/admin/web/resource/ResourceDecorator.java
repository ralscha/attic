package ch.ess.cal.admin.web.resource;

import java.net.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import org.apache.taglibs.display.*;


public class ResourceDecorator extends TableDecorator {

  public String getAction() throws MalformedURLException {
    DynaBean obj = (DynaBean)this.getObject();
    String name = (String)obj.get("name");
    Long id = (Long)obj.get("id");

    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    String editURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/editResource.do", null, params, null, true);

    params.remove("action");
    String deleteURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/deleteResource.do", null, params, null, true);

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
