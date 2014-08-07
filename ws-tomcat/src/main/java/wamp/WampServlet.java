package wamp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.FixedWebSocketServlet;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import wamp.in.WampCallMessage;
import wamp.out.WampWelcomeMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = "/wamp")
public class WampServlet extends FixedWebSocketServlet {

	private static final long serialVersionUID = 1L;

	final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected String selectSubProtocol(List<String> subProtocols) {
		for (String sub : subProtocols) {
			if ("wamp".equals(sub)) {
				return sub;
			}
		}
		return null;
	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		return new WampMessageInbound(request.getSession().getId());
	}

	private final class WampMessageInbound extends MessageInbound {

		private final String sessionId;

		WampMessageInbound(String sessionId) {
			this.sessionId = sessionId;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			try {
				WampWelcomeMessage msg = new WampWelcomeMessage(sessionId);
				String m = objectMapper.writeValueAsString(msg.serialize());
				outbound.writeTextMessage(CharBuffer.wrap(m));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onClose(int status) {
			System.out.println("onClose");
		}

		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			throw new UnsupportedOperationException("Binary message not supported.");
		}

		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			Object[] msgs = objectMapper.readValue(message.toString(), Object[].class);
			int messageType = (Integer) msgs[0];
			switch (messageType) {
			case 1:
				// PREFIX
				// ignore
				break;
			case 2:
				// CALL
				WampCallMessage callMessage = new WampCallMessage();
				callMessage.deserialize(msgs);
				System.out.println(callMessage.getProcURI());
				break;
			case 5:
				// SUBSCRIBE
				break;
			case 6:
				// UNSUBSCRIBE
				break;
			case 7:
				// PUBLISH
				break;
			default:
				break;
			}

			getWsOutbound().writeTextMessage(message);
		}
	}

}