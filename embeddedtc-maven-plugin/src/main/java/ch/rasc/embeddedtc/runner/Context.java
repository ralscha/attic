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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.tomcat.util.IntrospectionUtils;

public class Context {
	private String embeddedWar;

	private String externalWar;

	private String contextPath;

	private String contextFile;

	private boolean sessionPersistence = false;

	private List<Map<String, Object>> resources = Collections.emptyList();

	private List<ContextEnvironment> environments = Collections.emptyList();

	private List<ApplicationParameter> parameters = Collections.emptyList();

	private Map<String, Object> resource;

	private ContextEnvironment environment;

	private ApplicationParameter parameter;

	public String getEmbeddedWar() {
		return this.embeddedWar;
	}

	public void setEmbeddedWar(String embeddedWar) {
		this.embeddedWar = embeddedWar;
	}

	public String getExternalWar() {
		return this.externalWar;
	}

	public void setExternalWar(String externalWar) {
		this.externalWar = externalWar;
	}

	public String getContextPath() {
		return this.contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void setResources(List<Map<String, Object>> resources) {
		this.resources = resources;
	}

	public List<ContextEnvironment> getEnvironments() {

		if (this.environment != null) {
			if (this.environments.isEmpty()) {
				return Collections.singletonList(this.environment);
			}

			List<ContextEnvironment> combinedEnvironments = new ArrayList<>(
					this.environments);
			combinedEnvironments.add(this.environment);
			return combinedEnvironments;
		}

		return this.environments;
	}

	public void setEnvironments(List<ContextEnvironment> environments) {
		this.environments = environments;
	}

	public boolean isSessionPersistence() {
		return this.sessionPersistence;
	}

	public void setSessionPersistence(boolean sessionPersistence) {
		this.sessionPersistence = sessionPersistence;
	}

	public String getContextFile() {
		return this.contextFile;
	}

	public void setContextFile(String contextFile) {
		this.contextFile = contextFile;
	}

	public List<ApplicationParameter> getParameters() {
		if (this.parameter != null) {
			if (this.parameters.isEmpty()) {
				return Collections.singletonList(this.parameter);
			}

			List<ApplicationParameter> combinedParameters = new ArrayList<>(
					this.parameters);
			combinedParameters.add(this.parameter);
			return combinedParameters;
		}

		return this.parameters;
	}

	public void setParameters(List<ApplicationParameter> parameters) {
		this.parameters = parameters;
	}

	public void setResource(Map<String, Object> resource) {
		this.resource = resource;
	}

	public void setEnvironment(ContextEnvironment environment) {
		this.environment = environment;
	}

	public void setParameter(ApplicationParameter parameter) {
		this.parameter = parameter;
	}

	public boolean hasEnvironmentsOrResources() {
		return !this.environments.isEmpty() || !this.resources.isEmpty()
				|| this.environment != null || this.resource != null;
	}

	public List<ContextResource> createContextResourceObjects() {
		List<ContextResource> crObjects = new ArrayList<>();

		if (this.resource != null) {
			if (this.resources.isEmpty()) {
				setResources(Collections.singletonList(this.resource));
			}
			else {
				this.resources.add(this.resource);
			}
		}

		for (Map<String, Object> res : this.resources) {
			ContextResource contextResource = new ContextResource();

			for (Map.Entry<String, Object> entry : res.entrySet()) {
				IntrospectionUtils.setProperty(contextResource, entry.getKey(),
						entry.getValue().toString());
			}

			crObjects.add(contextResource);
		}

		return crObjects;
	}

	public void decryptPasswords(String password) {
		if (this.parameter != null) {
			this.parameter.setValue(
					ObfuscateUtil.toPlaintext(this.parameter.getValue(), password));
		}

		if (this.resource != null) {
			Map<String, Object> newResource = new HashMap<>();
			for (Map.Entry<String, Object> entry : this.resource.entrySet()) {
				if (entry.getValue() instanceof String) {
					newResource.put(entry.getKey(), ObfuscateUtil
							.toPlaintext((String) entry.getValue(), password));
				}
				else {
					newResource.put(entry.getKey(), entry.getValue());
				}
			}
			this.resource = newResource;
		}

		if (this.environment != null) {
			this.environment.setValue(
					ObfuscateUtil.toPlaintext(this.environment.getValue(), password));
		}

		List<Map<String, Object>> newResources = new ArrayList<>();
		for (Map<String, Object> res : this.resources) {
			Map<String, Object> newResource = new HashMap<>();
			for (Map.Entry<String, Object> entry : res.entrySet()) {
				if (entry.getValue() instanceof String) {
					newResource.put(entry.getKey(), ObfuscateUtil
							.toPlaintext((String) entry.getValue(), password));
				}
				else {
					newResource.put(entry.getKey(), entry.getValue());
				}
			}
			newResources.add(newResource);
		}
		this.resources = newResources;

		for (ContextEnvironment ce : this.environments) {
			ce.setValue(ObfuscateUtil.toPlaintext(ce.getValue(), password));
		}

		for (ApplicationParameter ap : this.parameters) {
			ap.setValue(ObfuscateUtil.toPlaintext(ap.getValue(), password));
		}

	}

	@Override
	public String toString() {
		return "Context [embeddedWar=" + this.embeddedWar + ", externalWar="
				+ this.externalWar + ", contextPath=" + this.contextPath
				+ ", contextFile=" + this.contextFile + ", sessionPersistence="
				+ this.sessionPersistence + ", resources=" + this.resources
				+ ", environments=" + this.environments + ", parameters="
				+ this.parameters + ", resource=" + this.resource + ", environment="
				+ this.environment + ", parameter=" + this.parameter + "]";
	}

}