package ch.ess.state;

import org.apache.commons.lang.builder.*;

public class StringGuard implements Guard {

  private String guardString;

  public StringGuard(String guardString) {
    this.guardString = guardString;
  }

  public boolean test(Object testObj) {
    if (testObj != null) {
      return testObj.equals(guardString);
    }    
    
    return false;

  }

  public boolean equals(Object obj) {
    try {
      return (guardString.equals(((StringGuard)obj).guardString));
    } catch (ClassCastException e) { 
      return false;
    }
  }

  public int hashCode() {
    return guardString.hashCode();
    
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
