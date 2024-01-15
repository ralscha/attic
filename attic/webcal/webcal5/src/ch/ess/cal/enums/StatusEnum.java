package ch.ess.cal.enums;

import ch.ess.base.enums.MessageKeyedEnum;
import ch.ess.base.enums.StringValuedEnum;

public enum StatusEnum implements StringValuedEnum, MessageKeyedEnum {
  NOTSTARTED("N", "task.status.notstarted"),
  INPROCESS("I", "task.status.inprocess"), 
  COMPLETED("O", "task.status.completed"), 
  WAITING("W", "task.status.waiting"),
  DEFERRED("D", "task.status.deferred"),
  CANCELLED("C", "task.status.cancelled");
  
  private String value;
  private String key;

  StatusEnum(String value, String key) {
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
