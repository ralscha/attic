package ch.ess.ex4u.shared;

import java.io.Serializable;
import java.util.HashSet;

public class PrincipalDetail implements Serializable {

  private Long id;
  private String name;
  private String fullName;
  private HashSet<String> roles;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public HashSet<String> getRoles() {
    return roles;
  }

  public void setRoles(HashSet<String> roles) {
    this.roles = roles;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
