package ch.ess.cal.service;

public class StringLength {
  private String str;
  private int len;

  StringLength(String str, int len) {
    this.str = str;
    this.len = len;
  }
  
  public int getLength() {
    return len;
  }

  public void setLength(int length) {
    this.len = length;
  }

  public String getString() {
    return str;
  }

  public void setString(String string) {
    this.str = string;
  }

}
