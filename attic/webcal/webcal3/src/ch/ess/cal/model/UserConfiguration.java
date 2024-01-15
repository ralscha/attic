package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

/** A business entity class representing a UserConfiguration
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 */

@Entity
@Proxy(lazy = false)
public class UserConfiguration extends BaseObject {

  private String name;
  private String propValue;
  private User user;

  @Column(nullable = false, length = 100)
  public String getName() {
    return name;
  }

  @Column(length = 255)
  public String getPropValue() {
    return propValue;
  }

  public void setName(final String string) {
    name = string;
  }

  public void setPropValue(final String string) {
    propValue = string;
  }

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  public User getUser() {
    return user;
  }

  public void setUser(final User user) {
    this.user = user;
  }

}
