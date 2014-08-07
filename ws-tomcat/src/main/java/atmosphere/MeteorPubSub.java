/*
 * Copyright 2012 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package atmosphere;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atmosphere.config.service.MeteorService;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.HeaderConfig;
import org.atmosphere.cpr.Meteor;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;

/**
 * Simple PubSub resource that demonstrate many functionality supported by Atmosphere
 * JQuery Plugin (WebSocket, Comet) and Atmosphere Meteor extension.
 *
 * @author Jeanfrancois Arcand
 */
@MeteorService
public class MeteorPubSub extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// Create a Meteor
		Meteor m = Meteor.build(req);

		// Log all events on the console, including WebSocket events.
		m.addListener(new WebSocketEventListenerAdapter());

		res.setContentType("text/html;charset=ISO-8859-1");

		Broadcaster b = lookupBroadcaster(req.getPathInfo());
		m.setBroadcaster(b);

		String header = req.getHeader(HeaderConfig.X_ATMOSPHERE_TRANSPORT);
		if (header != null
				&& header.equalsIgnoreCase(HeaderConfig.LONG_POLLING_TRANSPORT)) {
			req.setAttribute(ApplicationConfig.RESUME_ON_BROADCAST, Boolean.TRUE);
			m.suspend(-1);
		}
		else {
			m.suspend(-1);
		}

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Broadcaster b = lookupBroadcaster(req.getPathInfo());

		String message = req.getReader().readLine();

		if (message != null && message.indexOf("message") != -1) {
			b.broadcast(message.substring("message=".length()));
		}
	}

	Broadcaster lookupBroadcaster(String pathInfo) {
		String[] decodedPath = pathInfo.split("/");
		Broadcaster b = BroadcasterFactory.getDefault().lookup(
				decodedPath[decodedPath.length - 1], true);
		return b;
	}
}
