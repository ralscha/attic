package ch.rasc.pubsub.hazelcast;

import java.io.Serializable;

public class ShortMessageEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String msg;

	public ShortMessageEvent(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
