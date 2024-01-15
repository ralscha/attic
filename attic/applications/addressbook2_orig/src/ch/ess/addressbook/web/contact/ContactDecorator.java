package ch.ess.addressbook.web.contact;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.taglib.TagUtils;
import org.displaytag.decorator.TableDecorator;


public class ContactDecorator extends TableDecorator {

  public String getEdit() throws MalformedURLException {
    
    DynaBean obj = (DynaBean)this.getCurrentRowObject();    
    Long id = (Long)obj.get("id");


    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    String editURL =
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/editContact.do", null, params, null, true);

    String editImageURL = 
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/edit.gif", null, null, null, true);
                
    return "<a onclick=\"bClick=true\" href=\"" + editURL + "\"><img src=\""+editImageURL+"\" alt=\"\" border=\"0\"></a>";
  }
  

  public String getDelete() throws MalformedURLException {
    
    DynaBean obj = (DynaBean)this.getCurrentRowObject();    
    Long id = (Long)obj.get("id");
    String name = (String)obj.get("lastName");

    Map params = new HashMap();
    params.put("id", id);
    String editURL =
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/deleteContact.do", null, params, null, true);

    String deleteImageURL = 
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/del.gif", null, null, null, true);
                
    return "<a onclick=\"bClick=true; return confirmRequest('"+name+"')\" href=\"" + editURL + "\"><img src=\""+deleteImageURL+"\" alt=\"\" border=\"0\"></a>";
  }
  
}


