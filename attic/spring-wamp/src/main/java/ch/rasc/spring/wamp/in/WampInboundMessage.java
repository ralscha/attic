package ch.rasc.spring.wamp.in;

import ch.rasc.spring.wamp.MessageType;
import ch.rasc.spring.wamp.WampMessage;

public abstract class WampInboundMessage extends WampMessage {

	public WampInboundMessage(MessageType type) {
		super(type);
	}

	public abstract void deserialize(Object[] message);

}
