package ch.ess.cal.web.resourcegroup;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class ResourceGroupDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("ResourceGroup");
  }

  public String getDelete() throws MalformedURLException {        
    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {    
    return getDeleteString("ResourceGroup", "name");
    } else {
      return getEmptyString();
    }    
  }

}
