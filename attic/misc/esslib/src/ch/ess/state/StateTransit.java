package ch.ess.state;

import java.util.*;

public class StateTransit {

  private State activeState;
  private State nextState;
  private Map context;

  public StateTransit(State currentState) {
    activeState = currentState;
    context = new HashMap();
  }
  
  
  public Map getContext() {
    return context;
  }

  public void gotoState(State toState) throws StateMachineException {
    if (toState != null) {
      leaveFor(toState);
      enter();
    }
  }
  
  

  public void enterInitial(State state) throws StateMachineException {
    if (state == null)
      throw new IllegalArgumentException("initialState must not be null");
    if (activeState == null && nextState == null) {     
      nextState = state;
      enter();
    } else {
      hurl();
    }
  }

  public void enter() throws StateMachineException {
    if (activeState == null && nextState != null) {
      activeState = nextState;
      activeState = nextState.enter(context);
      nextState = null;
      
      if (activeState == null) {
        throw new RuntimeException("program error");
      }
      
      activeState.action(context);
    } else {
      hurl();
    }
  }

  public void leaveFor(State state) throws StateMachineException {
    if (activeState != null && nextState == null) {
      nextState = activeState;
      activeState.leaveFor(state, context);
      activeState = null;
      nextState = state;
    } else {
      hurl();
    }
  }

  public void leaveFinal() throws StateMachineException {
    leaveFor(null);
  }

  private void hurl() {
    String s = super.toString();
    s = s + "{activeState=" + activeState + ", nextState=" + nextState + "}";
    throw new IllegalStateException("Invalid State Transition: " + s);
  }

  
  public State getActiveState() {
    return activeState;
  }

}
