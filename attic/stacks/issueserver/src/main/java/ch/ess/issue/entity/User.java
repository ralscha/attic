package ch.ess.issue.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "issueUser")
public class User extends AbstractEntity {

  private String firstName;
  private String lastName;
  
  @Column(length=50)
  private String password;
  
  @Column(length=100)
  private String username;
  
  private String role;
  private String email;
  private boolean enabled;
  
  @Column(length=50)
  private String rememberMeToken;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRememberMeToken() {
    return rememberMeToken;
  }

  public void setRememberMeToken(String rememberMeToken) {
    this.rememberMeToken = rememberMeToken;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

}
