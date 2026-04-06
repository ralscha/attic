package ch.rasc.springwebsocket.client;

import org.msgpack.MessagePack;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

public class ClientQuoteHandler extends BinaryWebSocketHandler {

	private final MessagePack msgpack;

	public ClientQuoteHandler() {
		this.msgpack = new MessagePack();
		this.msgpack.register(Quote.class);
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
			throws Exception {
		Quote[] dst = this.msgpack.read(message.getPayload(), Quote[].class);
		for (Quote quote : dst) {
			System.out.println(quote);
		}

	}

}
