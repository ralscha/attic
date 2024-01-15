package ch.ess.cal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("E")
public class Event extends BaseEvent {
  //no additional attributes
}
