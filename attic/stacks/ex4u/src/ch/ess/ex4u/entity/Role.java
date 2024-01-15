package ch.ess.ex4u.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
public class Role extends AbstractEntity {

  @NotNull
  @Length(max = 50)
  private String name;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy="roles")    
  private Set<User> users;

  @Transient
  private int userId;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public int getUserId() {
    return userId;
  }
  
  public void setUserId(int userId) {
    this.userId = userId;
  }

  public Set<User> getUsers() {
    return users;
  }
  
  public void setUsers(Set<User> users) {
    this.users = users;
  }
}