package ch.ess.cal.enums;

/**
 * @author sr
 */
public enum TransparencyEnum implements StringValuedEnum, MessageKeyedEnum {
  OPAQUE("O", "event.transparency.opaque"), TRANSPARENT("T", "event.transparency.transparent");

  private String value;
  private String key;

  TransparencyEnum(String value, String key) {
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
