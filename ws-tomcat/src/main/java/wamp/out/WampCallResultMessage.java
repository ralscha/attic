package wamp.out;

import wamp.MessageType;
import wamp.WampOutboundMessage;

public class WampCallResultMessage extends WampOutboundMessage {

	private final String callId;

	private final Object result;

	public WampCallResultMessage(String callId, Object result) {
		super(MessageType.CALLRESULT);
		this.callId = callId;
		this.result = result;
	}

	@Override
	public Object[] serialize() {
		return new Object[] { getType(), callId, result };
	}

}
