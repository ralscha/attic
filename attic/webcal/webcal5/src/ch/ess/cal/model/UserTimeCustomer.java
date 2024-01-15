package ch.ess.cal.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;

@Entity
public class UserTimeCustomer extends BaseObject {

  private TimeCustomer timeCustomer;
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
  @JoinColumn(name = "timeCustomerId", nullable = false)
  public TimeCustomer getTimeCustomer() {
    return timeCustomer;
  }

  public void setTimeCustomer(TimeCustomer customer) {
    this.timeCustomer = customer;
  }
  
}
