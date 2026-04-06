package ch.rasc.pubsub.guava;

public class MsgEvent {
	private final String message;

	public MsgEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
