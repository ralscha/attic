package ch.ess.cal.vcal;

public class VEvent extends VObject implements VCalendarConstants {

  public String toString() {
    return (BEGIN + ":" + VEVENT + "\r\n" + super.toString() + END + ":" + VEVENT + "\r\n");
  }

}