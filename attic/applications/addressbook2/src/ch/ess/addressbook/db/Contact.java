package ch.ess.addressbook.db;

import java.util.Iterator;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Contact
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:26 $ 
  * @hibernate.class  table="abContact"
  */

public class Contact extends Persistent {
    
  private String category;
  private User user;
  private Map attributes;
  

  /**      
     * @hibernate.map cascade="all-delete-orphan" table="abContactAttribute"     
     * @hibernate.collection-key  column="contactId"     
     * @hibernate.collection-index column="field" type="string" length="255"
     * @hibernate.collection-element column="value" type="string" length="500" not-null="true" 
     */
  public Map getAttributes() {
    return attributes;
  }

  /** 
  * @hibernate.property length="1" not-null="true"
  */
  public String getCategory() {
    return category;
  }

  public void setAttributes(Map map) {
    attributes = map;
  }

  public void setCategory(String string) {
    category = string;
  }
  
  
  /** 
  * @hibernate.many-to-one class="ch.ess.addressbook.db.User" column="userId" not-null="false"
  */
  public User getUser() {
    return user;
  }

  public void setUser(User u) {
    user = u;
  }  


  public static Iterator findAll() throws HibernateException {
    return HibernateSession.currentSession().iterate("from Contact as contact order by contact.category asc");  
  }
  
  public static Iterator findWithCategory(String category) throws HibernateException {
    return HibernateSession.currentSession().iterate("from Contact as contact where contact.category = ?", category.toLowerCase(), Hibernate.STRING);  
  }

  public static Iterator findWithIds(String ids) throws HibernateException {
    return HibernateSession.currentSession().iterate("from Contact as contact where contact.id in ("+ids+")");
  }

  public static int delete(Long contactId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Contact as c where c.id = ?", contactId, Hibernate.LONG);
  }

  //load
  public static Contact load(Long contactId) throws HibernateException {
    return (Contact)HibernateSession.currentSession().load(Contact.class, contactId);  
  }

  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }

  public boolean equals(Object other) {
    if (!(other instanceof Contact)) {
      return false;
    }
    
    Contact castOther = (Contact)other;
    return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
  }

  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }

  public String getAttribute(AttributeEnum key) {
    if (attributes != null) {
      String s = (String)attributes.get(key.getName());
      if (s != null) {
        return s;
      } else {
        return "";
      }
    }
    return "";
  }
  
  
}
