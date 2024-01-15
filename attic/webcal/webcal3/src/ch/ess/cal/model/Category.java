package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

/** A business entity class representing a Category
 * 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $ 
 */

@Entity
public class Category extends TranslateObject {

  private String icalKey;
  private String colour;
  private Boolean unknown;

  private Set<EventCategory> eventCategories = new HashSet<EventCategory>();

  @Column(length = 100)
  public String getIcalKey() {
    return this.icalKey;
  }

  public void setIcalKey(String icalKey) {
    this.icalKey = icalKey;
  }

  @Column(nullable = false, length = 6)
  public String getColour() {
    return this.colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  @Column(nullable = false)
  public Boolean isUnknown() {
    return this.unknown;
  }

  public void setUnknown(Boolean unknown) {
    this.unknown = unknown;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<EventCategory> getEventCategory() {
    return eventCategories;
  }

  public void setEventCategory(Set<EventCategory> eventCategories) {
    this.eventCategories = eventCategories;
  }

}
