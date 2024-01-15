package ch.ess.cal.enums;

/**
 * @author sr
 */
public enum SensitivityEnum implements StringValuedEnum, MessageKeyedEnum {
  PUBLIC("P", "event.public"), PRIVATE("R", "event.private"), CONFIDENTIAL("C", "event.confidential");

  private String value;
  private String key;

  SensitivityEnum(String value, String key) {
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
