package ch.ess.addressbook.web.resource;

import java.net.MalformedURLException;

import net.sf.hibernate.HibernateException;
import ch.ess.common.web.ListDecorator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.3 $ $Date: 2003/11/11 19:04:44 $
  */
public class ResourceDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Resource");
  }

  public String getDelete() throws MalformedURLException, HibernateException {        
    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {    
    return getDeleteString("Resource", "name");
    } else {
      return getEmptyString();
    }    
  }

}
