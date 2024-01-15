package ch.ess.state;
import java.util.*;

import org.apache.commons.lang.builder.*;

public class State {



  private StateAction stateAction;
  private String name;
  private String key;
  private String location;
  private Integer number;


  private State defaultChild;
  private State historyChild;
  private State parent;

  private Map eventPermission = new HashMap();
    
  public State(String name, Integer number) {
    this(name, null, number, null, (StateAction)null);
  }

  public State(String name, String key, Integer number, String location) {
    this(name, key, number, location, (StateAction)null);
  }
  
 
  public State(String name, String key, Integer number, String location, StateAction stateAction) {
    defaultChild = null;
    historyChild = null;
    parent = null;
    this.stateAction = stateAction;
    
    this.name = name;
    this.number = number;
    this.key = key;
    this.location = location;
    
  }



  public void setStateAction(StateAction stateAction) {
    this.stateAction = stateAction;
  }
  

  
  

  void action(Map context) throws StateMachineException {
    if (stateAction != null)
      stateAction.action(this, context);
  }

  State enter(Map context) throws StateMachineException {
    enterParents(context);
    enterSelf(context);
    return enterChildren(context);
  }

  private void enterAction(Map context) throws StateMachineException {
    if (stateAction != null)
      stateAction.enterAction(this, context);
  }

  private State enterChildren(Map context) throws StateMachineException {
    State state = this;
    for (State state1 = state.getAutoChild(); state1 != null; state1 = state.getAutoChild()) {
      state1.enterSelf(context);
      state = state1;
    }

    return state;
  }

  private void enterParents(Map context) throws StateMachineException {
    State state = parent;
    if (state != null) {
      state.enterParents(context);
      state.enterSelf(context);
    }
  }

  private void enterSelf(Map context) throws StateMachineException {

    if (parent != null && parent.historyChild != null)
      parent.historyChild = this;
    enterAction(context);

  }

  private State getAutoChild() {
    return defaultChild == null ? historyChild : defaultChild;
  }

  public State getParent() {
    return parent;
  }

  private boolean isAncestorOf(State state) {
    boolean flag = false;
    if (state != null)
      while ((state = state.parent) != null)
        if (equals(state)) {
          flag = true;
          break;
        }
    return flag;
  }

  private void leaveAction(Map context) throws StateMachineException {
    if (stateAction != null)
      stateAction.leaveAction(this, context);
  }

  void leaveFor(State state, Map context) throws StateMachineException {
    if (!isAncestorOf(state)) {
      leaveSelf(context);
      State state1 = parent;
      if (state1 != null && !state1.equals(state))
        state1.leaveFor(state, context);
    }
  }

  private void leaveSelf(Map context) throws StateMachineException {

    leaveAction(context);
  }

  public void makeDefaultChild() {
    if (parent != null)
      parent.defaultChild = this;
  }

  public void makeHistoryChild() {
    if (parent != null) {
      parent.historyChild = this;
      parent.defaultChild = null;
    }
  }

  public void setParent(State state) {
    if (parent != state) {
      if (parent != null) {
        if (this == parent.defaultChild)
          parent.defaultChild = null;
        if (this == parent.historyChild)
          parent.historyChild = null;
      }
      parent = state;
    }
  }

  public String getName() {
    return name;
  }

  public Integer getNumber() {
    return number;
  }
  
  public String getKey() {
    return key;
  }

  
  
  public void addEvent(Event event, Guard guard) {
    Set eventSet = (Set) eventPermission.get(guard);

    if (eventSet == null) {
      eventSet = new HashSet();
      eventPermission.put(guard, eventSet);
    }

    eventSet.add(event.getName());

  }

  public boolean hasEvent(String name, Guard guard) {
    Set eventSet = (Set) eventPermission.get(guard);

    if (eventSet != null) {
      return (eventSet.contains(name));
    }

    return false;
  }
  
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public boolean equals(Object obj) {

    if (obj instanceof State) {
      return ((State) obj).number == number;
    }

    return false;
  }

  public int hashCode() {    
    return number.intValue();
  }


  public StateAction getStateAction() {
    return stateAction;
  }

  public String getLocation() {
    return location;
  }

}
