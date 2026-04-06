package ch.rasc.pubsub.guava;

public class SpecialMsgEvent extends MsgEvent {

	private final String user;

	public SpecialMsgEvent(String message, String user) {
		super(message);
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}

}
