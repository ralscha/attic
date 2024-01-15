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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import ch.ess.cal.enums.TimeEnum;

/** A business entity class representing a Reminder
 * 
 * @author sr
 * @version $Revision: 1.9 $ $Date: 2005/05/04 09:15:42 $ 
 */
@Entity
public class Reminder extends BaseObject {

  private Integer minutesBefore;
  private TimeEnum timeUnit;
  private Integer time;
  private Event event;
  private String email;
  private Locale locale;

  private Set<ReminderProperty> reminderProperties = new HashSet<ReminderProperty>();

  @Column(nullable = false)
  public Integer getMinutesBefore() {
    return this.minutesBefore;
  }

  public void setMinutesBefore(Integer minutesBefore) {
    this.minutesBefore = minutesBefore;
  }

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  public Event getEvent() {
    return this.event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  @Column(length = 255)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(nullable = false)
  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  @Column(nullable = false, length=1)
  @Type(type="ch.ess.cal.persistence.hibernate.StringValuedEnumType",
        parameters = { @Parameter( name="enum", value = "ch.ess.cal.enums.TimeEnum" ) } )    
  public TimeEnum getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(TimeEnum timeUnit) {
    this.timeUnit = timeUnit;
  }

  @Column(nullable = false, length = 10)
  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "reminder")
  public Set<ReminderProperty> getReminderProperties() {
    return reminderProperties;
  }

  public void setReminderProperties(Set<ReminderProperty> reminderProperties) {
    this.reminderProperties = reminderProperties;
  }
}
