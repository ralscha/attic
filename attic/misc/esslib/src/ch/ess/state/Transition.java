package ch.ess.state;

import org.apache.commons.lang.builder.*;

public class Transition {
  
  private State origin;
  private State destination;
  private Guard guard;

  public Transition() {
    this(null, null, null);
  }

  public Transition(State origin, State destination, Guard guard) {
    this.origin = origin;
    this.destination = destination;
    this.guard = guard;
  }

  public State getDestination() {
    return destination;
  }

  public Guard getGuard() {
    return guard;
  }

  public State getOrigin() {
    return origin;
  }

  public void setDestination(State destination) {
    this.destination = destination;
  }

  public void setGuard(Guard guard) {
    this.guard = guard;
  }

  public void setOrigin(State origin) {
    this.origin = origin;
  }


  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }


}
