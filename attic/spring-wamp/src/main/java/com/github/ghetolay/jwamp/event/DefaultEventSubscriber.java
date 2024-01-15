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
package com.github.ghetolay.jwamp.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.fasterxml.jackson.core.JsonParser;
import com.github.ghetolay.jwamp.WampConnection;
import com.github.ghetolay.jwamp.message.BadMessageFormException;
import com.github.ghetolay.jwamp.message.WampEventMessage;
import com.github.ghetolay.jwamp.message.WampMessage;
import com.github.ghetolay.jwamp.message.WampPublishMessage;
import com.github.ghetolay.jwamp.message.WampSubscribeMessage;
import com.github.ghetolay.jwamp.utils.ResultListener;

public class DefaultEventSubscriber implements WampEventSubscriber {

	private WampConnection conn;

	private final Set<String> topics;

	private final Map<String, ResultListener<Object>> resultListeners = new HashMap<String, ResultListener<Object>>();

	private ResultListener<EventResult> globalListener;

	public DefaultEventSubscriber() {
		topics = new CopyOnWriteArraySet<String>();
	}

	public DefaultEventSubscriber(Collection<String> topics,
			ResultListener<EventResult> globalListener) {
		this.topics = new CopyOnWriteArraySet<String>(topics);
		this.globalListener = globalListener;
	}

	@Override
	public void onConnected(WampConnection connection) {
		conn = connection;
		if (!topics.isEmpty()) {

			Set<String> initTopics = new HashSet<String>(topics);
			topics.clear();

			for (String s : initTopics) {
				try {
					subscribe(s);
				}
				catch (IOException e) {
					// TODO log
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onClose(String sessionId, int closeCode) {
	}

	@Override
	public boolean onMessage(String sessionId, int messageType, JsonParser parser)
			throws BadMessageFormException {
		if (messageType == WampMessage.EVENT) {
			onEvent(new WampEventMessage(parser));
			return true;
		}
		return false;
	}

	private void onEvent(WampEventMessage msg) {
		if (resultListeners.containsKey(msg.getTopicId())) {
			resultListeners.get(msg.getTopicId()).onResult(msg.getEvent());
		}
		else if (globalListener != null) {
			globalListener.onResult(new EventResult(msg.getTopicId(), msg.getEvent()));
		}

	}

	@Override
	public void subscribe(String topicId) throws IOException {
		if (!topics.contains(topicId)) {
			conn.sendMessage(new WampSubscribeMessage(WampMessage.SUBSCRIBE, topicId));

			topics.add(topicId);
		}
	}

	@Override
	public void subscribe(String topicId, ResultListener<Object> resultListener)
			throws IOException {
		subscribe(topicId);

		if (resultListener != null) {
			resultListeners.put(topicId, resultListener);
		}
	}

	@Override
	public void unsubscribe(String topicId) throws IOException {
		if (topics.contains(topicId)) {
			conn.sendMessage(new WampSubscribeMessage(WampMessage.UNSUBSCRIBE, topicId));

			topics.remove(topicId);
		}
		if (resultListeners.containsKey(topicId)) {
			resultListeners.remove(topicId);
		}
	}

	@Override
	public void publish(String topicId, Object event) throws IOException {
		publish(topicId, event, true);
	}

	@Override
	public void publish(String topicId, Object event, boolean excludeMe)
			throws IOException {
		publish(topicId, event, true, null, null);
	}

	@Override
	public void publish(String topicId, Object event, boolean excludeMe,
			List<String> eligible) throws IOException {
		if (eligible != null) {
			List<String> excludes = new ArrayList<String>();
			if (excludeMe) {
				excludes.add(conn.getSessionId());
			}

			publish(topicId, event, false, excludes, eligible);
		}
		else {
			publish(topicId, event, excludeMe, null, null);
		}
	}

	@Override
	public void publish(String topicId, Object event, List<String> exclude,
			List<String> eligible) throws IOException {
		if (exclude.size() == 1 && exclude.get(0).equals(conn.getSessionId())
				&& eligible == null) {
			publish(topicId, event, true, null, null);
		}
		else {
			publish(topicId, event, false, exclude, eligible);
		}
	}

	private void publish(String topicId, Object event, boolean excludeMe,
			List<String> exclude, List<String> eligible) throws IOException {

		if (!topics.contains(topicId)) {
			subscribe(topicId);
		}

		WampPublishMessage msg = new WampPublishMessage();
		msg.setTopicId(topicId);
		msg.setEvent(event);

		if (excludeMe) {
			msg.setExcludeMe(true);
		}
		else if (exclude != null || eligible != null) {
			msg.setExclude(exclude != null ? exclude : new ArrayList<String>());
			msg.setEligible(eligible != null ? exclude : new ArrayList<String>());
		}

		conn.sendMessage(msg);
	}

	@Override
	public ResultListener<EventResult> getGlobalListener() {
		return globalListener;
	}

	@Override
	public void setGlobalListener(ResultListener<EventResult> listener) {
		globalListener = listener;
	}

	public class EventResult {
		private final String topicId;

		private final Object event;

		private EventResult(String topicId, Object event) {
			this.topicId = topicId;
			this.event = event;
		}

		public String getTopicId() {
			return topicId;
		}

		public Object getEvent() {
			return event;
		}
	}
}
