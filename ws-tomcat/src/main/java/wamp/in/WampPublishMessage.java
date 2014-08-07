package wamp.in;

import java.util.ArrayList;
import java.util.List;

import wamp.MessageType;
import wamp.WampInboundMessage;

public class WampPublishMessage extends WampInboundMessage {

	private String topicURI;

	private Object event;

	private List<String> exclude;

	private List<String> eligible;

	private boolean excludeMe;

	public WampPublishMessage() {
		super(MessageType.PUBLISH);
		exclude = new ArrayList<>();
		eligible = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deserialize(Object[] message) {
		topicURI = (String) message[1];
		event = message[2];

		if (message.length == 4) {
			if (message[3] instanceof Boolean) {
				excludeMe = (Boolean) message[3];
			}
			else {
				exclude = (List<String>) message[3];
			}
		}
		if (message.length == 5) {
			eligible = (List<String>) message[4];
		}
	}

	public String getTopicURI() {
		return topicURI;
	}

	public Object getEvent() {
		return event;
	}

	public List<String> getExclude() {
		return exclude;
	}

	public List<String> getEligible() {
		return eligible;
	}

	public boolean isExcludeMe() {
		return excludeMe;
	}

}
