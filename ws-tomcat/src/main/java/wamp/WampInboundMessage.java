package wamp;

public abstract class WampInboundMessage extends WampMessage {

	public WampInboundMessage(MessageType type) {
		super(type);
	}

	public abstract void deserialize(Object[] message);

}
