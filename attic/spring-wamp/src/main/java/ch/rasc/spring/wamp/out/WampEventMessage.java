package ch.rasc.spring.wamp.out;

import ch.rasc.spring.wamp.MessageType;

public class WampEventMessage extends WampOutboundMessage {

	private final String topicURI;

	private final Object event;

	public WampEventMessage(String topicURI, Object event) {
		super(MessageType.EVENT);
		this.topicURI = topicURI;
		this.event = event;
	}

	@Override
	public Object[] serialize() {
		return new Object[] { getType(), topicURI, event };
	}

}
