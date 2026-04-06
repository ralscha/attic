package ch.rasc.pubsub.spring;

import org.springframework.context.ApplicationEvent;

public class AppEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final String message;

	public AppEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
