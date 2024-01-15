package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.TranslateObject;



@Entity
public class Category extends TranslateObject {

  private String icalKey;
  private String colour;
  private Boolean defaultCategory;
  private Integer sequence;

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
  public Boolean getDefaultCategory() {
    return defaultCategory;
  }

  public void setDefaultCategory(Boolean defaultCategory) {
    this.defaultCategory = defaultCategory;
  }
  
  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequenz) {
    this.sequence = sequenz;
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
