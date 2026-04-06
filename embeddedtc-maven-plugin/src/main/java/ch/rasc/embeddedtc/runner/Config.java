/**
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.embeddedtc.runner;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.IntrospectionUtils;

public class Config {

	private String extractDirectory = "tc";

	private Shutdown shutdown;

	private boolean silent = false;

	private boolean useShutdownHook = false;

	private String jvmRoute;

	private List<Map<String, Object>> valves = Collections.emptyList();

	private Map<String, Object> valve;

	private Set<String> listeners = new HashSet<>(
			Arrays.asList("org.apache.catalina.core.AprLifecycleListener"));

	private Map<String, Object> systemProperties = Collections.emptyMap();

	private Map<String, Object> connector;

	private List<Map<String, Object>> connectors = Collections.emptyList();

	private Context context;

	private List<Context> contexts = Collections.emptyList();

	private Path myJarDirectory;

	public String getJvmRoute() {
		return this.jvmRoute;
	}

	public void setJvmRoute(String jvmRoute) {
		this.jvmRoute = jvmRoute;
	}

	public boolean isSilent() {
		return this.silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	public boolean isUseShutdownHook() {
		return this.useShutdownHook;
	}

	public void setUseShutdownHook(boolean useShutdownHook) {
		this.useShutdownHook = useShutdownHook;
	}

	public Set<String> getListeners() {
		return this.listeners;
	}

	public void setListeners(Set<String> listeners) {
		this.listeners = listeners;
	}

	public void setConnectors(List<Map<String, Object>> connectors) {
		this.connectors = connectors;
	}

	public void setConnector(Map<String, Object> connector) {
		this.connector = connector;
	}

	public void setValves(List<Map<String, Object>> valves) {
		this.valves = valves;
	}

	public void setValve(Map<String, Object> valve) {
		this.valve = valve;
	}

	public List<Context> getContexts() {
		if (this.context != null) {
			if (this.contexts.isEmpty()) {
				return Collections.singletonList(this.context);
			}

			List<Context> combinedContexts = new ArrayList<>(this.contexts);
			combinedContexts.add(this.context);
			return combinedContexts;
		}
		return this.contexts;
	}

	public void setContexts(List<Context> contexts) {
		this.contexts = contexts;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Map<String, Object> getSystemProperties() {
		return this.systemProperties;
	}

	public void setSystemProperties(Map<String, Object> systemProperties) {
		this.systemProperties = systemProperties;
	}

	public String getExtractDirectory() {
		return this.extractDirectory;
	}

	public void setExtractDirectory(String extractDirectory) {
		this.extractDirectory = extractDirectory;
	}

	public Shutdown getShutdown() {
		return this.shutdown;
	}

	public void setShutdown(Shutdown shutdown) {
		this.shutdown = shutdown;
	}

	public Path getMyJarDirectory() {
		return this.myJarDirectory;
	}

	public void setMyJarDirectory(Path myJarDirectory) {
		this.myJarDirectory = myJarDirectory;
	}

	private static final String CONNECTOR_PROTOCOL = "protocol";

	private static final String CONNECTOR_PORT = "port";

	private static final String CONNECTOR_URIENCODING = "URIEncoding";

	public List<Connector> createConnectorObjects() throws Exception {
		List<Connector> conObjects = new ArrayList<>();

		if (this.connector != null) {
			if (this.connectors.isEmpty()) {
				setConnectors(Collections.singletonList(this.connector));
			}
			else {
				this.connectors.add(this.connector);
			}
		}

		for (Map<String, Object> con : this.connectors) {

			replaceVariables(con);

			Object protocol = con.get(CONNECTOR_PROTOCOL);
			if (protocol == null) {
				protocol = "HTTP/1.1";
			}
			Connector tcConnector = new Connector(protocol.toString());

			if (!con.containsKey(CONNECTOR_PORT)) {
				con.put(CONNECTOR_PORT, 8080);
			}

			if (!con.containsKey(CONNECTOR_URIENCODING)) {
				con.put(CONNECTOR_URIENCODING, "UTF-8");
			}

			for (Map.Entry<String, Object> entry : con.entrySet()) {
				if (!entry.getKey().equals(CONNECTOR_PROTOCOL)) {
					IntrospectionUtils.setProperty(tcConnector, entry.getKey(),
							entry.getValue().toString());
				}
			}

			conObjects.add(tcConnector);
		}

		return conObjects;
	}

	private static void replaceVariables(Map<String, Object> con) {
		for (Map.Entry<String, Object> entry : con.entrySet()) {
			String value = entry.getValue().toString().trim();
			if (value.startsWith("${") && value.endsWith("}")) {
				String var = value.substring(2, value.length() - 1);
				String systemProperty = System.getProperty(var);
				if (systemProperty != null) {
					entry.setValue(systemProperty);
				}
				else {
					String environmentVariable = System.getenv(var);
					if (environmentVariable != null) {
						entry.setValue(environmentVariable);
					}
				}
			}
		}

	}

	private static final String VALVE_CLASSNAME = "className";

	public List<Valve> createValveObjects() {
		List<Valve> valveObjects = new ArrayList<>();

		if (this.valve != null) {
			if (this.valves.isEmpty()) {
				setValves(Collections.singletonList(this.valve));
			}
			else {
				this.valves.add(this.valve);
			}
		}

		for (Map<String, Object> v : this.valves) {
			String className = (String) v.get(VALVE_CLASSNAME);
			if (className == null) {
				Runner.getLogger()
						.warn("Missing className option in valve configuration");
				continue;
			}

			Class<Valve> valveClass = null;

			try {
				valveClass = (Class<Valve>) Class.forName(className);
			}
			catch (ClassNotFoundException e) {
				Runner.getLogger().warn("Valve className '" + className + "' not found");
				continue;
			}

			Valve valveObject;
			try {
				valveObject = valveClass.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e) {
				Runner.getLogger().warn("Instantiation of class '" + className
						+ "' failed: " + e.getMessage());
				continue;
			}

			for (Map.Entry<String, Object> entry : v.entrySet()) {
				if (!entry.getKey().equals(VALVE_CLASSNAME)) {
					IntrospectionUtils.setProperty(valveObject, entry.getKey(),
							entry.getValue().toString());
				}
			}

			valveObjects.add(valveObject);
		}

		return valveObjects;
	}

	public boolean isEnableNaming() {
		for (Context ctx : getContexts()) {
			if (ctx.hasEnvironmentsOrResources() || ctx.getContextFile() != null) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "Config [jvmRoute=" + this.jvmRoute + ", silent=" + this.silent
				+ ", listeners=" + this.listeners + ", systemProperties="
				+ this.systemProperties + ", connectors=" + this.connectors + ", context="
				+ this.context + ", contexts=" + this.contexts + "]";
	}

}
