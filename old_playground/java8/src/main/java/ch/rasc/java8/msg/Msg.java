package ch.rasc.java8.msg;

import java.util.HashMap;
import java.util.Map;

public class Msg {
	private final Map<String, Object> headers = new HashMap<>();

	Msg(MsgType type) {
		this.headers.put(Headers.MSG_ID.name(), type);
	}

	public MsgType getType() {
		return (MsgType) this.headers.get(Headers.MSG_ID.name());
	}
}
