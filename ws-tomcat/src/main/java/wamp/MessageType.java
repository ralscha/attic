package wamp;

public enum MessageType {
	WELCOME(0), PREFIX(1), CALL(2), CALLRESULT(3), CALLERROR(4), SUBSCRIBE(5), UNSUBSCRIBE(
			6), PUBLISH(7), EVENT(8);

	private int type;

	private MessageType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
