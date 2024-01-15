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

package com.github.ghetolay.jwamp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ghetolay.jwamp.message.BadMessageFormException;
import com.github.ghetolay.jwamp.message.WampMessage;
import com.github.ghetolay.jwamp.message.WampWelcomeMessage;
import com.github.ghetolay.jwamp.utils.ResultListener;

public abstract class AbstractWampConnection implements WampConnection {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private String sessionId;

	protected ReconnectPolicy autoReconnect = ReconnectPolicy.YES;

	private ObjectMapper mapper;

	private boolean connected = false;

	private ResultListener<WampConnection> welcomeListener;

	private Collection<WampMessageHandler> messageHandlers;

	private boolean exclusiveHandler = true;

	public AbstractWampConnection(ObjectMapper mapper,
			Collection<WampMessageHandler> messageHandlers,
			ResultListener<WampConnection> wl) {
		if (mapper != null) {
			this.mapper = mapper;
		}
		else {
			this.mapper = new ObjectMapper();
		}

		this.welcomeListener = wl;

		if (messageHandlers != null) {
			this.messageHandlers = messageHandlers;
		}
		else {
			messageHandlers = new HashSet<WampMessageHandler>();
		}
	}

	public abstract void sendMessage(String data) throws IOException;

	public void onClose(int closeCode, String message) {
		for (WampMessageHandler h : messageHandlers) {
			h.onClose(sessionId, closeCode);
		}

		connected = false;
	}

	protected void reset() {
		connected = false;
	}

	public void newClientConnection() {
		newClientConnection(null);
	}

	public void newClientConnection(String sessionId) {
		try {
			sendWelcome(sessionId);

			initHandlers();

			connected = true;
		}
		catch (IOException e) {
			// TODO log
			if (log.isErrorEnabled()) {
				log.error("Unable to send Welcome Message");
			}
		}
	}

	private void initHandlers() {
		if (messageHandlers != null) {
			for (WampMessageHandler h : messageHandlers) {
				h.onConnected(this);
			}
		}
	}

	private void sendWelcome(String sessionId) throws IOException {
		if (sessionId == null || sessionId.isEmpty()) {
			this.sessionId = UUID.randomUUID().toString();
		}
		else {
			this.sessionId = sessionId;
		}

		if (log.isTraceEnabled()) {
			log.trace("Send welcome with sessionId : " + this.sessionId);
		}

		WampWelcomeMessage msg = new WampWelcomeMessage();
		msg.setImplementation(WampFactory.getImplementation());
		msg.setProtocolVersion(WampFactory.getProtocolVersion());
		msg.setSessionId(this.sessionId);

		sendMessage(msg);
	}

	// Need optimization
	@Override
	public void sendMessage(WampMessage msg) throws IOException {

		Object[] array = msg.toJSONArray();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			getObjectMapper().writeValue(baos, array);
			String jsonMsg = baos.toString("UTF-8");

			if (log.isDebugEnabled()) {
				log.debug("Sending Message " + jsonMsg);
			}

			sendMessage(jsonMsg);
		}
		catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onMessage(String data) {

		if (log.isDebugEnabled()) {
			log.debug("Receive Wamp Message " + data);
		}

		try {
			JsonParser parser = getObjectMapper().getJsonFactory().createJsonParser(data);

			if (parser.nextToken() != JsonToken.START_ARRAY) {
				throw new BadMessageFormException(
						"WampMessage must be a not null JSON array");
			}

			if (parser.nextToken() != JsonToken.VALUE_NUMBER_INT) {
				throw new BadMessageFormException("The first array element must be a int");
			}

			int messageType = parser.getIntValue();

			switch (messageType) {
			/*
			 * case WampMessage.PREFIX : handler.onPrefix(new WampPrefixMessage(array));
			 * return;
			 */
			case WampMessage.WELCOME:
				onWelcome(new WampWelcomeMessage(parser));
				return;
			}

			for (WampMessageHandler h : messageHandlers) {
				boolean result = h.onMessage(sessionId, messageType, parser);
				if (exclusiveHandler && result) {
					return;
				}
			}

			if (log.isErrorEnabled()) {
				log.error("Message Id not recognized : " + messageType);
			}

		}
		catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassCastException e) {
			// TODO
			e.printStackTrace();
		}
		catch (BadMessageFormException e) {
			// TODO
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void onWelcome(WampWelcomeMessage wampWelcomeMessage) {

		// onWelcome should only be called once
		if (!connected) {
			if (wampWelcomeMessage.getProtocolVersion() != WampFactory
					.getProtocolVersion() && log.isWarnEnabled()) {
				log.warn("server's Wamp protocol version ('"
						+ wampWelcomeMessage.getProtocolVersion()
						+ "') differs from this implementation version ('"
						+ WampFactory.getProtocolVersion() + "')\n"
						+ "Errors and weird behavior may occurs");
			}

			if (log.isTraceEnabled()) {
				log.trace("Server's Wamp Implementation : "
						+ wampWelcomeMessage.getImplementation());
			}

			sessionId = wampWelcomeMessage.getSessionId();

			initHandlers();

			welcomeListener.onResult(this);
			// save some memory since listener should be used only once
			welcomeListener = null;

			connected = true;
		}
		else if (log.isErrorEnabled()) {
			// TODO error log
			log.error("onWelcome called twice on the same connection !!");
		}

	}

	@Override
	public void setExclusiveHandler(boolean exclusive) {
		this.exclusiveHandler = exclusive;
	}

	@Override
	public boolean isExclusiveHandler() {
		return exclusiveHandler;
	}

	@Override
	public boolean setReconnectPolicy(ReconnectPolicy reconnect) {
		if (autoReconnect != ReconnectPolicy.IMPOSSIBLE) {
			autoReconnect = reconnect;
			return true;
		}
		return false;
	}

	@Override
	public ReconnectPolicy getReconnectPolicy() {
		return autoReconnect;
	}

	@Override
	public void addMessageHandler(WampMessageHandler handler) {
		messageHandlers.add(handler);

		if (connected) {
			handler.onConnected(this);
		}
	}

	@Override
	public boolean containsMessageHandler(WampMessageHandler handler) {
		return messageHandlers.contains(handler);
	}

	@Override
	public void removeMessageHandler(WampMessageHandler handler) {
		messageHandlers.remove(handler);
	}

	@Override
	public <T extends WampMessageHandler> T getMessageHandler(Class<T> handlerClass) {
		for (WampMessageHandler h : messageHandlers) {
			if (handlerClass.isInstance(h)) {
				return handlerClass.cast(h);
			}
		}
		return null;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	protected ObjectMapper getObjectMapper() {
		return mapper;
	}

	protected void setObjectMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	protected ResultListener<WampConnection> getWelcomeListener() {
		return welcomeListener;
	}

	protected void setWelcomeListener(ResultListener<WampConnection> listener) {
		this.welcomeListener = listener;
	}
}
