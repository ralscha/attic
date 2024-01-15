//$Id: User.java,v 1.8 2007/06/27 00:06:49 gavin Exp $
package ch.ess.booking.entity;

import static org.jboss.seam.ScopeType.SESSION;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@SuppressWarnings("all")
@Entity
@Name("user")
@Scope(SESSION)
@Table(name = "Customer")
public class User implements Serializable {

  @Id
  @Length(min = 4, max = 15)
  @Pattern(regex = "^\\w*$", message = "not a valid username")
  private String username;

  @NotNull
  @Length(min = 4, max = 15)
  private String password;

  @NotNull
  @Length(max = 100)
  private String name;

  public User(String name, String password, String username) {
    this.name = name;
    this.password = password;
    this.username = username;
  }

  public User() {
    //default constructor  
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  @Override
  public String toString() {
    return "User(" + username + ")";
  }
}
