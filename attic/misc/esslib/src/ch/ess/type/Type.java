package ch.ess.type;

import org.apache.commons.lang.builder.*;


public abstract class Type implements Comparable {


  protected Object typ;
  protected String key;
    
  public Integer getIntegerValue() {
    if (typ instanceof Integer) {
      return (Integer)typ;
    } else {
      return new Integer(typ.toString());
    }
  }
  
  public int getIntValue() {
    return getIntegerValue().intValue();
  }
  
  public String getStringValue() {
    if (typ instanceof String) {
      return (String)typ;
    } else {
      return typ.toString();
    }
  }
  
  public String getKey() {
    return key;
  }

  public int compareTo(Object o) {    
    Type t = (Type) o;
    return new CompareToBuilder()
                 .append(typ, t.typ)
                 .toComparison();
  }
  
  public String toString() {    
    return new ToStringBuilder(this).
      append(typ).
      toString();
  }

}
