package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;


@Entity
@Proxy(lazy = false)
public class Email extends BaseObject {

  private Integer sequence;
  private String email;
  private Boolean defaultEmail;
  private User user;
  private Group group;

  @Column(nullable = false)
  public Integer getSequence() {
    return this.sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  @Column(nullable = false, length = 255)
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(nullable = false)
  public Boolean isDefaultEmail() {
    return this.defaultEmail;
  }

  public void setDefaultEmail(Boolean defaultEmail) {
    this.defaultEmail = defaultEmail;
  }

  @ManyToOne
  @JoinColumn(name = "userId")
  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @ManyToOne
  @JoinColumn(name = "groupId")
  public Group getGroup() {
    return this.group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

}
