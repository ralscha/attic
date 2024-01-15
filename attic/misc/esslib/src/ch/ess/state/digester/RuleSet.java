package ch.ess.state.digester;

import org.apache.commons.digester.*;

import ch.ess.state.*;

public class RuleSet extends RuleSetBase {

  public void addRuleInstances(Digester digester) {
    
    digester.addObjectCreate("statemachine", StateMachine.class);

    //GLOBALE ACTIONS
    digester.addObjectCreate("statemachine/actions/action", StateActionBSH.class);
    digester.addSetProperties("statemachine/actions/action", "name", "name");
    digester.addBeanPropertySetter("statemachine/actions/action/enterAction", "enterActionCode");
    digester.addBeanPropertySetter("statemachine/actions/action/mainAction", "actionCode");
    digester.addBeanPropertySetter("statemachine/actions/action/leaveAction", "leaveActionCode");


    digester.addObjectCreate("statemachine/actions/action/permissions", DigesterPermission.class); 
    digester.addSetProperties("statemachine/actions/action/permissions", "role", "role"); 
    digester.addCallMethod("statemachine/actions/action/permissions/permission", "addPermission", 1);
    digester.addCallParam("statemachine/actions/action/permissions/permission", 0, "name");

    digester.addSetNext("statemachine/actions/action/permissions", "addDigesterPermission");


    digester.addSetNext("statemachine/actions/action", "addDigesterStateAction");

    //STATES
    digester.addFactoryCreate("statemachine/states/state", StateCreationFactory.class);

    //Lokale Actions
    digester.addObjectCreate("statemachine/states/state/action", StateActionBSH.class); 
    digester.addSetProperties("statemachine/states/state/action", "base", "base");   
    digester.addBeanPropertySetter("statemachine/states/state/action/enterAction", "enterActionCode");
    digester.addBeanPropertySetter("statemachine/states/state/action/mainAction", "actionCode");
    digester.addBeanPropertySetter("statemachine/states/state/action/leaveAction", "leaveActionCode");

    digester.addObjectCreate("statemachine/states/state/action/permissions", DigesterPermission.class); 
    digester.addSetProperties("statemachine/states/state/action/permissions", "role", "role"); 
    digester.addCallMethod("statemachine/states/state/action/permissions/permission", "addPermission", 1);
    digester.addCallParam("statemachine/states/state/action/permissions/permission", 0, "name");

    digester.addSetNext("statemachine/states/state/action/permissions", "addDigesterPermission");

    digester.addSetNext("statemachine/states/state/action", "setStateAction");

    digester.addSetNext("statemachine/states/state", "addState");

    //EVENTS
    digester.addObjectCreate("statemachine/events/event", DigesterEvent.class);
    digester.addSetProperties("statemachine/events/event", "name", "name");
    digester.addCallMethod("statemachine/events/event/transition", "addTransition", 3);
    digester.addCallParam("statemachine/events/event/transition", 0, "fromState");
    digester.addCallParam("statemachine/events/event/transition", 1, "toState");
    digester.addCallParam("statemachine/events/event/transition", 2, "guard");

    digester.addSetNext("statemachine/events/event", "addDigesterEvent");


  }

}
