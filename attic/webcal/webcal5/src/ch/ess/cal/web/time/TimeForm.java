package ch.ess.cal.web.time;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.annotation.struts.Variable;
import ch.ess.base.web.AbstractForm;

public class TimeForm extends AbstractForm {

  private String userId;
  private String taskTimeDate;
  private String startHour;
  private String startMin;
  private String endHour;
  private String endMin;
  private String workInHour;
  private String hourRate;
  private String activity;
  private String comment;
  private String cost;
  private String timeTaskId;
  private String timeProjectId;
  private String timeCustomerId;
  private String newProject;
  private String newTask;
  private String budget;
  private String budgetOpen;
  private String chargesStyle;
  private String amount;

  @Override
  public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {

    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    if (!(StringUtils.isNotBlank(workInHour) || (StringUtils.isNotBlank(startHour) && StringUtils.isNotBlank(endHour)))) {
      errors.add("workInHour", new ActionMessage("time.error.nohourorstartend"));
    }

    //EXCLUDE because now its possible to add times without tasks
  /*  if (!(StringUtils.isNotBlank(timeTaskId) || 
         (StringUtils.isNotBlank(newTask) && StringUtils.isNotBlank(timeProjectId)) || 
         (StringUtils.isNotBlank(newTask) && StringUtils.isNotBlank(newProject) && StringUtils.isNotBlank(timeCustomerId)))) {
      errors.add("timeTaskId", new ActionMessage("time.error.notask"));
    }*/
    
    if (!(StringUtils.isNotBlank(timeProjectId) || 
            (StringUtils.isNotBlank(newProject) && StringUtils.isNotBlank(timeCustomerId)))) {
         errors.add("timeTaskId", new ActionMessage("time.error.noproject"));
    }

    return errors;
  }

  @ValidatorField(key = "time.user", required = true)
  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }

  @ValidatorField(key = "time.taskTimeDate", required = false, validators = @Validator(name = "date", vars = @Variable(name = "datePatternStrict", value = "dd.MM.yyyy")))
  public void setTaskTimeDate(final String taskTimeDate) {
    this.taskTimeDate = taskTimeDate;
  }

  public String getTaskTimeDate() {
    return taskTimeDate;
  }

  @ValidatorField(key = "time.startHour", required = false, validators = @Validator(name = "integer"))
  public void setStartHour(final String startHour) {
    this.startHour = startHour;
  }

  public String getStartHour() {
    return startHour;
  }

  @ValidatorField(key = "time.startMin", required = false, validators = @Validator(name = "integer"))
  public void setStartMin(final String startMin) {
    this.startMin = startMin;
  }

  public String getStartMin() {
    return startMin;
  }

  @ValidatorField(key = "time.endHour", required = false, validators = @Validator(name = "integer"))
  public void setEndHour(final String endHour) {
    this.endHour = endHour;
  }

  public String getEndHour() {
    return endHour;
  }

  @ValidatorField(key = "time.endMin", required = false, validators = @Validator(name = "integer"))
  public void setEndMin(final String endMin) {
    this.endMin = endMin;
  }

  public String getEndMin() {
    return endMin;
  }

  @ValidatorField(key = "time.workInHour", required = false, validators = @Validator(name = "float"))
  public void setWorkInHour(final String workInHour) {
    this.workInHour = workInHour;
  }

  public String getWorkInHour() {
    return workInHour;
  }

  @ValidatorField(key = "time.hourRate", required = false, validators = @Validator(name = "float"))
  public void setHourRate(final String hourRate) {
    this.hourRate = hourRate;
  }

  public String getHourRate() {
    return hourRate;
  }

  public void setActivity(final String description) {
    this.activity = description;
  }

  public String getActivity() {
    return activity;
  }

  public void setComment(final String comment) {
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  @ValidatorField(key = "time.cost", required = false, validators = @Validator(name = "float"))
  public void setCost(final String cost) {
    this.cost = cost;
  }

  public String getCost() {
    return cost;
  }
  
  public void setTimeTaskId(final String timeTaskId) {
    this.timeTaskId = timeTaskId;
  }

  public String getTimeTaskId() {
    return timeTaskId;
  }

  public String getTimeCustomerId() {
    return timeCustomerId;
  }

  public void setTimeCustomerId(String timeCustomerId) {
    this.timeCustomerId = timeCustomerId;
  }

  public String getTimeProjectId() {
    return timeProjectId;
  }

  public void setTimeProjectId(String timeProjectId) {
    this.timeProjectId = timeProjectId;
  }

  public String getNewProject() {
    return newProject;
  }

  public void setNewProject(String newProject) {
    this.newProject = newProject;
  }

  public String getNewTask() {
    return newTask;
  }

  public void setNewTask(String newTask) {
    this.newTask = newTask;
  }

  
  public String getBudget() {
    return budget;
  }

  
  public void setBudget(String budget) {
    this.budget = budget;
  }

  
  public String getBudgetOpen() {
    return budgetOpen;
  }

  
  public void setBudgetOpen(String budgetOpen) {
    this.budgetOpen = budgetOpen;
  }

  @ValidatorField(key = "time.chargesStyle", required = false)
	public void setChargesStyle(String chargesStyle) {
		this.chargesStyle = chargesStyle;
	}
	
	public String getChargesStyle() {
		return chargesStyle;
	}
	
	@ValidatorField(key = "time.amount", required = false, validators = @Validator(name = "float"))
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getAmount() {
		return amount;
	}

}
