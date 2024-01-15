package ch.sr.regextest;

import gnu.regexp.*;

public class GnuRegexOperation extends RegexOperation {

  private RE regex;

  public GnuRegexOperation() {
    try {
      regex = new RE(Constants.EMAIL_REGEX);
    } catch (REException e) {}
  }

  protected boolean isValidEmail(String email) {
    if (regex.isMatch(email)) {
      return true;
    } else {
      return false;
    }
  }

  public String toString() {
    return "GNU Regex 1.1.4";
  }
}