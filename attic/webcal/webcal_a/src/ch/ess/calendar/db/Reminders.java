package ch.ess.calendar.db;

public class Reminders implements java.io.Serializable {


  private static final long serialVersionUID = 1L;

  private int reminderid;
	private int appointmentid;
	private int minutesbefore;
	private String email;

	public Reminders() {
		this.reminderid = 0;
		this.appointmentid = 0;
		this.minutesbefore = 0;
		this.email = null;
	}

	public Reminders(int reminderid, int appointmentid, int minutesbefore, String email) {
		this.reminderid = reminderid;
		this.appointmentid = appointmentid;
		this.minutesbefore = minutesbefore;
		this.email = email;
	}

	public int getReminderid() {
		return reminderid;
	}

	public void setReminderid(int reminderid) {
		this.reminderid = reminderid;
	}

	public int getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(int appointmentid) {
		this.appointmentid = appointmentid;
	}

	public int getMinutesbefore() {
		return minutesbefore;
	}

	public void setMinutesbefore(int minutesbefore) {
		this.minutesbefore = minutesbefore;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String toString() {
		return "Reminders("+ reminderid + " " + appointmentid + " " + minutesbefore + " " + email+")";
	}
}
