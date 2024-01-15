package ch.ess.downloader.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.sun.istack.internal.NotNull;

@Entity
@Table(name="dl_userfolder")
public class UserFolder extends BaseObject {

  private Folder folder;
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "folderId")
  @NotNull
  public Folder getFolder() {
    return this.folder;
  }

  public void setFolder(Folder folder) {
    this.folder = folder;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  @NotNull
  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
