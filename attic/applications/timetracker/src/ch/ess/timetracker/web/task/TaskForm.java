package ch.ess.timetracker.web.task;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import ch.ess.common.web.PersistentForm;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.db.Project;
import ch.ess.timetracker.db.Task;

/** 
  * @struts.form name="taskForm"
  */
public class TaskForm extends PersistentForm {


  private Long projectId;
  private Long customerId;
  
  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="task.name"
   */
  public void setName(String name) {
    ((Task)getPersistent()).setName(getTrimmedString(name));
  }

  public String getName() {
    return ((Task)getPersistent()).getName();
  }

  
  public void setDescription(String description) {
    ((Task)getPersistent()).setDescription(getTrimmedString(description));
  }

  public String getDescription() {
    return ((Task)getPersistent()).getDescription();
  }
  
    
  public Long getProjectId() {
    return projectId;
  }
  
  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="project"
   */    
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }  
  
  public Long getCustomerId() {
    return customerId;
  }
  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  
  protected void fromForm() throws HibernateException {
    Task task = (Task)getPersistent();            
    task.setProject(Project.load(projectId));
  }

  protected void toForm() {
    projectId = null;
    customerId = null;
        
    Task task = (Task)getPersistent();
    if (task != null) {
      if (task.getProject() != null) {
        projectId = task.getProject().getId();
        customerId = task.getProject().getCustomer().getId();
        WebContext.currentContext().getSession().setAttribute("customerId", customerId);
      }
    }        
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    String change = request.getParameter("change");
    if (GenericValidator.isBlankOrNull(change)) {
      ActionErrors errors = super.validate(mapping, request);
      return errors;
    } else {
      return null;
    }     
  }
  
}


