package ch.ess.cal.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** A business entity class representing a link between User and UserGroup
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 */

@Entity
public class UserUserGroup extends BaseObject {

  private UserGroup userGroup;
  private User user;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @ManyToOne
  @JoinColumn(name = "userGroupId", nullable = false)
  public UserGroup getUserGroup() {
    return userGroup;
  }

  public void setUserGroup(UserGroup userGroup) {
    this.userGroup = userGroup;
  }

}
