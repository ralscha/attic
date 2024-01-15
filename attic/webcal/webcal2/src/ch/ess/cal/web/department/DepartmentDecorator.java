package ch.ess.cal.web.department;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class DepartmentDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Department");
  }

  public String getDelete() throws MalformedURLException {
    
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {

      return getDeleteString("Department", "name");
    } else {
      return getEmptyString();
    }
  }

}
