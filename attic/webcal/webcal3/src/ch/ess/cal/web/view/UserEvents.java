package ch.ess.cal.web.view;

import java.io.Serializable;

public class UserEvents implements Serializable {
  private String name;
  private Integer id;
  private String[] tooltip;
  private String[] eventId;

  public String getName() {
    return name;
  }

  public void setName(String string) {
    name = string;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer long1) {
    id = long1;
  }

  public String[] getTooltip() {
    return tooltip;
  }

  public void setTooltip(String[] tooltip) {
    this.tooltip = tooltip;
  }

  public String[] getEventId() {
    return eventId;
  }

  public void setEventId(String[] eventId) {
    this.eventId = eventId;
  }

}
