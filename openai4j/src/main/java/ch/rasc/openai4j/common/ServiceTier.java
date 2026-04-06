/*
 * Copyright the original author or authors.
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
package ch.rasc.openai4j.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the latency tier to use for processing the request. This parameter is
 * relevant for customers subscribed to the scale tier service:
 */
public class ServiceTier {
	private final String value;

	@JsonCreator
	ServiceTier(String value) {
		this.value = value;
	}

	/**
	 * If set to 'default', the request will be processed using the default service tier
	 * with a lower uptime SLA and no latency guarentee.
	 */
	public static ServiceTier defaultTier() {
		return new ServiceTier("default");
	}

	/**
	 * If set to 'auto', the system will utilize scale tier credits until they are
	 * exhausted.
	 * <p>
	 * When not set, the default behavior is 'auto'.
	 */
	public static ServiceTier auto() {
		return new ServiceTier("auto");
	}

	@JsonValue
	public String value() {
		return this.value;
	}

}
