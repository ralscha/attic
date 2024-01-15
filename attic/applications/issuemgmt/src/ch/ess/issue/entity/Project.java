package ch.ess.issue.entity;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "project")
public class Project extends AbstractEntity {

  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Component> components = new HashSet<Component>();

  @ManyToOne
  @JoinColumn(name = "customerId", nullable = false)
  private Customer customer;

    
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Set<Component> getComponents() {
    return components;
  }

  public void setComponents(Set<Component> components) {
    this.components = components;
  }

}
