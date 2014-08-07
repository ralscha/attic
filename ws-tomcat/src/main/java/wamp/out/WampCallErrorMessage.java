package wamp.out;

import wamp.MessageType;
import wamp.WampOutboundMessage;

public class WampCallErrorMessage extends WampOutboundMessage {

	private final String callId;

	private final String errorURI;

	private final String errorDesc;

	private final Object errorDetails;

	public WampCallErrorMessage(String callId, String errorURI, String errorDesc) {
		this(callId, errorURI, errorDesc, null);
	}

	public WampCallErrorMessage(String callId, String errorURI, String errorDesc,
			Object errorDetails) {
		super(MessageType.CALLERROR);
		this.callId = callId;
		this.errorURI = errorURI;
		this.errorDesc = errorDesc;
		this.errorDetails = errorDetails;
	}

	@Override
	public Object[] serialize() {
		if (errorDetails == null) {
			return new Object[] { getType(), callId, errorURI, errorDesc };
		}
		return new Object[] { getType(), callId, errorURI, errorDesc, errorDetails };
	}

}
