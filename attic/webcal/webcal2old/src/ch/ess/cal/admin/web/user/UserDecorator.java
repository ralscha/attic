package ch.ess.cal.admin.web.user;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.util.RequestUtils;
import org.apache.taglibs.display.TableDecorator;


public class UserDecorator extends TableDecorator {

  public String getAction() throws MalformedURLException {

    DynaBean obj = (DynaBean)this.getObject();
    String name = (String)obj.get("userName");
    Long id = (Long)obj.get("id");

    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    String editURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/editUser.do", null, params, null, true);

    params.remove("action");
    String deleteURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/deleteUser.do", null, params, null, true);

    return "<a class=\"tableAction\" href=\""
      + editURL
      + "\">Edit</a> | "
      + "<a onclick=\"return confirmRequest('"
      + name
      + "')\" class=\"tableAction\" href=\""
      + deleteURL
      + "\">Delete</a>";
  }
  
  public String getAdmin() {
    DynaBean obj = (DynaBean)this.getObject();
    String admin = (String)obj.get("admin");
    if (admin.equals("Y")) {
      
      // normalize relative URLs against a context root
      HttpServletRequest request = (HttpServletRequest)getPageContext().getRequest();
      String url = request.getContextPath() + "/images/checked.gif";
      return "<img src=\""+url+"\" alt=\"\" width=\"13\" height=\"13\" border=\"0\">";
    } else {
      return "";
    }   
  }

}
