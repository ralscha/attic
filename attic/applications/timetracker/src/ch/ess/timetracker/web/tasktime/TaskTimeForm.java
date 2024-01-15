package ch.ess.timetracker.web.tasktime;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import ch.ess.common.web.PersistentForm;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.Constants;
import ch.ess.timetracker.db.Task;
import ch.ess.timetracker.db.TaskTime;
import ch.ess.timetracker.db.User;
import ch.ess.timetracker.web.WebUtils;

/** 
  * @struts.form name="taskTimeForm"
  */
public class TaskTimeForm extends PersistentForm {

  private Long projectId;
  private Long customerId;
  private Long taskId;
  private Long userId;
  

  /**   
   * @struts.validator type="required,date"
   * @struts.validator-args arg0resource="tasktime.date"
   * @struts.validator-var name="datePatternStrict" value="dd.MM.yyyy"  
   */
  public void setTaskTimeDate(String date) {
    try {
      Date d = Constants.DATE_FORMAT.parse(date);
      ((TaskTime)getPersistent()).setTaskTimeDate(d);
    } catch (ParseException e) {
      //no action
    }
  }

  public String getTaskTimeDate() {
    Date d = ((TaskTime)getPersistent()).getTaskTimeDate();
    if (d != null) {
      return Constants.DATE_FORMAT.format(d);    
    } else {
      Calendar today = new GregorianCalendar();
      return Constants.DATE_FORMAT.format(today.getTime());
    }
    
    
  }  
  
  /**   
   * @struts.validator type="required,float"
   * @struts.validator-args arg0resource="tasktime.hours"  
   */
  public void setWorkInHour(String dec) {

    try {
      BigDecimal bd = new BigDecimal(dec);
      ((TaskTime)getPersistent()).setWorkInHour(bd);
    } catch (NumberFormatException e) {
      //no action
    }
  }

  public String getWorkInHour() {
    BigDecimal bd = ((TaskTime)getPersistent()).getWorkInHour();
    if (bd != null) {
      return Constants.DECIMAL_FORMAT.format(bd);    
    }
    
    return null;
  }  
  
  /**   
   * @struts.validator type="required,float"
   * @struts.validator-args arg0resource="tasktime.hourRate"  
   */
  public void setHourRate(String hourRate) {
    try {
      BigDecimal bd = new BigDecimal(hourRate);
      ((TaskTime)getPersistent()).setHourRate(bd);
    } catch (NumberFormatException e) {
      //no action
    }
  }  

  public String getHourRate() {
    BigDecimal bd = ((TaskTime)getPersistent()).getHourRate();
    if (bd != null) {
      return ch.ess.timetracker.Constants.DECIMAL_FORMAT.format(bd);    
    }
    
    return null;
  }
  
  
  
  
  public void setComment(String comment) {
    ((TaskTime)getPersistent()).setComment(getTrimmedString(comment));
  }

  public String getComment() {
    return ((TaskTime)getPersistent()).getComment();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="tasktime.description"  
   */
  public void setDescription(String description) {
    ((TaskTime)getPersistent()).setDescription(getTrimmedString(description));
  }

  public String getDescription() {
    return ((TaskTime)getPersistent()).getDescription();
  }  
  
  
  public Long getTaskId() {
    return taskId;
  }
  
  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="task"
   */  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  
  public Long getUserId() {
    return userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
   
  
  public Long getProjectId() {
    return projectId;
  }
     
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
    
    TaskTime taskTime = (TaskTime)getPersistent();          
    taskTime.setTask(Task.load(taskId));
    
    if (userId != null) {
      User user = User.load(userId);
      taskTime.setUser(user);      
    } else if (taskTime.getUser() == null) {
      User user = WebUtils.getUser(WebContext.currentContext().getRequest());
      taskTime.setUser(user);      
    }
    
    if ((taskTime.getHourRate() != null) && (taskTime.getWorkInHour() != null)) {
      taskTime.setCost(taskTime.getHourRate().multiply(taskTime.getWorkInHour()));
    }
        
  }

  protected void toForm() throws HibernateException {
    
    taskId = null;    
    TaskTime taskTime = (TaskTime)getPersistent();    
    if (taskTime != null) {
      if (taskTime.getTask() != null) {
        taskId = taskTime.getTask().getId();
        
        projectId = taskTime.getTask().getProject().getId();
        customerId = taskTime.getTask().getProject().getCustomer().getId();
        WebContext.currentContext().getSession().setAttribute("customerId", customerId);        
        WebContext.currentContext().getSession().setAttribute("projectId", projectId);
      }
      
      if (taskTime.getHourRate() == null) {
        User user = WebUtils.getUser(WebContext.currentContext().getRequest());
        if (user.getHourRate() != null) {
          taskTime.setHourRate(user.getHourRate());
        }
      }     
      
      if (taskTime.getUser() != null) {
        userId = taskTime.getUser().getId();
      } else {
        userId = null;
      }
    }
  }

  
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    String changeProject = request.getParameter("changeProject");
    String changeCustomer = request.getParameter("changeCustomer"); 
    
    if (GenericValidator.isBlankOrNull(changeProject) && GenericValidator.isBlankOrNull(changeCustomer)) {
      ActionErrors errors = super.validate(mapping, request);
      return errors;
    } else {
      return null;
    }     
  }


}
