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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import ch.rasc.openai4j.assistants.AssistantsClient;
import ch.rasc.openai4j.audio.AudioClient;
import ch.rasc.openai4j.batch.BatchesClient;
import ch.rasc.openai4j.chatcompletions.ChatCompletionsClient;
import ch.rasc.openai4j.embeddings.EmbeddingsClient;
import ch.rasc.openai4j.files.FilesClient;
import ch.rasc.openai4j.finetuningjobs.FineTuningJobsClient;
import ch.rasc.openai4j.images.ImagesClient;
import ch.rasc.openai4j.models.ModelsClient;
import ch.rasc.openai4j.moderations.ModerationsClient;
import ch.rasc.openai4j.threads.ThreadsClient;
import ch.rasc.openai4j.threads.messages.ThreadsMessagesClient;
import ch.rasc.openai4j.threads.runs.ThreadsRunsClient;
import ch.rasc.openai4j.threads.runs.steps.ThreadsRunsStepsClient;
import ch.rasc.openai4j.uploads.UploadsClient;
import ch.rasc.openai4j.vectorstores.VectorStoresClient;
import ch.rasc.openai4j.vectorstores.filebatches.VectorStoresFileBatchesClient;
import ch.rasc.openai4j.vectorstores.files.VectorStoresFilesClient;
import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class OpenAIClient {

	public AudioClient audio;
	public ChatCompletionsClient chatCompletions;
	public EmbeddingsClient embeddings;
	public FilesClient files;
	public FineTuningJobsClient fineTuningJobs;
	public ImagesClient images;
	public ModelsClient models;
	public ModerationsClient moderations;
	public ThreadsClient threads;
	public ThreadsRunsClient threadsRuns;
	public ThreadsRunsStepsClient threadsRunsSteps;
	public ThreadsMessagesClient threadsMessages;
	public AssistantsClient assistants;
	public BatchesClient batches;
	public VectorStoresClient vectorStores;
	public VectorStoresFilesClient vectorStoresFiles;
	public VectorStoresFileBatchesClient vectorStoresFileBatches;
	public UploadsClient uploads;

	public static OpenAIClient create(
			Function<Configuration.Builder, Configuration.Builder> fn) {
		return create(fn.apply(Configuration.builder()).build());
	}

	public static OpenAIClient create(Configuration configuration) {

		OpenAIClient client = new OpenAIClient();
		JacksonDecoder jsonDecoder = new JacksonDecoder();
		JacksonEncoder jsonEncoder = new JacksonEncoder();
		FormEncoder formAndJsonEncoder = new FormEncoder(jsonEncoder);

		List<RequestInterceptor> interceptors = new ArrayList<>();

		if (configuration.additionalRequestInterceptor() != null) {
			interceptors.add(configuration.additionalRequestInterceptor());
		}

		if (configuration.organization() != null
				&& !configuration.organization().isBlank()) {
			interceptors.add(new OpenAIOrganizationRequestInterceptor(
					configuration.organization()));
		}

		if (configuration.apiVersion() != null && !configuration.apiVersion().isBlank()) {
			interceptors
					.add(new ApiVersionRequestInterceptor(configuration.apiVersion()));
		}

		String baseUrl;
		if (configuration.azureEndpoint() != null
				&& !configuration.azureEndpoint().isBlank()) {

			String azureEndpoint = configuration.azureEndpoint();
			if (azureEndpoint.endsWith("/")) {
				azureEndpoint = azureEndpoint.substring(0, azureEndpoint.length() - 1);
			}

			if (configuration.azureDeployment() != null
					&& !configuration.azureDeployment().isBlank()) {
				baseUrl = azureEndpoint + "/openai/deployments/"
						+ configuration.azureDeployment();
			}
			else {
				baseUrl = azureEndpoint + "/openai";
			}
			interceptors.add(new AzureApiKeyRequestInterceptor(configuration.apiKey()));
		}
		else {
			baseUrl = configuration.baseUrl();
			interceptors.add(new AuthorizationRequestInterceptor(configuration.apiKey()));
		}

		client.chatCompletions = jsonClientBuilder(configuration, jsonDecoder,
				jsonEncoder, interceptors).target(ChatCompletionsClient.class, baseUrl);

		client.embeddings = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder,
				interceptors).target(EmbeddingsClient.class, baseUrl);

		client.files = formAndJsonClientBuilder(configuration, jsonDecoder,
				formAndJsonEncoder, interceptors).target(FilesClient.class, baseUrl);

		client.fineTuningJobs = formAndJsonClientBuilder(configuration, jsonDecoder,
				formAndJsonEncoder, interceptors).target(FineTuningJobsClient.class,
						baseUrl);

		client.audio = formAndJsonClientBuilder(configuration, jsonDecoder,
				formAndJsonEncoder, interceptors).target(AudioClient.class, baseUrl);

		client.images = formAndJsonClientBuilder(configuration, jsonDecoder,
				formAndJsonEncoder, interceptors).target(ImagesClient.class, baseUrl);

		client.moderations = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder,
				interceptors).target(ModerationsClient.class, baseUrl);

		client.models = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder,
				interceptors).target(ModelsClient.class, baseUrl);

		client.uploads = formAndJsonClientBuilder(configuration, jsonDecoder,
				formAndJsonEncoder, interceptors).target(UploadsClient.class, baseUrl);

		var betaInterceptors = new ArrayList<>(interceptors);
		betaInterceptors.add(new OpenAIBetaRequestInterceptor());
		client.threads = betaClientBuilder(configuration, jsonDecoder, jsonEncoder,
				betaInterceptors).target(ThreadsClient.class, baseUrl);
		client.threadsRuns = betaClientBuilder(configuration, jsonDecoder, jsonEncoder,
				betaInterceptors).target(ThreadsRunsClient.class, baseUrl);
		client.threadsRunsSteps = betaClientBuilder(configuration, jsonDecoder,
				jsonEncoder, betaInterceptors).target(ThreadsRunsStepsClient.class,
						baseUrl);
		client.threadsMessages = betaClientBuilder(configuration, jsonDecoder,
				jsonEncoder, betaInterceptors).target(ThreadsMessagesClient.class,
						baseUrl);
		client.assistants = betaClientBuilder(configuration, jsonDecoder, jsonEncoder,
				betaInterceptors).target(AssistantsClient.class, baseUrl);
		client.batches = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder,
				betaInterceptors).target(BatchesClient.class, baseUrl);
		client.vectorStores = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder,
				betaInterceptors).target(VectorStoresClient.class, baseUrl);
		client.vectorStoresFiles = jsonClientBuilder(configuration, jsonDecoder,
				jsonEncoder, betaInterceptors).target(VectorStoresFilesClient.class,
						baseUrl);
		client.vectorStoresFileBatches = jsonClientBuilder(configuration, jsonDecoder,
				jsonEncoder, betaInterceptors).target(VectorStoresFileBatchesClient.class,
						baseUrl);

		return client;
	}

	private static Builder formAndJsonClientBuilder(Configuration configuration,
			JacksonDecoder jsonDecoder, FormEncoder formAndJsonEncoder,
			List<RequestInterceptor> interceptors) {
		return Feign.builder().client(configuration.client())
				.errorDecoder(configuration.errorDecoder())
				.retryer(configuration.retryer()).options(configuration.feignOptions())
				.logger(configuration.logger()).logLevel(configuration.logLevel())
				.decoder(jsonDecoder).encoder(formAndJsonEncoder)
				.requestInterceptors(interceptors);
	}

	private static Builder jsonClientBuilder(Configuration configuration,
			JacksonDecoder jsonDecoder, JacksonEncoder jsonEncoder,
			List<RequestInterceptor> interceptors) {
		return Feign.builder().client(configuration.client())
				.errorDecoder(configuration.errorDecoder())
				.retryer(configuration.retryer()).options(configuration.feignOptions())
				.logger(configuration.logger()).logLevel(configuration.logLevel())
				.decoder(jsonDecoder).encoder(jsonEncoder)
				.requestInterceptors(interceptors);
	}

	private static Builder betaClientBuilder(Configuration configuration,
			JacksonDecoder jsonDecoder, JacksonEncoder jsonEncoder,
			ArrayList<RequestInterceptor> betaInterceptors) {
		return Feign.builder().client(configuration.client())
				.errorDecoder(configuration.errorDecoder())
				.retryer(configuration.retryer()).options(configuration.feignOptions())
				.logger(configuration.logger()).logLevel(configuration.logLevel())
				.decoder(jsonDecoder).encoder(jsonEncoder)
				.requestInterceptors(betaInterceptors);
	}
}
