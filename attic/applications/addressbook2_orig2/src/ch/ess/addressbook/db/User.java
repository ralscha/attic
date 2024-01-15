package ch.ess.addressbook.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import net.sf.hibernate.type.Type;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing an User
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.11 $ $Date: 2003/11/11 18:58:37 $
  * @hibernate.class  table="abUser" lazy="true"
  */

public class User extends Persistent {

  private String firstName;
  private String name;
  private String email;
  private String userName;
  private String passwordHash;
  private String loginToken;
  private Date loginTokenTime;

  private Locale locale;

  private Set contacts;
  private Set userRoles;
  private Set userConfiguration;

  /** 
  * @hibernate.property length="100" not-null="false"
  */
  public String getFirstName() {
    return firstName;
  }

  /** 
  * @hibernate.property length="10" not-null="true"
  */
  public Locale getLocale() {
    return locale;
  }

  /** 
  * @hibernate.property length="100" not-null="true"
  */
  public String getName() {
    return name;
  }

  /** 
  * @hibernate.property length="40" not-null="false"
  */
  public String getPasswordHash() {
    return passwordHash;
  }

  /** 
  * @hibernate.property unique="true" length="100" not-null="true"
  */
  public String getUserName() {
    return userName;
  }

  /** 
  * @hibernate.property length="255" not-null="true"
  */
  public String getEmail() {
    return email;
  }

  /** 
  * @hibernate.property length="40" not-null="false"
  */
  public String getLoginToken() {
    return loginToken;
  }

  /** 
  * @hibernate.property type="timestamp" not-null="false"
  */
  public Date getLoginTokenTime() {
    return loginTokenTime;
  }

  public void setLoginToken(String string) {
    loginToken = string;
  }

  public void setLoginTokenTime(Date date) {
    loginTokenTime = date;
  }

  public void setFirstName(String string) {
    firstName = string;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setName(String string) {
    name = string;
  }

  public void setPasswordHash(String string) {
    passwordHash = string;
  }

  public void setUserName(String string) {
    userName = string;
  }

  public void setEmail(String string) {
    email = string;
  }

  /**      
     * @hibernate.set lazy="true" table="abUserConfiguration" cascade="all" inverse="true"
     * @hibernate.collection-key  column="userId"     
     * @hibernate.collection-one-to-many  class="ch.ess.addressbook.db.UserConfiguration"
     */
  public Set getUserConfiguration() {
    return userConfiguration;
  }

  public void setUserConfiguration(Set set) {
    userConfiguration = set;
  }

  /**      
     * @hibernate.set lazy="true" table="abUserRole" cascade="all-delete-orphan" inverse="true"
     * @hibernate.collection-key  column="userId"     
     * @hibernate.collection-one-to-many  class="ch.ess.addressbook.db.UserRole"
     */
  public Set getUserRoles() {
    if (userRoles == null) {
      userRoles = new HashSet();
    }

    return userRoles;
  }

  public void setUserRoles(Set set) {
    userRoles = set;
  }


  /**      
   * @hibernate.set lazy="true" table="abContact" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="userId"     
   * @hibernate.collection-one-to-many  class="ch.ess.addressbook.db.Contact"
   */
  public Set getContacts() {
    if (contacts == null) {
      contacts = new HashSet();
    }

    return contacts;
  }

  public void setContacts(Set set) {
    contacts = set;
  }

  public void addRole(Role role) {
    UserRole userRole = new UserRole();
    userRole.setUserName(getUserName());
    userRole.setRoleName(role.getName());
    userRole.setRole(role);
    userRole.setUser(this);

    getUserRoles().add(userRole);
  }


  //load
  public static User load(Long userId) throws HibernateException {
    return (User)HibernateSession.currentSession().load(User.class, userId);
  }

  //delete method

  public static int delete(Long userId) throws HibernateException {
    return HibernateSession.currentSession().delete("from User as u where u.id = ?", userId, Hibernate.LONG);
  }

  //finder methods    
  public static Iterator findWithSearchtext(String searchText, String roleId) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(User.class);

    if (!GenericValidator.isBlankOrNull(searchText)) {
      searchText = "%" + searchText + "%";
      crit.add(
        Expression.or(
          Expression.or(Expression.like("email", searchText), Expression.like("name", searchText)),
          Expression.like("userName", searchText)));
    }

    if (!GenericValidator.isBlankOrNull(roleId)) {
      crit.createCriteria("userRoles").add(Expression.eq("role.id", new Long(roleId)));
    }

    crit.addOrder(Order.asc("userName"));

    return crit.list().iterator();

  }

  public static User find(String userName, String passwordHash) throws HibernateException {
    List userList =
      HibernateSession.currentSession().find(
        "from User as u where u.userName = ? AND u.passwordHash = ?",
        new Object[] { userName, passwordHash },
        new Type[] { Hibernate.STRING, Hibernate.STRING });

    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }

  public static User find(String userName) throws HibernateException {
    List userList =
      HibernateSession.currentSession().find("from User as u where u.userName = ?", userName, Hibernate.STRING);

    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }

  public static User findExcludeId(String userName, Long id) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(User.class);

    crit.add(Expression.eq("userName", userName));

    if (id != null) {
      crit.add(Expression.not(Expression.eq("id", id)));
    }

    
    List userList = crit.list();

    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }

  
  public static User findWithLoginToken(String loginToken) throws HibernateException {
    List userList =
      HibernateSession.currentSession().find("from User as u where u.loginToken = ?", loginToken, Hibernate.STRING);
    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }

}
