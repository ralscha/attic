package ch.ess.issue.entity;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.jboss.seam.annotations.Name;

@Name("customer")
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {

  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Project> projects = new HashSet<Project>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Project> getProjects() {
    return projects;
  }

  public void setProjects(Set<Project> projects) {
    this.projects = projects;
  }

}
