package ch.ess.blank.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a UserRole
 * 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $   
 * @hibernate.class  table="blankUserRole" lazy="true"
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
   * @hibernate.many-to-one class="ch.ess.blank.db.Role" 
   * @hibernate.column name="roleId" not-null="true" index="FK_UserRole_Role"
   */
  public Role getRole() {
    return role;
  }

  /** 
   * @hibernate.many-to-one class="ch.ess.blank.db.User"
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