package ch.ess.cal.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** A business entity class representing a link between Event and Category
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 *    
 */
@Entity
public class EventCategory extends BaseObject {

  private Event event;
  private Category category;

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
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
