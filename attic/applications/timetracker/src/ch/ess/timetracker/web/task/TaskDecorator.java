package ch.ess.timetracker.web.task;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class TaskDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Task");
  }

  public String getDelete() throws MalformedURLException {        
    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {    
    return getDeleteString("Task", "name");
    } else {
      return getEmptyString();
    }    
  }

}
