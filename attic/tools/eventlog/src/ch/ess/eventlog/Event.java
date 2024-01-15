package ch.ess.eventlog;

public class Event {
  private String typ;
  private String date;
  private String time;
  private String source;
  private String description;

  public String getTyp() {
    return typ;
  }

  public void setTyp(String typ) {
    this.typ = typ;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String toString() {
    return "Typ: " + typ + "Date: " + date + "  Time: " + time + "  Source: " + source + "  Description: " + description;
  }
  
}
