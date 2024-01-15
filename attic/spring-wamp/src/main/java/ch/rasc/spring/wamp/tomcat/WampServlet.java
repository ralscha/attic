package ch.rasc.spring.wamp.tomcat;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.FixedWebSocketServlet;
import org.apache.catalina.websocket.StreamInbound;

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
		return new WampMessageInbound(request);
	}

}