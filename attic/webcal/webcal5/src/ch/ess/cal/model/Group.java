package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.TranslateObject;
import ch.ess.base.model.User;

@Entity
public class Group extends TranslateObject {

  private Set<Email> emails = new HashSet<Email>();
  private Set<GroupConfiguration> groupConfiguration = new HashSet<GroupConfiguration>();

  private Set<User> users = new HashSet<User>();
  private Set<User> accessUsers = new HashSet<User>();
  private Set<ResourceGroup> resourceGroups = new HashSet<ResourceGroup>();
  private Set<BaseEvent> events = new HashSet<BaseEvent>();

  private Set<FilePermission> filePermission = new HashSet<FilePermission>();
  private Set<DirectoryPermission> directoryPermission = new HashSet<DirectoryPermission>();

  private Boolean taskGroup;
  private Boolean documentGroup;
  private Boolean eventGroup;
  private Boolean timeGroup;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
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

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<GroupConfiguration> getGroupConfiguration() {
    return groupConfiguration;
  }

  public void setGroupConfiguration(Set<GroupConfiguration> groupConfiguration) {
    this.groupConfiguration = groupConfiguration;
  }

  @ManyToMany(mappedBy = "groups")
  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @ManyToMany(mappedBy = "accessGroups")
  public Set<User> getAccessUsers() {
    return accessUsers;
  }

  public void setAccessUsers(Set<User> accessUsers) {
    this.accessUsers = accessUsers;
  }

  @ManyToMany
  @JoinTable(name = "GroupResourceGroups", joinColumns = @JoinColumn(name = "groupId", nullable = false), inverseJoinColumns = @JoinColumn(name = "resourceGroupId", nullable = false))
  public Set<ResourceGroup> getResourceGroups() {
    return this.resourceGroups;
  }

  public void setResourceGroups(Set<ResourceGroup> resourceGroups) {
    this.resourceGroups = resourceGroups;
  }

  @OneToMany(mappedBy = "group")
  public Set<BaseEvent> getEvents() {
    return this.events;
  }

  public void setEvents(Set<BaseEvent> events) {
    this.events = events;
  }

  public Boolean getDocumentGroup() {
    return documentGroup;
  }

  public void setDocumentGroup(Boolean documentGroup) {
    this.documentGroup = documentGroup;
  }

  public Boolean getEventGroup() {
    return eventGroup;
  }

  public void setEventGroup(Boolean eventGroup) {
    this.eventGroup = eventGroup;
  }

  public Boolean getTaskGroup() {
    return taskGroup;
  }

  public void setTaskGroup(Boolean taskGroup) {
    this.taskGroup = taskGroup;
  }

  public Boolean getTimeGroup() {
    return timeGroup;
  }

  public void setTimeGroup(Boolean timeGroup) {
    this.timeGroup = timeGroup;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<DirectoryPermission> getDirectoryPermission() {
    return directoryPermission;
  }

  public void setDirectoryPermission(Set<DirectoryPermission> directoryPermission) {
    this.directoryPermission = directoryPermission;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<FilePermission> getFilePermission() {
    return filePermission;
  }

  public void setFilePermission(Set<FilePermission> filePermission) {
    this.filePermission = filePermission;
  }
}
