package ch.ess.cal.web.calresource;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

public class CalResourceDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("CalResource");
  }

  public String getDelete() throws MalformedURLException {
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("CalResource", "name");
    } else {
      return getEmptyString();
    }
  }

}