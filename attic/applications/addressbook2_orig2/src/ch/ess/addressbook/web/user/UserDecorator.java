package ch.ess.addressbook.web.user;

import java.net.MalformedURLException;

import net.sf.hibernate.HibernateException;
import ch.ess.common.web.ListDecorator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.7 $ $Date: 2003/11/11 19:05:26 $
  */
public class UserDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("User");
  }

  public String getDelete() throws MalformedURLException, HibernateException {        
    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {    
    return getDeleteString("User", "userName");
    } else {
      return getEmptyString();
    }    
  }

}
