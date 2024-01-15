package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/** A business entity class representing a ResourceGroup
 * 
 * @author sr
 * @version $Revision: 1.9 $ $Date: 2005/04/04 11:31:11 $ 
 */

@Entity
public class ResourceGroup extends TranslateObject {

  private Set<Resource> resources = new HashSet<Resource>();
  private Set<Group> groups = new HashSet<Group>();
  private Set<User> users = new HashSet<User>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "resourceGroup")
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
