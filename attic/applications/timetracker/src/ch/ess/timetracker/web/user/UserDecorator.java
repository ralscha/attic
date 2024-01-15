package ch.ess.timetracker.web.user;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:20 $
 */
public class UserDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("User");
  }

  public String getDelete() throws MalformedURLException {
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("User", "userName");
    } else {
      return getEmptyString();
    }
  }

}
