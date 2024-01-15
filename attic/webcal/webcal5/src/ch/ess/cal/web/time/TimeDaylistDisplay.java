package ch.ess.cal.web.time;

import java.io.Serializable;
import java.util.Date;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.model.User;
import ch.ess.cal.model.TimeTask;

public class TimeDaylistDisplay implements Serializable {

	//TaskID
	private String id;
	private String version;

	//
	private String activity;
	//
	private String comment;
	private Date taskTimeDate;
	//
	private String workinhour;
	//
	private String chargesStyle;
	//
	private String amount;
	//
	private String task;
	//
	private String timeId;
	//
	private String projectId;
	//
	private String customerNumber;
	//
	private String customer;
	//
	private String project;
	//
	private String projectNumber;
  
	private TimeTask timeTask;
	private User user;


	public String getActivity() {
		return activity;
	}
	
	public void setActivity(final String string) {
		activity = string;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(final String string) {
		comment = string;
	}
	 
	public Date getTaskTimeDate() {
		return taskTimeDate;
	}
	  
	public void setTaskTimeDate(final Date taskTimeDate) {
		this.taskTimeDate = taskTimeDate;
	}
	
	public String getWorkinhour() {
		return workinhour;
	}

	public void setWorkinhour(final String workInHour) {
		this.workinhour = workInHour;
	}
	  
	public TimeTask getTimeTask() {
		return timeTask;
	}
	
	public void setTimeTask(final TimeTask t) {
		timeTask = t;
	}
 
	public User getUser() {
		return user;
	}
	
	public void setUser(final User u) {
		user = u;
	}
	
	public void setChargesStyle(String chargesStyle) {
		this.chargesStyle = chargesStyle;
	}
		
	public String getChargesStyle() {
		return chargesStyle;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
		
	public String getAmount() {
		return amount;
	}
	
	public void setTask(String task) {
		this.task = task;
	}
	
	public String getTask() {
		return task;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getProject() {
		return project;
	}
	
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	
	public String getProjectNumber() {
		return projectNumber;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getVersion() {
		return version;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public String getTimeId() {
		return timeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}    
}
