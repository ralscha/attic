package wamp.in;

import java.util.Arrays;

import wamp.MessageType;
import wamp.WampInboundMessage;

public class WampCallMessage extends WampInboundMessage {

	private String callId;

	private String procURI;

	private Object parameters;

	public WampCallMessage() {
		super(MessageType.CALL);
	}

	@Override
	public void deserialize(Object[] message) {
		callId = (String) message[1];
		procURI = (String) message[2];

		if (message.length == 3) {
			parameters = null;
		}
		else if (message.length == 4) {
			parameters = message[3];
		}
		else {
			parameters = Arrays.copyOfRange(message, 3, message.length);
		}
	}

	public String getCallId() {
		return callId;
	}

	public String getProcURI() {
		return procURI;
	}

	public Object getParameters() {
		return parameters;
	}

}
