package ch.ess.state;


public interface Guard {

  boolean test(Object testObj);


  public boolean equals(Object obj);
  public int hashCode();

  
}
