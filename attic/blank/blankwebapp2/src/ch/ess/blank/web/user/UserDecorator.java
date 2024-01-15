package ch.ess.blank.web.user;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:18:22 $
 */
public class UserDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("User");
  }

  public String getDelete() throws MalformedURLException {
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("User", "userName");
    }
    return getEmptyString();

  }

}