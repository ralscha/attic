package ch.sr.regextest;

import jregex.*;

public class JRegexOperation extends RegexOperation {

  Matcher m;

  public JRegexOperation() {
    m=new Pattern(Constants.EMAIL_REGEX).matcher();
  }


  public String toString() {
    return "JRegex 1.2";
  }

  protected boolean isValidEmail(String email) {

    m.setTarget(email);
    if (m.matches()) {
      return true;
    } else {
      return false;
    }
  }

}