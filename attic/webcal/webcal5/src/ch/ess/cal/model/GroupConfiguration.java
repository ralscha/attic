package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import ch.ess.base.model.BaseObject;


@Entity
@Proxy(lazy = false)
public class GroupConfiguration extends BaseObject {

  private String name;
  private String propValue;
  private Group group;

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
  @JoinColumn(name = "groupId", nullable = false)
  public Group getGroup() {
    return this.group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

}
