package ch.ess.blank.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a UserGroup
 * 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 16:51:14 $   
 * @hibernate.class  table="UserGroup" lazy="true"
 */

public class UserGroup extends Persistent {

  private String groupName;
  private Set permissions;
  private Set users;

  /** 
   * @hibernate.property length="255" not-null="true"
   */
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**      
   * @hibernate.set lazy="true" table="UserGroupPermissions"
   * @hibernate.collection-key column="userGroupId"     
   * @hibernate.collection-many-to-many class="ch.ess.blank.db.Permission" column="permissionId"
   */
  public Set getPermissions() {
    if (permissions == null) {
      permissions = new HashSet();
    }
    return this.permissions;
  }

  public void setPermissions(Set permissions) {
    this.permissions = permissions;
  }

  /**      
   * @hibernate.set lazy="true" table="UserUserGroups"
   * @hibernate.collection-key column="userGroupId"     
   * @hibernate.collection-many-to-many class="ch.ess.blank.db.User" column="userId"
   */
  public Set getUsers() {
    if (users == null) {
      users = new HashSet();
    }
    return this.users;
  }

  public void setUsers(Set users) {
    this.users = users;
  }

  public static UserGroup load(Long userGroupId) throws HibernateException {
    return (UserGroup) HibernateSession.currentSession().load(UserGroup.class, userGroupId);
  }

  public static int delete(Long userGroupId) throws HibernateException {
    return HibernateSession.currentSession().delete("from UserGroup as u where u.id = ?", userGroupId, Hibernate.LONG);
  }

  //finder methods
  public static List find(String name) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(UserGroup.class);

    if (!GenericValidator.isBlankOrNull(name)) {
      name = "%" + name + "%";
      crit.add(Expression.like("groupName", name.trim()));
    }

    return crit.list();
  }

  public boolean canDelete() throws HibernateException {
    if (getId() != null) {
      return ((HibernateSession.collectionSize(users) == 0));
    }
    return false;

  }
}