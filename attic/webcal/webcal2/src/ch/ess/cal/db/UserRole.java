package ch.ess.cal.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a UserRole
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:46 $  
  * @hibernate.class  table="calUserRole" lazy="true"
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
  * @hibernate.many-to-one class="ch.ess.cal.db.Role" 
  * @hibernate.column name="roleId" not-null="true" index="FK_UserRole_Role"
  */
  public Role getRole() {
    return role;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.User"
  * @hibernate.column name="userId" not-null="true" index="FK_UserRole_User" 
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
