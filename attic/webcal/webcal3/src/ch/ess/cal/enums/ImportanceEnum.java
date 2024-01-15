package ch.ess.cal.enums;

/**
 * @author sr 
 */
public enum ImportanceEnum implements StringValuedEnum, MessageKeyedEnum {
  LOW("L", "event.low"), NORMAL("N", "event.normal"), HIGH("H", "event.high");

  private String value;
  private String key;

  ImportanceEnum(String value, String key) {
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
