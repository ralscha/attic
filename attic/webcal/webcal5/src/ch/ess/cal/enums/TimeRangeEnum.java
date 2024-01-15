package ch.ess.cal.enums;

import ch.ess.base.enums.MessageKeyedEnum;
import ch.ess.base.enums.StringValuedEnum;


public enum TimeRangeEnum implements StringValuedEnum, MessageKeyedEnum {
    
  DAY("D", "time.range.day"),  
  WEEK("W", "time.range.week"),
  MONTH("M", "time.range.month"),
  QUARTER("Q", "time.range.quarter"),
  SEMESTER("S", "time.range.semester"),
  YEAR("Y", "time.range.year");
  
  private String value;
  private String key;

  TimeRangeEnum(String value, String key) {
    this.value = value;
    this.key = key;
  }

  @Override
public String getValue() {
    return this.value;
  }

  @Override
public String getKey() {
    return this.key;
  }

}
