package ch.rasc.springwebsocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

public class QuoteHandler extends BinaryWebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		this.sessions.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
			throws Exception {
		this.sessions.remove(session.getId());
	}

	@Async
	public void sendToAll(byte[] message) {
		BinaryMessage binaryMsg = new BinaryMessage(message);

		for (WebSocketSession session : this.sessions.values()) {
			if (session.isOpen()) {
				try {
					session.sendMessage(binaryMsg);
				}
				catch (IOException e) {
					this.sessions.remove(session.getId());
					e.printStackTrace();
				}
			}
			else {
				this.sessions.remove(session.getId());
			}
		}
	}

}
