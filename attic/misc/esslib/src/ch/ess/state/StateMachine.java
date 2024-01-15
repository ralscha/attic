package ch.ess.state;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.commons.collections.*;
import org.apache.commons.digester.*;
import org.apache.commons.lang.builder.*;
import org.apache.commons.validator.*;

import ch.ess.state.digester.*;
import ch.ess.state.digester.RuleSet;

/**
 * @author rh
 *
 */

public class StateMachine {

  private Map eventByName = new HashMap();
  private Map stateActionByName = new HashMap();
  private Map stateByNumber = new HashMap();
  private Map stateByName = new HashMap();
  private MultiMap stateByKey = new MultiHashMap();

  private static Digester createDigester() {
    Digester digester = new Digester();
    
    URL dtdUrl = StateMachine.class.getResource("/ch/ess/state/digester/statemachine.dtd");
    digester.register("-//ESS Development AG//DTD StateMachine 1.0//EN", dtdUrl.toString());
    
    digester.setValidating(true);
    
    digester.addRuleSet(new RuleSet());
    return digester;
  }

  public static StateMachine createStateMachine(String path) throws StateMachineException {
    InputStream is = StateMachine.class.getResourceAsStream(path);  
    try {
      try {    
        return createStateMachine(is);
      } finally {
        if (is != null) {
          is.close();
        }
      }  
    } catch (Exception ex) {
      throw new StateMachineException(ex);
    }    
    
  }

  public static StateMachine createStateMachine(InputStream is) throws StateMachineException {
    try {
      return (StateMachine) createDigester().parse(is);
    } catch (Exception e) {
      throw new StateMachineException(e);
    }

  }
  
  public static StateMachine createStateMachine(Reader reader) throws StateMachineException {
    try {
      return (StateMachine) createDigester().parse(reader);
    } catch (Exception e) {
      throw new StateMachineException(e);
    }  
  }

  public static StateMachine createStateMachine() {
    return new StateMachine();
  }

  //State  
  public void addState(State state) {
    
    StateAction sa = state.getStateAction();
    if (sa != null) {
      String base = sa.getBase();
      if (base != null) {
        StateAction baseAction = lookupStateAction(base);
        if (baseAction != null) {
          sa.copyFrom(baseAction);
        } else {
          throw new IllegalArgumentException("stateAction base: " + base + " not found");
        }        
      }
    }

    stateByNumber.put(state.getNumber(), state);
    stateByName.put(state.getName(), state);
    if (state.getKey() != null) {
      stateByKey.put(state.getKey(), state);
    }
  }

  public State lookupState(Integer number) {
    return (State) stateByNumber.get(number);
  }

  public State lookupState(String name) {
    return (State) stateByName.get(name);
  }
  
  public MultiMap getStateKeyMap() {
    return stateByKey;
  }

  //do not call this method
  //for digester only
  public void addDigesterStateAction(StateAction action) {
    stateActionByName.put(action.getName(), action);
  }

  //for digester only
  private StateAction lookupStateAction(String name) {
    return (StateAction) stateActionByName.get(name);
  }

  //Events
  public void addEvent(Event event) {    
    eventByName.put(event.getName(), event);
  }

  public Event lookupEvent(String name) {
    return (Event) eventByName.get(name);
  }

  //do not call this method  
  //for digester only
  public void addDigesterEvent(DigesterEvent dEvent) {
    Event newEvent = new Event(dEvent.getName());

    int size = dEvent.getTransitionList().size();
    for (int i = 0; i < size; i++) {
      DigesterEvent.Transition tran = (DigesterEvent.Transition)dEvent.getTransitionList().get(i);

          
      State from = null;      
      if (GenericValidator.isInt(tran.getFrom())) {
        from = lookupState(new Integer(tran.getFrom()));
      } else {
        from = lookupState(tran.getFrom());
      }
      
      State to = null;      
      if (GenericValidator.isInt(tran.getTo())) {
        to = lookupState(new Integer(tran.getTo()));
      } else {
        to = lookupState(tran.getTo());
      }
      
      
      String guard = tran.getGuard();

      if ((from != null) && (to != null)) {
        if (guard != null) {
          newEvent.addTransition(from, to, new StringGuard(guard));
        } else {
          newEvent.addTransition(from, to, null);
        }
      } else {
        String notFound = null;
        if (from == null) {
          notFound = tran.getFrom();
        } else if (to == null) {
          notFound = tran.getTo();
        }

        throw new IllegalArgumentException("stateName: " + notFound + " not found");

      }
    }
    addEvent(newEvent);
    
    

  }

  public boolean hasActiveStateEvent(StateTransit transit, String eventName, Guard guard) {
    State activeState = transit.getActiveState();
    return (activeState.hasEvent(eventName, guard));
  }

  public void doEvent(StateTransit transit, String eventName, Object testObj) throws StateMachineException {
    Event event = lookupEvent(eventName);
    if (event != null) {
      event.event(transit, testObj);
    } else {
      throw new StateMachineException("event: " + eventName + " not found");
    }

  }

  public void doEvent(StateTransit transit, String eventName) throws StateMachineException {
    doEvent(transit, eventName, null);

  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
