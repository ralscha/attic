package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/** A business entity class representing a Resource
 * 
 * @author sr
 * @version $Revision: 1.7 $ $Date: 2005/04/04 11:31:11 $ 
 */

@Entity
public class Resource extends TranslateObject {

  private ResourceGroup resourceGroup;
  private Set<Event> events = new HashSet<Event>();

  @ManyToOne
  @JoinColumn(name = "resourceGroupId", nullable = false)
  public ResourceGroup getResourceGroup() {
    return this.resourceGroup;
  }

  public void setResourceGroup(ResourceGroup resourceGroup) {
    this.resourceGroup = resourceGroup;
  }

  @OneToMany(mappedBy = "resource")
  public Set<Event> getEvents() {
    return this.events;
  }

  public void setEvents(Set<Event> events) {
    this.events = events;
  }

}
