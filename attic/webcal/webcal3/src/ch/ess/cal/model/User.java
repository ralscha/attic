package ch.ess.cal.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.AssociationTable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import ch.ess.cal.Util;

/** A business entity class representing an User
 * 
 * @author sr
 * @version $Revision: 1.12 $ $Date: 2005/04/04 11:31:11 $
 */

@Entity
public class User extends BaseObject {

  private String firstName;
  private String name;
  private String shortName;
  private String userName;
  private String passwordHash;
  private String loginToken;
  private Date loginTokenTime;
  private Set<Email> emails = new HashSet<Email>();

  private Locale locale;
  private TimeZone timeZone;

  private Set<UserUserGroup> userUserGroups = new HashSet<UserUserGroup>();
  private Set<UserConfiguration> userConfiguration = new HashSet<UserConfiguration>();
  private Set<Group> groups = new HashSet<Group>();
  private Set<Group> accessGroups = new HashSet<Group>();

  private Set<ResourceGroup> resourceGroups = new HashSet<ResourceGroup>();
  private Set<Event> events = new HashSet<Event>();

  @Column(length = 100)
  public String getFirstName() {
    return firstName;
  }

  @Column(nullable = false, length = 10)
  public Locale getLocale() {
    return locale;
  }

  @Column(nullable = false, length = 100)
  public String getName() {
    return name;
  }

  @Column(length = 40)
  public String getPasswordHash() {
    return passwordHash;
  }

  @Column(nullable = false, length = 100, unique = true)
  public String getUserName() {
    return userName;
  }

  @Column(length = 40)
  public String getLoginToken() {
    return loginToken;
  }

  @Type(type = "timestamp")
  public Date getLoginTokenTime() {
    return Util.clone(loginTokenTime);
  }

  public void setLoginToken(final String string) {
    loginToken = string;
  }

  public void setLoginTokenTime(final Date date) {
    loginTokenTime = Util.clone(date);
  }

  @Column(length = 30)
  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Column(nullable = false)
  public TimeZone getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }

  public void setFirstName(final String string) {
    firstName = string;
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  public void setName(final String string) {
    name = string;
  }

  public void setPasswordHash(final String string) {
    passwordHash = string;
  }

  public void setUserName(final String string) {
    userName = string;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserConfiguration> getUserConfiguration() {
    return userConfiguration;
  }

  public void setUserConfiguration(Set<UserConfiguration> userConfiguration) {
    this.userConfiguration = userConfiguration;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserUserGroup> getUserUserGroups() {
    return userUserGroups;
  }

  public void setUserUserGroups(Set<UserUserGroup> userUserGroups) {
    this.userUserGroups = userUserGroups;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
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

  @ManyToMany(cascade = CascadeType.ALL)
  @AssociationTable(table = @Table(name = "AccessGroupUsers"), joinColumns = @JoinColumn(name = "userId", nullable = false), inverseJoinColumns = @JoinColumn(name = "groupId", nullable = false))
  public Set<Group> getAccessGroups() {
    return accessGroups;
  }

  public void setAccessGroups(Set<Group> accessGroups) {
    this.accessGroups = accessGroups;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  @AssociationTable(table = @Table(name = "UserResourceGroups"), joinColumns = @JoinColumn(name = "userId", nullable = false), inverseJoinColumns = @JoinColumn(name = "resourceGroupId", nullable = false))
  public Set<ResourceGroup> getResourceGroups() {
    return resourceGroups;
  }

  public void setResourceGroups(Set<ResourceGroup> resourceGroups) {
    this.resourceGroups = resourceGroups;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  @AssociationTable(table = @Table(name = "GroupUsers"), joinColumns = @JoinColumn(name = "userId", nullable = false), inverseJoinColumns = @JoinColumn(name = "groupId", nullable = false))
  public Set<Group> getGroups() {
    return groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
  public Set<Event> getEvents() {
    return events;
  }

  public void setEvents(Set<Event> events) {
    this.events = events;
  }

}
