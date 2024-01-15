package ch.ess.downloader.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;

@Entity
@Table(name="dl_folder")
public class Folder extends BaseObject {

  private String name;
  private Date createDate;
  private Date accessedDate;
  private Boolean active;
  private String uuid;
  private Set<File> files = new HashSet<File>(0);
  private Set<UserFolder> userFolders = new HashSet<UserFolder>(0);

  @Length(max=255)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getAccessedDate() {
    return this.accessedDate;
  }

  public void setAccessedDate(Date accessedDate) {
    this.accessedDate = accessedDate;
  }

  public Boolean getActive() {
    return this.active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "folder")
  public Set<File> getFiles() {
    return this.files;
  }

  public void setFiles(Set<File> files) {
    this.files = files;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "folder")
  public Set<UserFolder> getUserFolders() {
    return this.userFolders;
  }

  public void setUserFolders(Set<UserFolder> userFolders) {
    this.userFolders = userFolders;
  }

  
  @Length(max=50)
  public String getUuid() {
    return uuid;
  }
  
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

}
