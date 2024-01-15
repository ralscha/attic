package ch.ess.cal.web.resource;

import java.net.MalformedURLException;

import ch.ess.common.web.ListDecorator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/05 06:28:46 $
  */
public class ResourceDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Resource");
  }

  public String getDelete() throws MalformedURLException {        
    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {    
    return getDeleteString("Resource", "name");
    } else {
      return getEmptyString();
    }    
  }

}
