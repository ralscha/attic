package wamp;

public class WampMessage {

	private final MessageType type;

	public WampMessage(MessageType type) {
		this.type = type;
	}

	public int getType() {
		return type.getType();
	}

}
