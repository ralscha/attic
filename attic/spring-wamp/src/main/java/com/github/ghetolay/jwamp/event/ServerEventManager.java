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

import java.util.HashMap;
import java.util.Map;

import com.github.ghetolay.jwamp.WampConnection;
import com.github.ghetolay.jwamp.utils.ActionMapping;

public class ServerEventManager extends AbstractEventManager {

	private final Map<String, WampConnection> connections = new HashMap<String, WampConnection>();

	public ServerEventManager(ActionMapping<EventAction> am) {
		super(am);
	}

	@Override
	public void onConnected(WampConnection connection) {
		connections.put(connection.getSessionId(), connection);
	}

	@Override
	protected WampConnection getConnection(String sessionId) {
		return connections.get(sessionId);
	}

	@Override
	public void onClose(String sessionId, int closeCode) {
		super.onClose(sessionId, closeCode);

		connections.remove(sessionId);
	}
}
