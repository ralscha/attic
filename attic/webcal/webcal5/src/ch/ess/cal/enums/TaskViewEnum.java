package ch.ess.cal.enums;

import ch.ess.base.enums.MessageKeyedEnum;
import ch.ess.base.enums.StringValuedEnum;

public enum TaskViewEnum implements StringValuedEnum, MessageKeyedEnum {
  SIMPLE_LIST("0", "task.view.simple"), 
  DETAILED_LIST("1", "task.view.detailed"), 
  ACTIVE_TASKS("2", "task.view.active"), 
  NEXT_SEVEN_DAYS("3", "task.view.next"), 
  OVERDUE_TASKS("4", "task.view.overdue");

  private String value;
  private String key;

  TaskViewEnum(String value, String key) {
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
