package ch.ess.test.state;

import ch.ess.state.*;

public class StateXMLTest {

  public static void main(String[] args) {
    
    try {

      StateMachine sm = StateMachine.createStateMachine("/ch/ess/state/test/statemachine.xml");
      
      
      //System.out.println(sm);
      
      /*
      State s = State.lookupState("one");
      System.out.println(s);
      
      s = State.lookupState("two");
      System.out.println(s);
      */
      /*
      StateAction defaultSA = StateAction.lookupStateAction("default");
      StateAction ownSA = StateAction.lookupStateAction("myOwnAction");
      System.out.println(defaultSA);
      System.out.println(ownSA);

      System.out.println(defaultSA.hasPermission("bedarf.ansehen"));
      System.out.println(defaultSA.hasPermission("bedarf.bearbeiten"));

      System.out.println(ownSA.hasPermission("bedarf.ansehen"));
      System.out.println(ownSA.hasPermission("bedarf.bearbeiten"));
      */
      
      
      //go
      Object testObj = new Object();
    /*
      StateTransit transit = new StateTransit(sm.lookupState("one"));
      transit.getContext().put("testObj", testObj);  
      sm.doEvent(transit, "goTo");
      //StateMachine.doEvent(transit, context, "goTo");
    */
      
      //nochmals
      StateTransit transit = new StateTransit(sm.lookupState("one")); 
      transit.getContext().put("testObj", testObj);  
      sm.doEvent(transit, "goTo", "bedarf.ansehen");   
      sm.doEvent(transit, "goTo", "bedarf.ansehen"); 

      //StateMachine.doEvent(transit, context, "goTo", "bedarf.ansehen");       
      
      
    } catch (StateMachineException e) {
      
      e.printStackTrace();
    } 
    
  }
}
