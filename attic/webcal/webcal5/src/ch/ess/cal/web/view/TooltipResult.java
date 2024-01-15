package ch.ess.cal.web.view;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class TooltipResult {
  @RemoteProperty
  private String tooltip;
  @RemoteProperty
  private int day;
  @RemoteProperty
  private String width;
  @RemoteProperty
  private String title;


  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public String getTooltip() {
    return tooltip;
  }

  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
