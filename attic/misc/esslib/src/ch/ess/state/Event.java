package ch.ess.state;

import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 * @author rh
 * 20.02.2003
 */
public class Event {
 
  private Map transition;
  private String name;  
  
  
  public Event(String name) {    
    transition = new HashMap();
    transition = new HashMap();
    this.name = name;
  }
    
  public String getName() {
    return name;
  }
  
    
  public void addTransition(State from, State to, Guard guard) {
    transition.put(from, new Transition(from, to, guard));
    
    from.addEvent(this, guard);    
  }
  
  
  public void event(StateTransit stateTransit) throws StateMachineException {
    event(stateTransit, null);
  }

  public void event(StateTransit stateTransit, Object testObj) throws StateMachineException {        
    
    Transition trans = (Transition)transition.get(stateTransit.getActiveState());
    
    if (trans != null) {
      
      if (testObj != null) {
        Guard guard = trans.getGuard();
        if (guard != null) {
          if (guard.test(testObj)) {
            stateTransit.gotoState(trans.getDestination());
          }
        } else {      
          stateTransit.gotoState(trans.getDestination());
        }
      } else {
        stateTransit.gotoState(trans.getDestination());
      }
      
    } else {    
      throw new StateMachineException("Event: " + name + " activeState: " 
                                      + stateTransit.getActiveState().getName());
    }
  }



  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
      



}
