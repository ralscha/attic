package ch.ess.test.state;

import ch.ess.state.*;

public class StateTest {

  public static void main(String[] args) {
    
    StateMachine sm = StateMachine.createStateMachine();
    
    //configuration
    StateActionBSH startAction = new StateActionBSH();
    startAction.addPermission("bedarf.ansehen");
    
    String actionCode = "System.out.println(\"action: \" + context.get(\"testObject\"));";
    startAction.setActionCode(actionCode);
        
    State start = new State("Start", null, new Integer(1), null, startAction);    
    sm.addState(start);
    State second = new State("Second", null, new Integer(2), null, startAction);
    sm.addState(second);
    State third = new State("Third", null, new Integer(3), null, startAction);
    sm.addState(third);
    
      
    Event e = new Event("goTo");                     
    e.addTransition(start, second, new StringGuard("bedarf.ansehen"));
    e.addTransition(second, third, new StringGuard("bedarf.ansehen"));
    
    sm.addEvent(e);
    
    
    
    //go
    Object testObj = new Object();
    
    StateTransit transit = new StateTransit(start); 
    
    System.out.println("has : " + sm.hasActiveStateEvent(transit, "goTo", new StringGuard("bedarf.ansehen")));
     
    try {
      sm.doEvent(transit, "goTo");
      sm.doEvent(transit, "goTo");
      
      //nochmals
      transit = new StateTransit(start); 
      transit.getContext().put("testObject", testObj);
      sm.doEvent(transit, "goTo", "bedarf.ansehen");   
      sm.doEvent(transit, "goTo", "bedarf.ansehen"); 
    } catch (StateMachineException ex) {
      
      ex.printStackTrace();
    }
    //StateMachine.doEvent(transit, context, "goTo", "bedarf.ansehen"); 
    
    
  }
}
