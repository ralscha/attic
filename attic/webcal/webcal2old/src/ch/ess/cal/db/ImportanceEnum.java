package ch.ess.cal.db;

import net.sf.hibernate.*;

public class ImportanceEnum implements PersistentEnum {

  private final int code;
  
  private ImportanceEnum(int code) {
    this.code = code;
  }
  
  public static final ImportanceEnum LOW = new ImportanceEnum(0);
  public static final ImportanceEnum NORMAL = new ImportanceEnum(1);
  public static final ImportanceEnum HIGH = new ImportanceEnum(2);
  
  public int toInt() {
    return code;
  }
  
  public static ImportanceEnum fromInt(int code) {
    switch (code) {
      case 0 :
        return LOW;
      case 1 :
        return NORMAL;
      case 2 :
        return HIGH;
      default :
        throw new RuntimeException("Unknown importance code");
    }
  }
}

