package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import ch.ess.base.model.BaseObject;
import ch.ess.cal.enums.PosEnum;
import ch.ess.cal.enums.RecurrenceTypeEnum;


@Entity
public class Recurrence extends BaseObject {

  private Boolean active;
  private Boolean exclude;
  private RecurrenceTypeEnum type;
  private Integer interval;
  private Integer dayOfWeekMask;
  private Integer dayOfMonth;
  private Integer monthOfYear;
  private PosEnum instance;
  private Integer occurrences;
  private Long duration;
  private Boolean always;
  private Long until;

  private Long patternStartDate;
  private Long patternEndDate;
  private String rfcRule;
  private BaseEvent event;

  //  private Map<Integer,Long> days = new HashMap<Integer,Long>();

  @Column(nullable = false)
  public Boolean isExclude() {
    return this.exclude;
  }

  public void setExclude(Boolean exclude) {
    this.exclude = exclude;
  }

  @Column(nullable = false)
  public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean b) {
    active = b;
  }

  @Column(nullable = false, length=2)
  @Type(type="ch.ess.base.hibernate.StringValuedEnumType",
        parameters = { @Parameter( name="enum", value = "ch.ess.cal.enums.RecurrenceTypeEnum" ) } )  
  public RecurrenceTypeEnum getType() {
    return this.type;
  }

  public void setType(RecurrenceTypeEnum type) {
    this.type = type;
  }

  @Column(name="[interval]")
  public Integer getInterval() {
    return this.interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }

  public Integer getDayOfWeekMask() {
    return this.dayOfWeekMask;
  }

  public void setDayOfWeekMask(Integer dayOfWeekMask) {
    this.dayOfWeekMask = dayOfWeekMask;
  }

  public Integer getDayOfMonth() {
    return this.dayOfMonth;
  }

  public void setDayOfMonth(Integer dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public Integer getMonthOfYear() {
    return this.monthOfYear;
  }

  public void setMonthOfYear(Integer monthOfYear) {
    this.monthOfYear = monthOfYear;
  }

  @Column(length=1)
  @Type(type="ch.ess.base.hibernate.StringValuedEnumType",
        parameters = { @Parameter( name="enum", value = "ch.ess.cal.enums.PosEnum" ) } )
  public PosEnum getInstance() {
    return this.instance;
  }

  public void setInstance(PosEnum instance) {
    this.instance = instance;
  }

  public Integer getOccurrences() {
    return this.occurrences;
  }

  public void setOccurrences(Integer occurrences) {
    this.occurrences = occurrences;
  }

  @Column(nullable = false)
  public Long getDuration() {
    return this.duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  @Column(nullable = false)
  public Boolean isAlways() {
    return this.always;
  }

  public void setAlways(Boolean always) {
    this.always = always;
  }

  public Long getUntil() {
    return this.until;
  }

  public void setUntil(Long until) {
    this.until = until;
  }

  @Column(length = 255)
  public String getRfcRule() {
    return this.rfcRule;
  }

  public void setRfcRule(String rfcRule) {
    this.rfcRule = rfcRule;
  }

  public Long getPatternEndDate() {
    return patternEndDate;
  }

  @Column(nullable = false)
  public Long getPatternStartDate() {
    return patternStartDate;
  }

  public void setPatternEndDate(Long long1) {
    patternEndDate = long1;
  }

  public void setPatternStartDate(Long long1) {
    patternStartDate = long1;
  }

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  public BaseEvent getEvent() {
    return this.event;
  }

  public void setEvent(BaseEvent event) {
    this.event = event;
  }
}
