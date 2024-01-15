package ch.ess.sandbox;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.EndTask;

@Name("ship")
public class ShipAction {

  @In
  String email;
  
  
  @BeginTask
  @EndTask
  public String ship() {
    System.out.println("SHIPT TO : " + email);
    return null;
  }
  
  
}
