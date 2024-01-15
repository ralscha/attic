package ch.ess.cal.web.holiday;


import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class HolidayDecorator extends ListDecorator {

  
  public String getCheck() {

    Long id = getLongProperty("id");    
    Boolean active = getBooleanProperty("active");
             
    String result = "<input onclick=\"bClick=true\" type=\"checkbox\" name=\"check\" value=\""+id+"\"";
    
    if (active.booleanValue()) {
      result += " checked";
    }
    result += ">";
    
    if (active.booleanValue()) {
      result += "<input type=\"hidden\" name=\"activeId\" value=\""+id+"\">";
    } else {
      result += "<input type=\"hidden\" name=\"notactiveId\" value=\""+id+"\">";
    }
    
    return result;         
  }


  public String getEdit() throws MalformedURLException {
    return getEditString("Holiday");
  }

  public String getDelete() throws MalformedURLException {

    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {
      return getDeleteString("Holiday", "name");
    } else {
      return getEmptyString();
    }
  }

}
