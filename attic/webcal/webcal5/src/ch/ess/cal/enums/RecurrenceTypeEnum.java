package ch.ess.cal.enums;

import ch.ess.base.enums.MessageKeyedEnum;
import ch.ess.base.enums.StringValuedEnum;


public enum RecurrenceTypeEnum implements StringValuedEnum, MessageKeyedEnum {
  DAILY("0", "event.recurrence.daily"), 
  WEEKLY("1", "event.recurrence.weekly"), 
  MONTHLY("2", "event.recurrence.monthly"), 
  MONTHLY_NTH("3", "event.recurrence.monthlynth"), 
  YEARLY("4", "event.recurrence.yearly"), 
  YEARLY_NTH("5","event.recurrence.yearlynth"), 
  DATES("10", "event.recurrence.dates");

  private String value;
  private String key;

  RecurrenceTypeEnum(String value, String key) {
    this.value = value;
    this.key = key;
  }

  public String getValue() {
    return this.value;
  }

  public String getKey() {
    return this.key;
  }

}
