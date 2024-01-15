package ch.ess.cal.web.calresource;

import ch.ess.cal.db.Resource;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 30.09.2003
  *
  * @struts.action path="/newCalResource" name="calResourceForm" input=".calresource.list" scope="session" validate="false" parameter="add" roles="admin" 
  * @struts.action path="/editCalResource" name="calResourceForm" input=".calresource.list" scope="session" validate="false" parameter="edit" roles="admin"
  * @struts.action path="/storeCalResource" name="calResourceForm" input=".calresource.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteCalResource" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".calresource.edit"
  * @struts.action-forward name="list" path="/listCalResource.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteCalResource.do" 
  * @struts.action-forward name="reload" path="/editCalResource.do" 
  */
public class CalResourceEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Resource.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Resource.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Resource();
  }
}