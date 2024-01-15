package ch.sr.regextest;


import java.util.regex.*;

public class Jdk14Operation extends RegexOperation {

  private Pattern pattern;

  public Jdk14Operation() {
    pattern = Pattern.compile(Constants.EMAIL_REGEX);
      
  }
  
  protected boolean isValidEmail(String email) {
    /*
    if (email.matches(Constants.EMAIL_REGEX)) {
      return true;
    } else {
      return false;
    }
    */
    
    Matcher matcher = pattern.matcher(email);
   
    if (matcher.matches()) {
      return true;
    } else {
      return false;
    }
    
  }  

  public String toString() {
    return "Jdk 1.4 Regex";
  }

}
