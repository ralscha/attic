package ch.ess.issue.service;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import ch.ess.issue.entity.Customer;

@Name("helloAction")
@Scope(ScopeType.CONVERSATION)
public class HelloAction {

  @In(required=false)
  @Out(required=false)
  private Customer customer;
    
  @Begin
  public void start(String name) {
    customer = new Customer();
    customer.setName(name);
  }
  
  @End
  public void end() {
    System.out.println(customer.getName());
  }
}