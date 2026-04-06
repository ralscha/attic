package ch.rasc.pubsub.guava;

import java.util.Date;

public class TimeEvent {
	private final Date time;

	public TimeEvent(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return this.time;
	}

}
