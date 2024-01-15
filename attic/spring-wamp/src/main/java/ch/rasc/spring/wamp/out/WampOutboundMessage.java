package ch.rasc.spring.wamp.out;

import ch.rasc.spring.wamp.MessageType;
import ch.rasc.spring.wamp.WampMessage;

public abstract class WampOutboundMessage extends WampMessage {

	public WampOutboundMessage(MessageType type) {
		super(type);
	}

	public abstract Object[] serialize();

}
