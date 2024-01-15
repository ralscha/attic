package ch.ess.task.web.status;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class StatusDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Status");
  }

  public String getDelete() throws MalformedURLException {

    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("Status", "name");
    } else {
      return getEmptyString();
    }    
    
  }

}
