/**
 *Copyright [2012] [Ghetolay]
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.github.ghetolay.jwamp.jetty;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ghetolay.jwamp.AbstractWampConnection;
import com.github.ghetolay.jwamp.WampConnection;
import com.github.ghetolay.jwamp.WampMessageHandler;
import com.github.ghetolay.jwamp.utils.ResultListener;

public class JettyConnection extends AbstractWampConnection implements
		WebSocket.OnTextMessage {

	private URI uri;

	protected Connection connection;

	private boolean intentionallyClosed = false;

	public JettyConnection(URI uri, ObjectMapper mapper,
			Collection<WampMessageHandler> handlers, ResultListener<WampConnection> wr) {
		super(mapper, handlers, wr);
		if (uri != null) {
			this.uri = uri;
		}
		else {
			autoReconnect = ReconnectPolicy.IMPOSSIBLE;
		}
	}

	public void onOpen(Connection connection) {
		if (log.isTraceEnabled()) {
			log.trace("New connection opened");
		}
		this.connection = connection;
	}

	@Override
	public void onClose(int closeCode, String message) {
		if (connection != null && !intentionallyClosed
				&& autoReconnect == ReconnectPolicy.YES) {

			connection = null;
			if (reconnect()) {
				return;
			}
		}
		onClose();
		super.onClose(closeCode, message);
	}

	public void onClose() {

	}

	@Override
	public void close(int closeCode, String message) {
		intentionallyClosed = true;
		connection.close(closeCode, message);
	}

	@Override
	public void sendMessage(String data) throws IOException {
		connection.sendMessage(data);
	}

	protected boolean reconnect() {
		reset();
		try {
			// Give it some time
			Thread.sleep(1000);

			int i;
			for (i = 0; i < 5; i++) {
				try {
					System.out.println("RECONNECT");
					WampJettyFactory.getInstance().connect(uri, 10000, this);
					return true;
				}
				catch (Exception e) {
					if (log.isDebugEnabled()) {
						log.debug("Failed to reconnect to " + uri.toString() + " ["
								+ (i + 1) + "/5] : " + e.getMessage());
					}

					Thread.sleep(5000);
				}
			}
			if (i == 5 && log.isWarnEnabled()) {
				log.warn("Unable to reconnect to " + uri.toString());
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("Thread interrupted while trying to reconnect", e);
			}
		}

		return false;
	}
}
