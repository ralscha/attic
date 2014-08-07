package wamp.in;

import wamp.MessageType;
import wamp.WampInboundMessage;

public class WampUnsubscribeMessage extends WampInboundMessage {

	private String topicURI;

	public WampUnsubscribeMessage() {
		super(MessageType.UNSUBSCRIBE);
	}

	@Override
	public void deserialize(Object[] message) {
		topicURI = (String) message[1];
	}

	public String getTopicURI() {
		return topicURI;
	}

}
