package ch.ess.timetracker.web.customer;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class CustomerDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Customer");
  }

  public String getDelete() throws MalformedURLException {        
    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {    
    return getDeleteString("Customer", "name");
    } else {
      return getEmptyString();
    }    
  }

}
