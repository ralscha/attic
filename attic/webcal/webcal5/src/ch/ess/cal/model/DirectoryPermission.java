package ch.ess.cal.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;

@Entity
public class DirectoryPermission extends BaseObject {

  private Boolean readPermission;
  private Boolean writePermission;
  private Directory directory;
  private User user;
  private Group group;

  @ManyToOne
  @JoinColumn(name = "directoryId", nullable = false)   
  public Directory getDirectory() {
    return directory;
  }

  public void setDirectory(Directory directory) {
    this.directory = directory;
  }

  @ManyToOne
  @JoinColumn(name = "groupId")
  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public Boolean getReadPermission() {
    return readPermission;
  }

  public void setReadPermission(Boolean read) {
    this.readPermission = read;
  }

  @ManyToOne
  @JoinColumn(name = "userId")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Boolean getWritePermission() {
    return writePermission;
  }

  public void setWritePermission(Boolean write) {
    this.writePermission = write;
  }

}
