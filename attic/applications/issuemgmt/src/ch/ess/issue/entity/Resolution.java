package ch.ess.issue.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="resolution")
public class Resolution extends AbstractEntity {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
