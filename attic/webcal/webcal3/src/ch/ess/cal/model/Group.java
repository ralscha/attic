package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AssociationTable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/** A business entity class representing a Group
 * 
 * @author sr
 * @version $Revision: 1.9 $ $Date: 2005/04/04 11:31:12 $ 
 */
@Entity
public class Group extends TranslateObject {

  private Set<Email> emails = new HashSet<Email>();
  private Set<GroupConfiguration> groupConfiguration = new HashSet<GroupConfiguration>();

  private Set<User> users = new HashSet<User>();
  private Set<User> accessUsers = new HashSet<User>();
  private Set<ResourceGroup> resourceGroups = new HashSet<ResourceGroup>();
  private Set<Event> events = new HashSet<Event>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
  public Set<Email> getEmails() {
    return emails;
  }

  public void setEmails(Set<Email> emails) {
    this.emails = emails;
  }

  @Transient
  public String getDefaultEmail() {
    for (Email email : emails) {
      if (email.isDefaultEmail()) {
        return email.getEmail();
      }
    }
    return null;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
  public Set<GroupConfiguration> getGroupConfiguration() {
    return groupConfiguration;
  }

  public void setGroupConfiguration(Set<GroupConfiguration> groupConfiguration) {
    this.groupConfiguration = groupConfiguration;
  }

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "groups")
  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "accessGroups")
  public Set<User> getAccessUsers() {
    return accessUsers;
  }

  public void setAccessUsers(Set<User> accessUsers) {
    this.accessUsers = accessUsers;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  @AssociationTable(table = @Table(name = "GroupResourceGroups"), joinColumns = @JoinColumn(name = "groupId", nullable = false), inverseJoinColumns = @JoinColumn(name = "resourceGroupId", nullable = false))
  public Set<ResourceGroup> getResourceGroups() {
    return this.resourceGroups;
  }

  public void setResourceGroups(Set<ResourceGroup> resourceGroups) {
    this.resourceGroups = resourceGroups;
  }

  @OneToMany(mappedBy = "group")
  public Set<Event> getEvents() {
    return this.events;
  }

  public void setEvents(Set<Event> events) {
    this.events = events;
  }

}
