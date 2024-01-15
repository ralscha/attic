package ch.ess.state;

import java.util.*;

public abstract class StateAction {

  protected final static String ALL = "all";
 
  private String name;
  private String base;
  private Map permissions;

  public StateAction(Set permissions) {
    this.name = null;
    
    if (permissions != null) {      
      this.permissions = new HashMap();
      this.permissions.put(ALL, permissions);
    }
        
  }


  public abstract void action(State state, Map context) throws StateMachineException;

  public abstract void enterAction(State state, Map context) throws StateMachineException;

  public abstract void leaveAction(State state, Map context) throws StateMachineException;


  public boolean hasPermission(String permission) {   
    Set perm = (Set)permissions.get(ALL); 
    return ((perm != null) && (perm.contains(permission)));
  }
  
  public void addPermission(String permission) {
    addPermission(permission, ALL);
  }
  
  public void addPermission(String permission, String role) {
    if (permissions == null) {
      permissions = new HashMap();      
    }

    
    if (role == null) {
      role = ALL;
    }
    
    Set perm = (Set)permissions.get(role);
    if (perm == null) {
      perm = new HashSet();
      permissions.put(role, perm);
    } 
    perm.add(permission);
    
    if (!role.equals(ALL)) {
      perm = (Set)permissions.get(ALL);
      if (perm == null) {
        perm = new HashSet();
        permissions.put(ALL, perm);
      } 
      perm.add(permission);      
    }
  }  

  public void copyFrom(StateAction sa) {    
  }

  public Set getPermissions() {
    return getPermissions(ALL);
  }

  public Set getPermissions(String role) {
    if (permissions != null) {
      return (Set)permissions.get(role);
    }
    return null;    
  }
  
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

}
