package wamp.out;

import wamp.MessageType;
import wamp.WampOutboundMessage;

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
