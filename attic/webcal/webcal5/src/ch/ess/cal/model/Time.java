package ch.ess.cal.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;

@Entity
public class Time extends BaseObject {

  private String activity;
  private String comment;
  private Date taskTimeDate;
  private BigDecimal workInHour;
  private BigDecimal hourRate;
  private BigDecimal cost;
  private Integer startHour;
  private Integer startMin;
  private Integer endHour;
  private Integer endMin;
  private String chargesStyle;
  private BigDecimal amount;
  
  private TimeTask timeTask;
  private TimeProject timeProject;
  private User user;
  

  @Column(length = 255)
  public String getActivity() {
    return activity;
  }

  public void setActivity(final String string) {
    activity = string;
  }

  @Column(length = 1000)
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
  
   
  public Integer getEndHour() {
    return endHour;
  }

  public void setEndHour(Integer endHour) {
    this.endHour = endHour;
  }

   
  public Integer getEndMin() {
    return endMin;
  }

  public void setEndMin(Integer endMin) {
    this.endMin = endMin;
  }

   
  public Integer getStartHour() {
    return startHour;
  }

  public void setStartHour(Integer startHour) {
    this.startHour = startHour;
  }

   
  public Integer getStartMin() {
    return startMin;
  }

  public void setStartMin(Integer startMin) {
    this.startMin = startMin;
  }

  @Column(precision = 18, scale = 3) 
  public BigDecimal getWorkInHour() {
    return workInHour;
  }
  
  public void setWorkInHour(final BigDecimal workInHour) {
    this.workInHour = workInHour;
  }
  
  @Column(precision = 18, scale = 3)
  public BigDecimal getHourRate() {
    return hourRate;
  }
  
  public void setHourRate(final BigDecimal hourRate) {
    this.hourRate = hourRate;
  }
  
  @Column(precision = 18, scale = 3) 
  public BigDecimal getCost() {
    return cost;
  }
  public void setCost(final BigDecimal cost) {
    this.cost = cost;
  }  
  
  @ManyToOne
  @JoinColumn(name = "timeTaskId", nullable = true) 
  public TimeTask getTimeTask() {
    return timeTask;
  }

  public void setTimeTask(final TimeTask t) {
    timeTask = t;
  }
  

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false) 
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
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	@ManyToOne
	@JoinColumn(name = "timeProjectId", nullable = true) 
	public TimeProject getTimeProject() {
		return timeProject;
	}

	public void setTimeProject(TimeProject timeProject) {
		this.timeProject = timeProject;
	}   
}
