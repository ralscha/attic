package ch.ess.blank.web.resource;

import ch.ess.blank.db.TextResource;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 * 
 * @struts.action path="/newResource" name="resourceForm" input=".resource.list" scope="session" validate="false" parameter="add" roles="admin" 
 * @struts.action path="/editResource" name="resourceForm" input=".resource.list" scope="session" validate="false" parameter="edit" roles="admin"
 * @struts.action path="/storeResource" name="resourceForm" input=".resource.edit" scope="session" validate="true" parameter="store" roles="admin"
 * @struts.action path="/deleteResource" parameter="delete" validate="false" roles="admin"
 *
 * @struts.action-forward name="edit" path=".resource.edit"
 * @struts.action-forward name="list" path="/listResource.do" redirect="true"
 * @struts.action-forward name="delete" path="/deleteResource.do" 
 * @struts.action-forward name="reload" path="/editResource.do" 
 */
public class ResourceEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return TextResource.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return TextResource.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new TextResource();
  }
}