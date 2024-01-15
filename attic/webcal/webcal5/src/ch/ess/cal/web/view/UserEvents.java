package ch.ess.cal.web.view;

import java.io.Serializable;

public class UserEvents implements Serializable {
  private String name;
  private Integer id;
  private String[] tooltip;
  private int[] tooltipWidth;
  private String[] eventId;
  private String[] picFileName;
  private boolean[] multipleEvents;

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

  public int[] getTooltipWidth() {
    return tooltipWidth;
  }

  public void setTooltipWidth(int[] tooltipWidth) {
    this.tooltipWidth = tooltipWidth;
  }

  public boolean[] getMultipleEvents() {
    return multipleEvents;
  }

  public void setMultipleEvents(boolean[] multipleEvents) {
    this.multipleEvents = multipleEvents;
  }

  
  public String[] getPicFileName() {
    return picFileName;
  }

  
  public void setPicFileName(String[] picFileName) {
    this.picFileName = picFileName;
  }

}
