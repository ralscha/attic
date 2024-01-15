package ch.ess.issue.entity;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "component")
public class Component extends AbstractEntity {

  private String name;
  
  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
