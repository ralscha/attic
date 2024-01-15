package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.TranslateObject;
import ch.ess.base.model.User;



@Entity
public class ResourceGroup extends TranslateObject {

  private Set<Resource> resources = new HashSet<Resource>();
  private Set<Group> groups = new HashSet<Group>();
  private Set<User> users = new HashSet<User>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "resourceGroup")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Resource> getResources() {
    return this.resources;
  }

  public void setResources(Set<Resource> resources) {
    this.resources = resources;
  }

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "resourceGroups")
  public Set<Group> getGroups() {
    return this.groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "resourceGroups")
  public Set<User> getUsers() {
    return this.users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

}
