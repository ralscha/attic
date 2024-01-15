package ch.ess.cal.enums;

import ch.ess.base.enums.MessageKeyedEnum;
import ch.ess.base.enums.StringValuedEnum;


public enum TaskReminderBeforeEnum implements StringValuedEnum, MessageKeyedEnum {
  BEFORE_START("S", "task.reminder.before.start"), 
  BEFORE_DUE("D", "task.reminder.before.due");

  private String value;
  private String key;

  TaskReminderBeforeEnum(String value, String key) {
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
