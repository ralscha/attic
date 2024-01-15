package ch.ess.issue.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="issueType")
public class IssueType extends AbstractEntity {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
