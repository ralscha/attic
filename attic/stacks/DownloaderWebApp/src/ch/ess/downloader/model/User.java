package ch.ess.downloader.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.Length;

@Entity
@Table(name="dl_user")
public class User extends BaseObject {

  private String loginId;
  private String password;
  private String roleName;
  private Set<UserFolder> userFolders = new HashSet<UserFolder>(0);

  @Length(max=255)
  public String getLoginId() {
    return this.loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  @Length(max=255)
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Length(max=255)
  public String getRoleName() {
    return this.roleName;
  }

  public void setRoleName(String role) {
    this.roleName = role;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  public Set<UserFolder> getUserFolders() {
    return this.userFolders;
  }

  public void setUserFolders(Set<UserFolder> userFolders) {
    this.userFolders = userFolders;
  }

}
