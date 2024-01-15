package ch.rasc.spring.wamp.in;

import ch.rasc.spring.wamp.MessageType;

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
