package ch.ess.task.web.project;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class ProjectDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Project");
  }

  public String getDelete() throws MalformedURLException {
    
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("Project", "name");
    } else {
      return getEmptyString();
    }    
  }

}
