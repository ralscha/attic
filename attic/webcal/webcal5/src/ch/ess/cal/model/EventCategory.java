package ch.ess.cal.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;


@Entity
public class EventCategory extends BaseObject {

  private BaseEvent event;
  private Category category;

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  public BaseEvent getEvent() {
    return event;
  }

  public void setEvent(BaseEvent event) {
    this.event = event;
  }

  @ManyToOne
  @JoinColumn(name = "categoryId", nullable = false)
  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

}
