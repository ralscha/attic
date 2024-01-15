package ch.ess.state.digester;

import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 * @author sr
 * 20.02.2003
 */
public class DigesterPermission {
 
  private Set permissions;
  private String role;  
  
  public DigesterPermission() {    
    this.permissions = new HashSet();
    this.role = null;
  }   
    
  public void addPermission(String name) {
    permissions.add(name);
  }
  
  public Set getPermissions() {
    return permissions;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String string) {
    role = string;
  }
  
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }  

}
