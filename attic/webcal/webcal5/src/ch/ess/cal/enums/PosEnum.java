package ch.ess.cal.enums;

import ch.ess.base.enums.MessageKeyedEnum;
import ch.ess.base.enums.StringValuedEnum;


public enum PosEnum implements StringValuedEnum, MessageKeyedEnum {

  FIRST("1", "event.recurrence.first"), 
  SECOND("2", "event.recurrence.second"), 
  THIRD("3", "event.recurrence.third"), 
  FOURTH("4", "event.recurrence.fourth"), 
  LAST("5", "event.recurrence.last");

  private String value;
  private String key;

  PosEnum(String value, String key) {
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
