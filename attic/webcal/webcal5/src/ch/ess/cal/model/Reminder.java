package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.BaseObject;
import ch.ess.cal.enums.TaskReminderBeforeEnum;


@Entity
public class Reminder extends BaseObject {

  private Integer minutesBefore;
  private TimeEnum timeUnit;
  private Integer time;
  private Long reminderDate;
  private BaseEvent event;
  private String email;
  private Locale locale;
  private TaskReminderBeforeEnum taskBefore;

  private Set<ReminderProperty> reminderProperties = new HashSet<ReminderProperty>();

  public Integer getMinutesBefore() {
    return this.minutesBefore;
  }

  public void setMinutesBefore(Integer minutesBefore) {
    this.minutesBefore = minutesBefore;
  }

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  public BaseEvent getEvent() {
    return this.event;
  }

  public void setEvent(BaseEvent event) {
    this.event = event;
  }

  @Column(length = 255)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public Long getReminderDate() {
    return reminderDate;
  }

  public void setReminderDate(Long reminderDate) {
    this.reminderDate = reminderDate;
  }

  @Column(length=1)
  @Type(type="ch.ess.base.hibernate.StringValuedEnumType",
        parameters = { @Parameter( name="enum", value = "ch.ess.cal.enums.TaskReminderBeforeEnum" ) } )  
  public TaskReminderBeforeEnum getTaskBefore() {
    return taskBefore;
  }

  public void setTaskBefore(TaskReminderBeforeEnum taskBefore) {
    this.taskBefore = taskBefore;
  }

  @Column(length=1)
  @Type(type="ch.ess.base.hibernate.StringValuedEnumType",
        parameters = { @Parameter( name="enum", value = "ch.ess.base.enums.TimeEnum" ) } )    
  public TimeEnum getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(TimeEnum timeUnit) {
    this.timeUnit = timeUnit;
  }

  @Column(length = 10)
  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "reminder")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<ReminderProperty> getReminderProperties() {
    return reminderProperties;
  }

  public void setReminderProperties(Set<ReminderProperty> reminderProperties) {
    this.reminderProperties = reminderProperties;
  }
}
