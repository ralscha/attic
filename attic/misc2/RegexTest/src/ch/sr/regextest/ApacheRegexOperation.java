package ch.sr.regextest;

import org.apache.regexp.*;

public class ApacheRegexOperation extends RegexOperation {

  private RE regex;

  public ApacheRegexOperation() {
    try {
      regex = new RE(Constants.EMAIL_REGEX);
    } catch (RESyntaxException e) {
      e.printStackTrace();
    }      
  }
  
  protected boolean isValidEmail(String email) {

    if (regex.match(email)) {
      return true;
    } else {
      return false;
    }
  }  

  public String toString() {
    return "Apache Regex 1.2";
  }
}
