package wamp;

public abstract class WampOutboundMessage extends WampMessage {

	public WampOutboundMessage(MessageType type) {
		super(type);
	}

	public abstract Object[] serialize();

}
