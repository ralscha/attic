package @packageProject@.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Indexed
@Table(name = "`User`")
public class User extends AbstractEntity {

  @NotNull
  @Length(max = 100)
  @Column(unique = true)
  @Field(index=Index.TOKENIZED, store=Store.NO)
  private String userName;
  
  @Length(max = 200)
  @Field(index=Index.TOKENIZED, store=Store.NO)
  private String name;

  @Length(max = 200)
  @Field(index=Index.TOKENIZED, store=Store.NO)
  private String firstName;

  @Email
  @Length(max = 255)
  @NotNull
  @Field(index=Index.TOKENIZED, store=Store.NO)
  private String email;

  @Length(max = 40)
  private String passwordHash;

  @Length(max = 255)
  private String openId;

  @Length(max = 8)
  private String locale;
  
  private boolean enabled;

  @ManyToMany
  @JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
  private Set<Role> roles;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  
  public String getLocale() {
    return locale;
  }

  
  public void setLocale(String locale) {
    this.locale = locale;
  }

}
