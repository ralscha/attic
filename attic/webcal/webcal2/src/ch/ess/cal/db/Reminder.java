package ch.ess.cal.db;

import java.util.Locale;

import net.sf.hibernate.HibernateException;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Reminder
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calReminder" lazy="true"
  */
public class Reminder extends Persistent {

  private int minutesBefore;
  private String timeUnit;
  private int time;
  private Event event;
  private String email;
  private Locale locale;

  /** 
  * @hibernate.property not-null="true"
  */
  public int getMinutesBefore() {
    return this.minutesBefore;
  }

  public void setMinutesBefore(int minutesBefore) {
    this.minutesBefore = minutesBefore;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.Event"
  * @hibernate.column name="eventId" not-null="true" index="FK_Reminder_Event"  
  */                 
  public Event getEvent() {
    return this.event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
    
  /** 
  * @hibernate.property length="100" not-null="false"
  */  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  /** 
  * @hibernate.property length="15" not-null="true"
  */
  public String getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(String timeUnit) {
    this.timeUnit = timeUnit;
  }
  
  /** 
  * @hibernate.property length="10" not-null="true"
  */
  public Locale getLocale() {
    return locale;
  }
  
  public void setLocale(Locale locale) {
    this.locale = locale;
  }  

  //load
  public static Reminder load(Long reminderId) throws HibernateException {
    return (Reminder)HibernateSession.currentSession().load(Reminder.class, reminderId);
  }


}
