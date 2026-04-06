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
package ch.rasc.openai4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import feign.Client;
import feign.Logger;
import feign.Logger.Level;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.http2client.Http2Client;
import feign.slf4j.Slf4jLogger;

@SuppressWarnings({ "hiding", "unused" })
public class Configuration {

	private final String apiKey;
	private final String organization;
	private final String baseUrl;
	private final Client client;
	private final Retryer retryer;
	private final Request.Options feignOptions;
	private final Logger logger;
	private final ErrorDecoder errorDecoder;
	private final RequestInterceptor additionalRequestInterceptor;
	private final Level logLevel;
	private final String azureEndpoint;
	private final String apiVersion;
	private final String azureDeployment;

	private Configuration(Builder builder) {
		if (builder.apiKey == null) {
			throw new IllegalArgumentException("apiKey must not be null");
		}

		this.apiKey = builder.apiKey;
		this.organization = builder.organization;

		this.baseUrl = Objects.requireNonNullElse(builder.baseUrl,
				"https://api.openai.com/v1");
		this.client = Objects.requireNonNullElse(builder.client, new Http2Client());
		this.retryer = Objects.requireNonNullElse(builder.retryer, new Retryer.Default(
				TimeUnit.SECONDS.toMillis(2), TimeUnit.SECONDS.toMillis(2), 3));
		this.feignOptions = Objects.requireNonNullElse(builder.feignOptions,
				new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true));
		this.logger = Objects.requireNonNullElse(builder.logger, new Slf4jLogger());
		this.errorDecoder = Objects.requireNonNullElse(builder.errorDecoder,
				new OpenAIErrorDecoder());
		this.additionalRequestInterceptor = builder.additionalRequestInterceptor;
		this.logLevel = Objects.requireNonNullElse(builder.logLevel, Level.NONE);
		this.azureEndpoint = builder.azureEndpoint;
		this.apiVersion = builder.apiVersion;
		this.azureDeployment = builder.azureDeployment;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String apiKey;
		private String organization;
		private String baseUrl;
		private Client client;
		private Retryer retryer;
		private Request.Options feignOptions;
		private Logger logger;
		private ErrorDecoder errorDecoder;
		private RequestInterceptor additionalRequestInterceptor;
		private Level logLevel;
		private String azureEndpoint;
		private String apiVersion;
		private String azureDeployment;

		private Builder() {
		}

		public Builder apiKey(String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		public Builder organization(String organization) {
			this.organization = organization;
			return this;
		}

		public Builder baseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		public Builder client(Client client) {
			this.client = client;
			return this;
		}

		public Builder retryer(Retryer retryer) {
			this.retryer = retryer;
			return this;
		}

		public Builder feignOptions(Request.Options feignOptions) {
			this.feignOptions = feignOptions;
			return this;
		}

		public Builder logger(Logger logger) {
			this.logger = logger;
			return this;
		}

		public Builder errorDecoder(ErrorDecoder errorDecoder) {
			this.errorDecoder = errorDecoder;
			return this;
		}

		public Builder additionalRequestInterceptor(
				RequestInterceptor additionalRequestInterceptor) {
			this.additionalRequestInterceptor = additionalRequestInterceptor;
			return this;
		}

		public Builder logLevel(Level logLevel) {
			this.logLevel = logLevel;
			return this;
		}

		/**
		 * The name of your Azure OpenAI Resource.
		 */
		public Builder azureEndpoint(String azureEndpoint) {
			this.azureEndpoint = azureEndpoint;
			return this;
		}

		public Builder apiVersion(String apiVersion) {
			this.apiVersion = apiVersion;
			return this;
		}

		/**
		 * The deployment name you chose when you deployed the model.
		 */
		public Builder azureDeployment(String azureDeployment) {
			this.azureDeployment = azureDeployment;
			return this;
		}

		public Configuration build() {
			return new Configuration(this);
		}
	}

	public String apiKey() {
		return this.apiKey;
	}

	public String organization() {
		return this.organization;
	}

	public String baseUrl() {
		return this.baseUrl;
	}

	public Client client() {
		return this.client;
	}

	public Retryer retryer() {
		return this.retryer;
	}

	public Request.Options feignOptions() {
		return this.feignOptions;
	}

	public Logger logger() {
		return this.logger;
	}

	public ErrorDecoder errorDecoder() {
		return this.errorDecoder;
	}

	public RequestInterceptor additionalRequestInterceptor() {
		return this.additionalRequestInterceptor;
	}

	public Level logLevel() {
		return this.logLevel;
	}

	public String azureEndpoint() {
		return this.azureEndpoint;
	}

	public String apiVersion() {
		return this.apiVersion;
	}

	public String azureDeployment() {
		return this.azureDeployment;
	}
}
