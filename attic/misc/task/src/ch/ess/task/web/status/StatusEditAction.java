package ch.ess.task.web.status;

import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.task.db.Status;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * 
  * @struts.action path="/newStatus" name="statusForm" input=".status.list" scope="session" validate="false" roles="admin" parameter="add"
  * @struts.action path="/editStatus" name="statusForm" input=".status.list" scope="session" validate="false" roles="admin" parameter="edit"
  * @struts.action path="/storeStatus" name="statusForm" input=".status.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteStatus" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".status.edit"
  * @struts.action-forward name="list" path="/listStatus.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteStatus.do" 
  * @struts.action-forward name="reload" path="/editStatus.do" 
  */
public class StatusEditAction extends PersistentAction {


  protected int deletePersistent(Long id) throws Exception {
    return Status.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Status.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Status();
  }

}