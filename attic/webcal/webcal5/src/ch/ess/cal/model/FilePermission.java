package ch.ess.cal.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;

@Entity
public class FilePermission extends BaseObject {

  private Boolean readPermission;
  private Boolean writePermission;
  private File file;
  private User user;
  private Group group;

  @ManyToOne
  @JoinColumn(name = "fileId", nullable = false)
  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
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
