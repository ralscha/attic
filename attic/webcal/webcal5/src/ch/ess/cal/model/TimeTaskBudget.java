package ch.ess.cal.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;

@Entity
public class TimeTaskBudget extends BaseObject {

  private TimeTask timeTask;
  private TimeProject timeProject;
  private Date date;
  private BigDecimal amount;
  private String comment;

  @ManyToOne
  @JoinColumn(name = "timeTaskId", nullable = true)
  public TimeTask getTimeTask() {
    return timeTask;
  }

  public void setTimeTask(TimeTask timeTask) {
    this.timeTask = timeTask;
  }

  @ManyToOne
  @JoinColumn(name = "timeProjectId", nullable = true)
  public TimeProject getTimeProject() {
    return timeProject;
  }

  public void setTimeProject(TimeProject timeProject) {
    this.timeProject = timeProject;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Column(precision = 18, scale = 3)
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Column(length = 255)
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

}
