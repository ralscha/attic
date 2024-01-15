package ch.ess.task.web.task;

import java.util.Date;

import org.apache.lucene.index.Term;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.db.Persistent;
import ch.ess.common.search.SearchEngine;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.PersistentForm;
import ch.ess.common.web.WebContext;
import ch.ess.task.db.Task;
import ch.ess.task.web.WebUtils;
import ch.ess.task.web.task.search.UpdateTaskRunnable;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 14.09.2003 
  * 
  * @struts.action path="/newTask" name="taskForm" input=".task.list" scope="session" validate="false" roles="admin" parameter="add"
  * @struts.action path="/editTask" name="taskForm" input=".task.list" scope="session" validate="false" roles="admin" parameter="edit"
  * @struts.action path="/storeTask" name="taskForm" input=".task.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteTask" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".task.edit"
  * @struts.action-forward name="list" path="/listTask.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteTask.do" 
  * @struts.action-forward name="reload" path="/editTask.do" 
  */
public class TaskEditAction extends PersistentAction {


  protected int deletePersistent(Long id) throws Exception {
    Term t = new Term(Constants.SEARCH_ID, id.toString());
    SearchEngine.deleteTerm(t);

    return Task.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Task.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    
    Task newTask = new Task();
    newTask.setCreatedBy(WebUtils.getUser(WebContext.currentContext().getRequest()));
    newTask.setCreated(new Date());
    return newTask;
  }



  protected ActionForward store() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    PersistentForm persistentForm = (PersistentForm)ctx.getForm();

    if (isCancelled(ctx.getRequest())) {
      return findForward(Constants.FORWARD_LIST);
    }

    Persistent persistent = persistentForm.getPersistentFromForm();
    
    ActionForward forward = save(persistent);
    if (forward == null) {
      new UpdateTaskRunnable(persistent.getId()).run();
      return findForward(Constants.FORWARD_LIST);
    } else {
      return forward;
    }

  }

}