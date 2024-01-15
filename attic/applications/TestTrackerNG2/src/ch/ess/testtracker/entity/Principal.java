package ch.ess.testtracker.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
public class Principal extends AbstractEntity {

  @Length(max = 100)
  private String name;
  
  @Length(max = 100)
  private String firstName;
  
  @Length(max = 10)
  private String locale;
  
  private boolean enabled;
  
  @NotNull
  @Length(max = 100)
  private String userName;
  
  @NotNull
  @Length(max = 255)
  private String email;
  
  @Length(max = 100)
  private String oe;
  
  @Length(max = 40)
  private String passwordHash;
  
  @Transient
  private boolean admin;
  
  @Transient
  private boolean roleAuthor;
  
  @Transient
  private boolean roleTester;
  
  @Transient
  private boolean roleDeveloper;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getOe() {
    return oe;
  }

  public void setOe(String oe) {
    this.oe = oe;
  }

  
  public String getPasswordHash() {
    return passwordHash;
  }

  
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  
  public boolean isAdmin() {
    return admin;
  }

  
  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  
  public boolean isRoleAuthor() {
    return roleAuthor;
  }

  
  public void setRoleAuthor(boolean roleAuthor) {
    this.roleAuthor = roleAuthor;
  }

  
  public boolean isRoleTester() {
    return roleTester;
  }

  
  public void setRoleTester(boolean roleTester) {
    this.roleTester = roleTester;
  }

  
  public boolean isRoleDeveloper() {
    return roleDeveloper;
  }

  
  public void setRoleDeveloper(boolean roleDeveloper) {
    this.roleDeveloper = roleDeveloper;
  }

  


}
