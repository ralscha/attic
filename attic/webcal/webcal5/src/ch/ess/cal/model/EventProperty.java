package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import ch.ess.base.model.BaseObject;


@Entity
@Proxy(lazy = false)
public class EventProperty extends BaseObject {

  private String name;
  private String propValue;
  private BaseEvent event;

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
  public BaseEvent getEvent() {
    return this.event;
  }

  public void setEvent(BaseEvent event) {
    this.event = event;
  }

}
