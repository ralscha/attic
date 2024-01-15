package ch.ess.cal.db;

import java.util.Map;

import ch.ess.common.db.Persistent;

/** A business entity class representing a Recurrence
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calRecurrence" lazy="true"
  */
public class Recurrence extends Persistent {

  private boolean active;
  private boolean exclude;
  private RecurrenceTypeEnum type;
  private Integer interval;
  private Integer dayOfWeekMask;
  private Integer dayOfMonth;
  private Integer monthOfYear;
  private Integer instance;
  private Integer occurrences;
  private Long duration;
  private boolean always;
  private Long until;
  
  private Long patternStartDate;
  private Long patternEndDate;
  private Long startTime;
  private Long endTime;
  
  
  private String rfcRule;
  private Event event;
  private Map days;


  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isExclude() {
    return this.exclude;
  }

  public void setExclude(boolean exclude) {
    this.exclude = exclude;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean b) {
    active = b;
  }


  /** 
  * @hibernate.property not-null="true"
  */
  public RecurrenceTypeEnum getType() {
    return this.type;
  }

  public void setType(RecurrenceTypeEnum type) {
    this.type = type;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Integer getInterval() {
    return this.interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Integer getDayOfWeekMask() {
    return this.dayOfWeekMask;
  }

  public void setDayOfWeekMask(Integer dayOfWeekMask) {
    this.dayOfWeekMask = dayOfWeekMask;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Integer getDayOfMonth() {
    return this.dayOfMonth;
  }

  public void setDayOfMonth(Integer dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Integer getMonthOfYear() {
    return this.monthOfYear;
  }

  public void setMonthOfYear(Integer monthOfYear) {
    this.monthOfYear = monthOfYear;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Integer getInstance() {
    return this.instance;
  }

  public void setInstance(Integer instance) {
    this.instance = instance;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Integer getOccurrences() {
    return this.occurrences;
  }

  public void setOccurrences(Integer occurrences) {
    this.occurrences = occurrences;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public Long getDuration() {
    return this.duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isAlways() {
    return this.always;
  }

  public void setAlways(boolean always) {
    this.always = always;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Long getUntil() {
    return this.until;
  }

  public void setUntil(Long until) {
    this.until = until;
  }

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getRfcRule() {
    return this.rfcRule;
  }

  public void setRfcRule(String rfcRule) {
    this.rfcRule = rfcRule;
  }


  /** 
  * @hibernate.property not-null="false"
  */
  public Long getPatternEndDate() {
    return patternEndDate;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public Long getPatternStartDate() {
    return patternStartDate;
  }


  public void setPatternEndDate(Long long1) {
    patternEndDate = long1;
  }

  public void setPatternStartDate(Long long1) {
    patternStartDate = long1;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Long getEndTime() {
    return endTime;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Long getStartTime() {
    return startTime;
  }

  public void setEndTime(Long long1) {
    endTime = long1;
  }

  public void setStartTime(Long long1) {
    startTime = long1;
  }
  
  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.Event"
  * @hibernate.column name="eventId" not-null="true" index="FK_Recurrence_Event"  
  */               
  public Event getEvent() {
    return this.event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }



  /**      
   * @hibernate.map lazy="true" cascade="all-delete-orphan" table="calRecurrenceDay"
   * @hibernate.collection-key column="recurrenceId"     
   * @hibernate.collection-index column="pos" type="integer"
   * @hibernate.collection-element column="recurrenceDay" type="long"
   */            
  public Map getDays() {
    return this.days;
  }

  public void setDays(Map days) {
    this.days = days;
  }





}
