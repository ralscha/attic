package ch.ess.cal.db;

import net.sf.hibernate.*;

public class RecurrenceTypeEnum implements PersistentEnum {

  private final int code;
  
  private RecurrenceTypeEnum(int code) {
    this.code = code;
  }
  
  public static final RecurrenceTypeEnum DAILY = new RecurrenceTypeEnum(0);
  public static final RecurrenceTypeEnum WEEKLY = new RecurrenceTypeEnum(1);
  public static final RecurrenceTypeEnum MONTHLY = new RecurrenceTypeEnum(2);
  public static final RecurrenceTypeEnum MONTHLY_NTH = new RecurrenceTypeEnum(3);  
  public static final RecurrenceTypeEnum YEARLY = new RecurrenceTypeEnum(4);
  public static final RecurrenceTypeEnum YEARLY_NTH = new RecurrenceTypeEnum(5);
  
  public static final RecurrenceTypeEnum DATES = new RecurrenceTypeEnum(10);
  
  public int toInt() {
    return code;
  }
  
  public static RecurrenceTypeEnum fromInt(int code) {
    switch (code) {
      case 0 :
        return DAILY;
      case 1 :
        return WEEKLY;
      case 2 :
        return MONTHLY;
      case 3 :
        return MONTHLY_NTH;
      case 4 :
        return YEARLY;
      case 5 :
        return YEARLY_NTH;
      case 10 :
        return DATES;
      default :
        throw new RuntimeException("Unknown recurrence type code");
    }
  }
}

