package ch.ess.tt.model;

public class Principal {

  private int id;
  private String name;
  private String firstName;
  private String locale;
  private boolean enabled;
  private String userName;
  private String email;
  private String oe;
  private String passwordHash;
  private boolean admin;
  private boolean roleAuthor;
  private boolean roleTester;
  private boolean roleDeveloper;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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
