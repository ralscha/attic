package ch.ess.base.enums;


/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:22 $
 */
public enum TimeEnum implements StringValuedEnum, MessageKeyedEnum {
  MINUTE("0", "time.minute"), 
  HOUR("1", "time.hour"), 
  DAY("2", "time.day"), 
  WEEK("3", "time.week"), 
  MONTH("4", "time.month"), 
  YEAR("5", "time.year");

  private String value;
  private String key;

  TimeEnum(String value, String key) {
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
