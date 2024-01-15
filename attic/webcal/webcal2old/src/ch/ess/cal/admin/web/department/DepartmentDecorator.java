package ch.ess.cal.admin.web.department;

import java.net.*;
import java.util.*;

import org.apache.struts.util.*;
import org.apache.taglibs.display.*;

import ch.ess.cal.db.*;

public class DepartmentDecorator extends TableDecorator {

  public String getAction() throws MalformedURLException {
    Department obj = (Department)this.getObject();
    String name = obj.getName();
    Long id = obj.getDepartmentId();

    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    String editURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/editDepartment.do", null, params, null, true);

    params.remove("action");
    String deleteURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/deleteDepartment.do", null, params, null, true);

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
