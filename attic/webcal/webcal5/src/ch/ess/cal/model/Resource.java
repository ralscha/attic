package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ch.ess.base.model.TranslateObject;


@Entity
public class Resource extends TranslateObject {

  private ResourceGroup resourceGroup;
  private Set<BaseEvent> events = new HashSet<BaseEvent>();

  @ManyToOne
  @JoinColumn(name = "resourceGroupId", nullable = false)
  public ResourceGroup getResourceGroup() {
    return this.resourceGroup;
  }

  public void setResourceGroup(ResourceGroup resourceGroup) {
    this.resourceGroup = resourceGroup;
  }

  @OneToMany(mappedBy = "resource")
  public Set<BaseEvent> getEvents() {
    return this.events;
  }

  public void setEvents(Set<BaseEvent> events) {
    this.events = events;
  }

}
