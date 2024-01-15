package ch.ess.task.web.priority;

import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.task.db.Priority;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * 
  * @struts.action path="/newPriority" name="priorityForm" input=".priority.list" scope="session" validate="false" roles="admin" parameter="add"
  * @struts.action path="/editPriority" name="priorityForm" input=".priority.list" scope="session" validate="false" roles="admin" parameter="edit"
  * @struts.action path="/storePriority" name="priorityForm" input=".priority.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deletePriority" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".priority.edit"
  * @struts.action-forward name="list" path="/listPriority.do" redirect="true"
  * @struts.action-forward name="delete" path="/deletePriority.do" 
  * @struts.action-forward name="reload" path="/editPriority.do" 
  */
public class PriorityEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Priority.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Priority.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Priority();
  }
}