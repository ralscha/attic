package ch.ess.cal.vcal;

public class VTodo extends VObject implements VCalendarConstants {

  public String toString() {
    return (BEGIN + ":" + VTODO + "\r\n" + super.toString() + END + ":" + VTODO + "\r\n");
  }
}