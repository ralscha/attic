package ch.rasc.springwebsocket;

import java.util.concurrent.Executor;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class ChatHandler extends RegistryHandler {

	private final Executor asyncExecutor;

	public ChatHandler(Executor asyncExecutor) {
		this.asyncExecutor = asyncExecutor;
	}

	@Override
	protected void handleTextMessage(final WebSocketSession session,
			final TextMessage message) throws Exception {
		this.asyncExecutor.execute(() -> sendToAll(session.getId() + " says: <strong>"
				+ message.getPayload() + "</strong>"));

	}

}