package ch.ess.cal.db;

import net.sf.hibernate.PersistentEnum;

public class SensitivityEnum implements PersistentEnum {

  private final int code;

  private SensitivityEnum(int code) {
    this.code = code;
  }

  public static final SensitivityEnum NO_SENSITIVITY = new SensitivityEnum(0);
  public static final SensitivityEnum PERSONAL = new SensitivityEnum(1);
  public static final SensitivityEnum PRIVATE = new SensitivityEnum(2);
  public static final SensitivityEnum CONFIDENTIAL = new SensitivityEnum(3);

  public int toInt() {
    return code;
  }

  public static SensitivityEnum fromInt(int code) {
    switch (code) {
      case 0 :
        return NO_SENSITIVITY;
      case 1 :
        return PERSONAL;
      case 2 :
        return PRIVATE;
      case 3 :
        return CONFIDENTIAL;
      default :
        throw new RuntimeException("Unknown sensitivity code");
    }
  }
  
  public static String getKey(SensitivityEnum se) {
    switch (se.toInt()) {
      case 0 :
        return "event.nosensitivity";
      case 1 :
        return "event.personal";
      case 2 :
        return "event.private";
      case 3 :
        return "event.confidential";
      default :
        return "";
    }
     
  }
}
