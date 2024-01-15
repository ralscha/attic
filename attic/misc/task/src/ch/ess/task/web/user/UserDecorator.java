package ch.ess.task.web.user;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/05 06:28:58 $
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
