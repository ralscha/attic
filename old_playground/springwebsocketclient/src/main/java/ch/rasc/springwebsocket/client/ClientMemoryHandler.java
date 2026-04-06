package ch.rasc.springwebsocket.client;

import java.time.LocalDateTime;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientMemoryHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {
		Memory memory = this.objectMapper.readValue(message.getPayload(), Memory.class);
		System.out.println(LocalDateTime.now().toString() + ": Got this message");
		System.out.println(memory.heap);
		System.out.println(memory.nonheap);
		System.out.println();
	}

}
