package ch.ess.blank.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Permission
 * 
 * @author  Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 16:51:14 $   
 * @hibernate.class  table="Permission" lazy="true"
 */

public class Permission extends Persistent {

  private String permission;
  private Set userGroup;

  /** 
   * @hibernate.property length="255" not-null="true"
   */
  public String getPermission() {
    return permission;
  }

  public void setPermission(String string) {
    permission = string;
  }

  /**      
   * @hibernate.set lazy="true" table="UserGroupPermissions"
   * @hibernate.collection-key column="permissionId"     
   * @hibernate.collection-many-to-many class="ch.ess.blank.db.UserGroup" column="userGroupId"
   */
  public Set getUserGroups() {
    if (userGroup == null) {
      userGroup = new HashSet();
    }
    return this.userGroup;
  }

  public void setUserGroups(Set userGroup) {
    this.userGroup = userGroup;
  }

  //finder methods
  public static List find() throws HibernateException {
    return HibernateSession.currentSession().find("from Permission");
  }

}