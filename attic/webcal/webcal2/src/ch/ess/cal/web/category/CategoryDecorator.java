package ch.ess.cal.web.category;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.taglib.TagUtils;

import ch.ess.common.web.ListDecorator;

public class CategoryDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Category");
  }

  public String getDelete() throws MalformedURLException {

    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("Category", "name");
    } else {
      return getEmptyString();
    }
  }

  public String getColour() throws MalformedURLException {
    String colour = getStringProperty("colour");

    Map params = new HashMap();
    params.put("w", "30");
    params.put("h", "14");
    params.put("c", colour);
    
    
    String picURL =
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/calPic.donogz", null, params, null, true);

    return "<img src=\""+picURL+"\" alt=\"\" width=\"30\" height=\"14\" border=\"0\">";
    
  }

  public String getBwColour() throws MalformedURLException {
    String colour = getStringProperty("bwColour");

    Map params = new HashMap();
    params.put("w", "30");
    params.put("h", "14");
    params.put("c", colour);
    
    
    String picURL =
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/calPic.donogz", null, params, null, true);

    return "<img src=\""+picURL+"\" alt=\"\" width=\"30\" height=\"14\" border=\"0\">";

  }


  public String getDefault() throws MalformedURLException {

    Boolean unknown = getBooleanProperty("unknown");
    
    if (unknown.booleanValue()) {

      String checkedImageURL =
        TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/checked.gif", null, null, null, true);

      return "<img src=\"" + checkedImageURL + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\">";
    } else {
      return getEmptyString();
    }
  }


}
