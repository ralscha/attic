package ch.ess.timetracker.web.task;

import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.db.Customer;
import ch.ess.timetracker.db.Task;

/** 
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
    return Task.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Task.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Task();
  }
  
  protected ActionForward store() throws Exception {
    WebContext ctx = WebContext.currentContext();
    
    String change = ctx.getRequest().getParameter("change");
    if (!GenericValidator.isBlankOrNull(change)) {      
      TaskForm tf = (TaskForm)ctx.getForm();
      
      ctx.getSession().setAttribute("customerId", tf.getCustomerId());

      return ctx.getMapping().getInputForward(); 
    } else {
      return super.store();
    }
  }
  
  
  protected ActionForward add() throws Exception {
    TaskForm tf = (TaskForm)WebContext.currentContext().getForm();
    
    List customerList = HibernateSession.currentSession().find("from Customer as c where size(c.projects) > 0 order by c.name asc");
    if (customerList.size() > 0) {
      Customer c1 = (Customer)customerList.get(0);
      tf.setCustomerId(c1.getId());
      
      WebContext.currentContext().getSession().setAttribute("customerId", c1.getId());
      
    }
    
    return super.add();
  }

}