package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

/** A business entity class representing a EventProperty
 * 
 * @author sr
 * @version $Revision: 1.7 $ $Date: 2005/04/04 11:31:11 $ 
 * 
 */
@Entity
@Proxy(lazy = false)
public class EventProperty extends BaseObject {

  private String name;
  private String propValue;
  private Event event;

  @Column(nullable = false, length = 100)
  public String getName() {
    return name;
  }

  @Column(length = 255)
  public String getPropValue() {
    return propValue;
  }

  public void setName(final String string) {
    name = string;
  }

  public void setPropValue(final String string) {
    propValue = string;
  }

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  public Event getEvent() {
    return this.event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

}
