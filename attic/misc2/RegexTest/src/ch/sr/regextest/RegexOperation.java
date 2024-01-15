package ch.sr.regextest;


import ch.sr.benchmark.*;

public abstract class RegexOperation implements Operation {

  public void warmUp() {
    System.out.println(isValidEmail("ralphschaer@yahoo.com"));
    System.out.println(isValidEmail("notcorrect"));
  }

  public void execute() {
    isValidEmail("ralphschaer@yahoo.com");
    isValidEmail("notcorrect");      
  }

  public int getIterationCount() {
    return 10000;
  }
  
  protected abstract boolean isValidEmail(String email);  

}
