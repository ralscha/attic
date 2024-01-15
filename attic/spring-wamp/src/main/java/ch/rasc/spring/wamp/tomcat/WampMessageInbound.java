package ch.rasc.spring.wamp.tomcat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.rasc.spring.wamp.WampMessageHandler;

public class WampMessageInbound extends MessageInbound {

	private final String sessionId;

	private final WampMessageHandler messageHandler;

	public WampMessageInbound(HttpServletRequest request) {
		this.sessionId = request.getSession().getId();
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession()
						.getServletContext());
		messageHandler = wac.getBean(WampMessageHandler.class);
	}

	@Override
	protected void onOpen(WsOutbound outbound) {
		try {
			outbound.writeTextMessage(CharBuffer.wrap(messageHandler.open(sessionId)));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void onClose(int status) {
		messageHandler.close(sessionId);
	}

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
		throw new UnsupportedOperationException("Binary message not supported.");
	}

	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
		String outMessage = messageHandler.handle(message.toString());
		getWsOutbound().writeTextMessage(CharBuffer.wrap(outMessage));
	}
}