package ch.ess.cal.web.time;

import java.util.Calendar;

public class CalendarRange {
  private Calendar from;
  private Calendar to;
  private String description;
  private String internalDescription;
  private boolean end;

  public CalendarRange(final Calendar from, final Calendar to, final String description,
      final String internalDescription, final boolean end) {
    this.from = (Calendar)from.clone();
    this.to = (Calendar)to.clone();
    this.description = description;
    this.internalDescription = internalDescription;
    this.end = end;
  }

  public Calendar getFrom() {
    return from;
  }

  public void setFrom(final Calendar from) {
    this.from = from;
  }

  public Calendar getTo() {
    return to;
  }

  public void setTo(final Calendar to) {
    this.to = to;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getInternalDescription() {
    return internalDescription;
  }

  public void setInternalDescription(final String internalDescription) {
    this.internalDescription = internalDescription;
  }

  public boolean isEnd() {
    return end;
  }

  public void setEnd(boolean end) {
    this.end = end;
  }
}