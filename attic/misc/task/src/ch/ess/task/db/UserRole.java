package ch.ess.task.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a UserRole
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:29 $   
  * @hibernate.class  table="taskUserRole" lazy="true"
  */

public class UserRole extends Persistent {

  private String userName;
  private String roleName;
  private User user;
  private Role role;

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getRoleName() {
    return roleName;
  }

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getUserName() {
    return userName;
  }

  public void setRoleName(String string) {
    roleName = string;
  }

  public void setUserName(String string) {
    userName = string;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.task.db.Role" column="roleId" not-null="true"
  */
  public Role getRole() {
    return role;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.task.db.User" column="userId" not-null="true" 
  */
  public User getUser() {
    return user;
  }

  public void setRole(Role r) {
    role = r;
  }

  public void setUser(User u) {
    user = u;
  }

}
