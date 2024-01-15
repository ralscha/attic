package ch.ess.state;

import java.util.*;

import org.apache.commons.lang.builder.*;

import ch.ess.state.digester.*;

import bsh.*;

public class StateActionBSH extends StateAction {
    
  private String enterActionCode;
  private String actionCode;
  private String leaveActionCode;


  public StateActionBSH() {
    super(null);
    
    enterActionCode = null;
    actionCode = null;
    leaveActionCode = null;
  }
  

  public StateActionBSH(String name, String enterAction, String action, String leaveAction, Set permissions) {
    super(permissions);
    
    this.enterActionCode = enterAction;
    this.actionCode = action;
    this.leaveActionCode = leaveAction;
    
  }
    

  private void runCode(State state, Map context, String code) throws StateMachineException {
    if (code != null) {
      
      try {
        Interpreter i = new Interpreter();
        
        i.set("state", state);      
        i.set("context", context);
      
        i.eval(code);
      } catch (EvalError ee) {
        throw new StateMachineException(ee);
      }      
    }
  }

  public void action(State state, Map context) throws StateMachineException {
    runCode(state, context, actionCode);
  }

  public void enterAction(State state, Map context) throws StateMachineException {
    runCode(state, context, enterActionCode);
  }

  public void leaveAction(State state, Map context) throws StateMachineException {
    runCode(state, context, leaveActionCode);
  }


   
  public String getActionCode() {
    return actionCode;
  }

  public String getEnterActionCode() {
    return enterActionCode;
  }

  public String getLeaveActionCode() {
    return leaveActionCode;
  }

  public void setActionCode(String action) {
    this.actionCode = blank2null(action);
  }

  public void setEnterActionCode(String enterAction) {
    this.enterActionCode = blank2null(enterAction);
  }

  public void setLeaveActionCode(String leaveAction) {
    this.leaveActionCode = blank2null(leaveAction);
  }


  public void copyFrom(StateAction sa) {
    if (sa instanceof StateActionBSH) {
      StateActionBSH parent = (StateActionBSH)sa;
      
      if (getActionCode() == null) {
        setActionCode(parent.getActionCode());
      }
      
      if (getEnterActionCode() == null) {
        setEnterActionCode(parent.getEnterActionCode());
      }
      
      if (getLeaveActionCode() == null) {
        setLeaveActionCode(parent.getLeaveActionCode());
      }
      
      if ((getPermissions() == null) || (getPermissions().isEmpty())) {
        Iterator it = parent.getPermissions().iterator();
        while (it.hasNext()) {
          String element = (String)it.next();
          addPermission(element);
        }
      }
      
    }
  }

  private String blank2null(String str) {
    if (str != null) {
      if ("".equals(str.trim())) {
        return null;
      }      
    }
    
    return str;
  }


  public void addDigesterPermission(DigesterPermission perm) {
    
    if (perm.getPermissions() != null) {
      String role = perm.getRole();
      if (role == null) {
       role = ALL;
      }
    
      for (Iterator it = perm.getPermissions().iterator(); it.hasNext();) {
        String element = (String)it.next();
        addPermission(element, role);
      }    
    }
  }

  public String toString() {        
    return "name="+getName() +"\n" + ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }


}
