package ch.ess.cal.web.user;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $
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
