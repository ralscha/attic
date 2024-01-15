package ch.ess.base.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import ch.ess.base.Util;
import ch.ess.cal.model.BaseEvent;
import ch.ess.cal.model.Contact;
import ch.ess.cal.model.DirectoryPermission;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.FilePermission;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.ResourceGroup;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.UserTimeCustomer;


@Entity
public class User extends BaseObject {

  private String name;
  private String firstName;
  private String userName;
  private String shortName;
  private String passwordHash;
  private String loginToken;
  private Date loginTokenTime;
  private boolean enabled;
  private Set<Email> emails = new HashSet<Email>();

  private Locale locale;
  private TimeZone timeZone;

  private Set<UserUserGroup> userUserGroups = new HashSet<UserUserGroup>();
  private Set<UserTimeCustomer> userTimeCustomers = new HashSet<UserTimeCustomer>();
  private Set<UserConfiguration> userConfiguration = new HashSet<UserConfiguration>();

  
  private Set<Group> groups = new HashSet<Group>();
  private Set<Group> accessGroups = new HashSet<Group>();
  private Set<ResourceGroup> resourceGroups = new HashSet<ResourceGroup>();  
  private Set<BaseEvent> events = new HashSet<BaseEvent>();
  private Set<Contact> contacts = new HashSet<Contact>();
  
  private Set<FilePermission> filePermission = new HashSet<FilePermission>();
  private Set<DirectoryPermission> directoryPermission = new HashSet<DirectoryPermission>();
  
  private Set<Time> times = new HashSet<Time>();
  
  @Column(nullable = false, length = 10)
  public Locale getLocale() {
    return locale;
  }

  @Column(nullable = false, length = 100)
  public String getName() {
    return name;
  }

  @Column(nullable = false, length = 100)  
  public String getFirstName() {
    return firstName;
  }
  
  @Column(length = 40)
  public String getPasswordHash() {
    return passwordHash;
  }

  @Column(nullable = false, length = 100, unique = true)
  public String getUserName() {
    return userName;
  }

  
  @Column(length = 30)
  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
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

  public void setFirstName(String firstName) {
    this.firstName = firstName;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Column(nullable = false)
  public TimeZone getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }
  
  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user")
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
  public Set<UserTimeCustomer> getUserTimeCustomers() {
    return userTimeCustomers;
  }

  public void setUserTimeCustomers(Set<UserTimeCustomer> userTimeCustomers) {
    this.userTimeCustomers = userTimeCustomers;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "AccessGroupUsers", 
             joinColumns = @JoinColumn(name = "userId", nullable = false), 
             inverseJoinColumns = @JoinColumn(name = "groupId", nullable = false))
  public Set<Group> getAccessGroups() {
    return accessGroups;
  }

  public void setAccessGroups(Set<Group> accessGroups) {
    this.accessGroups = accessGroups;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "UserResourceGroups", 
             joinColumns = @JoinColumn(name = "userId", nullable = false), 
             inverseJoinColumns = @JoinColumn(name = "resourceGroupId", nullable = false))
  public Set<ResourceGroup> getResourceGroups() {
    return resourceGroups;
  }

  public void setResourceGroups(Set<ResourceGroup> resourceGroups) {
    this.resourceGroups = resourceGroups;
  }

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "GroupUsers", 
             joinColumns = @JoinColumn(name = "userId", nullable = false), 
             inverseJoinColumns = @JoinColumn(name = "groupId", nullable = false))
  public Set<Group> getGroups() {
    return groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

  
  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
  public Set<BaseEvent> getEvents() {
    return events;
  }

  public void setEvents(Set<BaseEvent> events) {
    this.events = events;
  }

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
  public Set<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(Set<Contact> contacts) {
    this.contacts = contacts;
  }  

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)    
  public Set<DirectoryPermission> getDirectoryPermission() {
    return directoryPermission;
  }

  public void setDirectoryPermission(Set<DirectoryPermission> directoryPermission) {
    this.directoryPermission = directoryPermission;
  }
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)  
  public Set<FilePermission> getFilePermission() {
    return filePermission;
  }

  public void setFilePermission(Set<FilePermission> filePermission) {
    this.filePermission = filePermission;
  } 

 
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Time> getTimes() {
    return times;
  }

  public void setTimes(Set<Time> times) {
    this.times = times;
  }
}
