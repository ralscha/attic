package ch.ess.task.web.priority;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class PriorityDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Priority");
  }

  public String getDelete() throws MalformedURLException {
            
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("Priority", "name");
    } else {
      return getEmptyString();
    }    
    
  }

}

