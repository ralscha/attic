package wamp.in;

import wamp.MessageType;
import wamp.WampInboundMessage;

public class WampSubscribeMessage extends WampInboundMessage {

	private String topicURI;

	public WampSubscribeMessage() {
		super(MessageType.SUBSCRIBE);
	}

	@Override
	public void deserialize(Object[] message) {
		topicURI = (String) message[1];
	}

	public String getTopicURI() {
		return topicURI;
	}

}
