package ch.ess.timetracker.web.tasktime;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.db.Customer;
import ch.ess.timetracker.db.Project;
import ch.ess.timetracker.db.TaskTime;
import ch.ess.timetracker.db.User;
import ch.ess.timetracker.web.WebUtils;

/** 
  * @struts.action path="/newTaskTime" name="taskTimeForm" input=".tasktime.list" scope="session" validate="false" parameter="add"
  * @struts.action path="/editTaskTime" name="taskTimeForm" input=".tasktime.list" scope="session" validate="false" parameter="edit" 
  * @struts.action path="/storeTaskTime" name="taskTimeForm" input=".tasktime.edit" scope="session" validate="true" parameter="store"
  * @struts.action path="/deleteTaskTime" parameter="delete" validate="false"
  *
  * @struts.action-forward name="edit" path=".tasktime.edit"
  * @struts.action-forward name="list" path="/listTaskTime.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteTaskTime.do" 
  * @struts.action-forward name="reload" path="/editTaskTime.do" 
  */
public class TaskTimeEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return TaskTime.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return TaskTime.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new TaskTime();
  }

  protected ActionForward store() throws Exception {
    WebContext ctx = WebContext.currentContext();
    
    String changeProject = ctx.getRequest().getParameter("changeProject");
    String changeCustomer = ctx.getRequest().getParameter("changeCustomer");
    
    if (!GenericValidator.isBlankOrNull(changeProject)) {      
      TaskTimeForm ttf = (TaskTimeForm)ctx.getForm();
      
      ctx.getSession().setAttribute("customerId", ttf.getCustomerId());
      ctx.getSession().setAttribute("projectId", ttf.getProjectId());
      
      return ctx.getMapping().getInputForward(); 
    } else if (!GenericValidator.isBlankOrNull(changeCustomer)) {      
      TaskTimeForm ttf = (TaskTimeForm)ctx.getForm();
      
      ctx.getSession().setAttribute("customerId", ttf.getCustomerId());      
      Customer c = Customer.load(ttf.getCustomerId());
      
      Long projectId = ((Project)c.getProjects().iterator().next()).getId();      
      
      ttf.setProjectId(projectId);
      ctx.getSession().setAttribute("projectId", projectId);
      
      return ctx.getMapping().getInputForward(); 
    } else {
      return super.store();
    }
  }
  
  
  protected ActionForward add() throws Exception {
    TaskTimeForm ttf = (TaskTimeForm)WebContext.currentContext().getForm();
    
    WebContext ctx = WebContext.currentContext();
    
    Collection customers = null;
    if (ctx.getRequest().isUserInRole("admin")) {
      customers = HibernateSession.currentSession().find("from Customer as c where size(c.projects) > 0 order by c.name asc");
    } else {
      User user = WebUtils.getUser(ctx.getRequest());      
      customers = user.getCustomers();
    }
    
    
    
    if (customers.size() > 0) {
      Customer c = getOneCustomer(customers);
      if (c != null) {
        ttf.setCustomerId(c.getId());      
        ctx.getSession().setAttribute("customerId", c.getId());
       
        Long projectId = ((Project)c.getProjects().iterator().next()).getId();
        ttf.setProjectId(projectId);
        ctx.getSession().setAttribute("projectId", projectId);
      }      
    }    
    return super.add();
  }
  
  private Customer getOneCustomer(Collection l) {
    for (Iterator it = l.iterator(); it.hasNext();) {
      Customer c = (Customer)it.next();
      if (hasProjectTasks(c.getProjects())) {
        return c;
      }
    }
    return null;
  }
  
  private boolean hasProjectTasks(Set projects) {
    for (Iterator it = projects.iterator(); it.hasNext(); ) {
      Project p = (Project) it.next();
      if (p.getTasks().size() > 0) {
        return true;
      }      
    }    
    return false;
  }
  
}