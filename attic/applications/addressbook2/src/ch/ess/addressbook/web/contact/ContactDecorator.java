package ch.ess.addressbook.web.contact;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.taglib.TagUtils;

import ch.ess.common.web.ListDecorator;

public class ContactDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Contact");
  }

  public String getDelete() throws MalformedURLException {
    return getDeleteString("Contact", "lastName");
  }

  public String getInfo() throws MalformedURLException {
    DynaBean obj = (DynaBean)this.getCurrentRowObject();
      
    String info = (String)obj.get("info");
    
    String infoImageURL =
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/info.gif", null, null, null, true);

    String s = "<div onmouseover=\"return overlib('"+info+"', FGCOLOR, '#EEEEEE', WIDTH, 110);\" onmouseout=\"return nd();\">";
    s += "<img src=\"" + infoImageURL + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\" />";
    s += "</div>";
    
    return s;
  }
  
  public String getExport() throws MalformedURLException {
    DynaBean obj = (DynaBean)this.getCurrentRowObject();
    Long id = (Long)obj.get("id");

    Map params = new HashMap();
    params.put("id", id);
    String editURL =
      TagUtils.getInstance().computeURL(
        getPageContext(),
        null,
        null,
        "/exportVcard.vcf",
        null,
        params,
        null,
        true);

    String editImageURL =
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/export.gif", null, null, null, true);

    return "<a onclick=\"bClick=true\" href=\""
      + editURL
      + "\"><img src=\""
      + editImageURL
      + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\" /></a>";
  }  
  

}
