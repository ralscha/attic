package ch.sr.regextest;

import org.apache.oro.text.regex.*;

public class OroOperation extends RegexOperation {

  private Pattern pattern;
  private Perl5Compiler compiler;

  PatternMatcher matcher;

  public OroOperation() {
    compiler = new Perl5Compiler();

    try {
      pattern = compiler.compile(Constants.EMAIL_REGEX);
      matcher =  new Perl5Matcher();
    } catch (MalformedPatternException e) {
      e.printStackTrace();
    }  
  }

  
  protected boolean isValidEmail(String email) {
   
    if (matcher.contains(email, pattern)) {
      return true;
    } else {
      return false;
    }
  }  

  public String toString() {
    return "Jakarta-Oro 2.0.6";
  }

}
