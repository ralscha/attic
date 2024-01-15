package ch.ess.task.web.task;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMapping;

import ch.ess.common.web.PersistentForm;
import ch.ess.task.Constants;
import ch.ess.task.db.Priority;
import ch.ess.task.db.Project;
import ch.ess.task.db.Status;
import ch.ess.task.db.Task;
import ch.ess.task.db.User;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 14.09.2003 
  * @struts.form name="taskForm"
  */
public class TaskForm extends PersistentForm {

  private Long priorityId;
  private Long projectId;
  private Long statusId;
  private Long toUserId;
  private String createdBy;
  private String created;

  public TaskForm() {
    reset(null, null);
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    priorityId = null;
    projectId = null;
    statusId = null;
    toUserId = null;
  }

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

  public void setDescription(String descr) {
    ((Task)getPersistent()).setDescription(getTrimmedString(descr));
  }

  public String getDescription() {
    return ((Task)getPersistent()).getDescription();
  }

  public void setResolution(String res) {
    ((Task)getPersistent()).setResolution(getTrimmedString(res));
  }

  public String getResolution() {
    return ((Task)getPersistent()).getResolution();
  }

  /**   
   * @struts.validator type="date"
   * @struts.validator-args arg0resource="task.dateResolved"
   * @struts.validator-var name="datePatternStrict" value="dd.MM.yyyy" 
   */
  public void setResolved(String resolved) {
    if (!GenericValidator.isBlankOrNull(resolved)) {
      try {
        Date d = Constants.PARSE_DATE_FORMAT.parse(resolved);
        ((Task)getPersistent()).setResolved(d);
      } catch (ParseException pe) {
        pe.printStackTrace();
      }
    }
    ((Task)getPersistent()).setResolved(null);

  }

  public String getResolved() {
    Date d = ((Task)getPersistent()).getResolved();
    if (d != null) {
      return Constants.DEFAULT_DATE_FORMAT.format(d);
    } else {
      return "";
    }
  }

  /**   
   * @struts.validator type="integer,intRange"
   * @struts.validator-args arg0resource="task.complete"
   * @struts.validator-args arg1value="0"
   * @struts.validator-args arg2value="100"
   * @struts.validator-var name="max" value="100"
   * @struts.validator-var name="min" value="0"
   */
  public void setComplete(Integer complete) {
    ((Task)getPersistent()).setComplete(complete);
  }

  public Integer getComplete() {
    return ((Task)getPersistent()).getComplete();
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public Long getPriorityId() {
    return priorityId;
  }

  public Long getProjectId() {
    return projectId;
  }

  public Long getStatusId() {
    return statusId;
  }

  public Long getToUserId() {
    return toUserId;
  }

  public String getCreated() {
    return created;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="task.priority"
   */
  public void setPriorityId(Long id) {
    priorityId = id;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="task.project"
   */
  public void setProjectId(Long id) {
    projectId = id;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="task.status"
   */
  public void setStatusId(Long id) {
    statusId = id;
  }

  public void setToUserId(Long id) {
    toUserId = id;
  }

  protected void fromForm() throws HibernateException {
    Task task = (Task)getPersistent();

    if (getToUserId().longValue() > 0) {
      task.setAssignedTo(User.load(getToUserId()));
    } else {
      task.setAssignedTo(null);
    }

    task.setStatus(Status.load(getStatusId()));
    task.setProject(Project.load(getProjectId()));
    task.setPriority(Priority.load(getPriorityId()));

  }

  protected void toForm() {

    Task task = (Task)getPersistent();
    createdBy = null;

    if (task != null) {

      User u = task.getCreatedBy();
      if (u != null) {
        createdBy = u.getName() + " " + u.getFirstName();
      }

      if (task.getAssignedTo() != null) {
        setToUserId(task.getAssignedTo().getId());
      }
      if (task.getPriority() != null) {
        setPriorityId(task.getPriority().getId());
      }
      if (task.getProject() != null) {
        setProjectId(task.getProject().getId());
      }
      if (task.getStatus() != null) {
        setStatusId(task.getStatus().getId());
      }

      if (task.getCreated() != null) {
        created = Constants.FULL_DATE_FORMAT.format(task.getCreated());
      } else {
        created = null;
      }
    }

  }

}
