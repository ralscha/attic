import org.jmonde.state.State;
import org.jmonde.state.StateTransit;


public class MonTest2 {

  StateTransit transit = new StateTransit();
    
  State one = new State(new State1());
  State two = new State(new State2());
  State three = new State(new State3());

  public void goTo2() {
    if (one.isActive()) {
      transit.leaveFor(two);
      transit.enter();
      
    } 
  }

  public void go() {
    
    transit.enterInitial(one);
    
    goTo2();
    
    
    
    System.out.println(one.isActive());
    System.out.println(two.isActive());
    
    
  }

  public static void main(String[] args) {


    new MonTest2().go();
    
  
  }

}
