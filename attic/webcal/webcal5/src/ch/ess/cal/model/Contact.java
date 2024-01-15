package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;

@Entity
public class Contact extends BaseObject {

  private String category;
  private Group group;

  private Map<String, ContactAttribute> contactAttributes;
  private Set<User> users = new HashSet<User>();

  @ManyToOne
  @JoinColumn(name = "groupId")
  public Group getGroup() {
    return this.group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  @Column(nullable = false, length = 1)
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  @MapKey(name="field")
  public Map<String, ContactAttribute> getContactAttributes() {
    return contactAttributes;
  }

  public void setContactAttributes(Map<String, ContactAttribute> contactAttributes) {
    this.contactAttributes = contactAttributes;
  }

  @ManyToMany
  @JoinTable(name = "UserContacts", joinColumns = @JoinColumn(name = "contactId", nullable = false), inverseJoinColumns = @JoinColumn(name = "userId", nullable = false))
  public Set<User> getUsers() {
    return this.users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

}
