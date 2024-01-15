package ch.ess.cal.admin.web.category;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.RequestUtils;
import org.apache.taglibs.display.TableDecorator;

import ch.ess.cal.db.Category;

public class CategoryDecorator extends TableDecorator {

  public String getAction() throws MalformedURLException {
    Category obj = (Category)this.getObject();
    String name = obj.getName();
    Long id = obj.getCategoryId();

    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    String editURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/editCategory.do", null, params, null, true);

    params.remove("action");
    String deleteURL =
      RequestUtils.computeURL(getPageContext(), null, null, "/deleteCategory.do", null, params, null, true);

    return "<a class=\"tableAction\" href=\""
      + editURL
      + "\">Edit</a> | "
      + "<a onclick=\"return confirmRequest('"
      + name
      + "')\" class=\"tableAction\" href=\""
      + deleteURL
      + "\">Delete</a>";
  }
  
  public String getColour() {
    Category obj = (Category)this.getObject();
    return "<table border=\"0\" style=\"background: #"+obj.getColour()+";\"><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></table>";
  }
  
  
  public String getDefault() {
    Category obj = (Category)this.getObject();
    if (obj.isUnknown()) {
      HttpServletRequest request = (HttpServletRequest)getPageContext().getRequest();
      String url = request.getContextPath() + "/images/checked.gif";
      return "<img src=\""+url+"\" alt=\"\" width=\"13\" height=\"13\" border=\"0\">";
    } else {
      return "";      
    }
  }  

}
