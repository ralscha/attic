package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import ch.ess.cal.enums.StatusEnum;

@Entity
@DiscriminatorValue("T")
public class Task extends BaseEvent {

  private Long dueDate;
  private Long completeDate;
  private StatusEnum status;
  private Integer complete;
  private Boolean allDayDue;

  public Long getDueDate() {
    return dueDate;
  }

  public void setDueDate(Long dueDate) {
    this.dueDate = dueDate;
  }

  public Long getCompleteDate() {
    return completeDate;
  }

  public void setCompleteDate(Long completed) {
    this.completeDate = completed;
  }

  @Column(length = 1)
  @Type(type = "ch.ess.base.hibernate.StringValuedEnumType", parameters = {@Parameter(name = "enum", value = "ch.ess.cal.enums.StatusEnum")})
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Integer getComplete() {
    return complete;
  }

  public void setComplete(Integer complete) {
    this.complete = complete;
  }

  public Boolean getAllDayDue() {
    return allDayDue;
  }

  public void setAllDayDue(Boolean allDayDue) {
    this.allDayDue = allDayDue;
  }

}
