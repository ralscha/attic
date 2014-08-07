package wamp.out;

import wamp.MessageType;
import wamp.WampOutboundMessage;

public class WampWelcomeMessage extends WampOutboundMessage {

	private final String sessionId;

	private final int protocolVersion = 1;

	private final String serverIdent = "test/0.0.1";

	public WampWelcomeMessage(String sessionId) {
		super(MessageType.WELCOME);
		this.sessionId = sessionId;
	}

	@Override
	public Object[] serialize() {
		return new Object[] { getType(), sessionId, protocolVersion, serverIdent };
	}

}
