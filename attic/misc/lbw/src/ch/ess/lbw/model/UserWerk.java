package ch.ess.lbw.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;

@Entity
public class UserWerk extends BaseObject {

  private Werk werk;
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
  @JoinColumn(name = "werkId", nullable = false)
  public Werk getWerk() {
    return werk;
  }

  public void setWerk(Werk werk) {
    this.werk = werk;
  }

}