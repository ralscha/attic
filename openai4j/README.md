# OpenAI now provides an official Java SDK: https://github.com/openai/openai-java


# openai4j

Unofficial, community-maintained Java implementation of the OpenAI API:

https://platform.openai.com/docs/api-reference

openai4j is a Java library that implements most [documented endpoints](https://platform.openai.com/docs/api-reference) as of 16. April 2024, 
including the endpoints that are only available in beta.  
Legacy, deprecated and streaming endpoints are not implemented.

## Installation

```xml
<dependency>
  <groupId>ch.rasc</groupId>
  <artifactId>openai4j</artifactId>
  <version>1.3.6</version>
</dependency>
```


## Usage

### Chat completions
```java
  String apiKey = ... // read OpenAI api key from environment variable
  var client = OpenAIClient.create(c -> c.apiKey(apiKey));

  var response = client.chatCompletions.create(r -> r
		.addMessages(SystemMessage.of("You are a helpful assistant"),
				UserMessage.of("What is the capital of Spain?"))
		.model("gpt-4o"));
  String response = response.choices().get(0).message().content();
```

### Function calling with Java code

```java
public class ChatCompletionsFunctionExample {
	private final static ObjectMapper om = new ObjectMapper();

	static class Location {
		@JsonProperty(required = true)
		@JsonPropertyDescription("Latitude of the location. Geographical WGS84 coordinates")
		public float latitude;

		@JsonProperty(required = true)
		@JsonPropertyDescription("Longitude of the location. Geographical WGS84 coordinates")
		public float longitude;
	}

	static class TemperatureFetcher {

		public Float fetchTemperature(Location location) {
			System.out.println("calling fetchTemperature");
			try (var client = HttpClient.newHttpClient()) {
				var request = HttpRequest.newBuilder()
						.uri(URI.create("https://api.open-meteo.com/v1/metno?latitude="
								+ location.latitude + "&longitude=" + location.longitude
								+ "&current=temperature_2m"))
						.build();
				var response = client.send(request,
						java.net.http.HttpResponse.BodyHandlers.ofString());

				var body = response.body();
				System.out.println(body);
				var jsonNode = om.readTree(body);
				var current = jsonNode.get("current");
				var temperature = current.get("temperature_2m");
				return temperature.floatValue();
			}
			catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}

		}

	}

	public static void main(String[] args) throws JsonProcessingException {

		String apiKey = ... // read OpenAI api key from environment variable
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		TemperatureFetcher fetcher = new TemperatureFetcher();
		String functionName = "get_temperature";
		JavaFunction<Location, Float> getWeather = JavaFunction.of(functionName,
				"Get the current temperature of a location", Location.class,
				fetcher::fetchTemperature);

		var service = new ChatCompletionsService(client.chatCompletions, om);

		var response = service.createJavaFunctions(r -> r.addMessages(UserMessage.of(
				"What are the current temperatures in Oslo, Norway and Helsinki, Finland?"))
				.model("gpt-4o").javaFunctions(List.of(getWeather)));
		var choice = response.choices().get(0);
		System.out.println(choice.message().content());

	}
}
```


### Asisstant

```java
  String apiKey = ... // read OpenAI api key from environment variable
  var client = OpenAIClient.create(c -> c.apiKey(apiKey));

  Assistant assistant = client.assistants.create(c -> c.name("Math Tutor").instructions(
      "You are a personal math tutor. Write and run code to answer math questions.")
      .addTools(CodeTool.of()).model("gpt-4o"));
  }

  var thread = client.threads.create();

  var message = client.threadsMessages.create(thread.id(), c -> c.content(
        "I need to solve the equation `3x + 11 = 14`. Can you help me?"));

  var run = client.threadsRuns.create(thread.id(),
      c -> c.assistantId(assistant.id()).instructions(
          "Please address the user as Jane Doe. The user has a premium account."));

  client.threadsRuns.waitForProcessing(run, 30, TimeUnit.SECONDS, 2, TimeUnit.MINUTES);

  var messages = client.threadsMessages.list(thread.id(), p -> p.before(message.id()));
  for (var msg : messages.data()) {
     var content = msg.content().get(0);
     if (content instanceof MessageContentText text) {
       System.out.println(text.text().value());
     }
  }
```

### Azure

```java
  String apiKey = ... // read OpenAI api key from environment variable
  String azureEndpoint = ... // "https://myresource.openai.azure.com/"
  var azureClient = OpenAIClient
     .create(Configuration.builder()
     .apiVersion("2024-02-01")
     .apiKey(apiKey)
     .azureDeployment("gpt-35-turbo")
     .azureEndpoint(azureEndpoint)
     .build());
```     

Check out the [openai4j-examples](https://github.com/ralscha/openai4j-examples) repository for more examples.

## Changelog

### 1.3.6 - December 3, 2024
  * Add support for Predicted Outputs
  * Support for gpt-4o-audio-preview model for chat completions, which supports both audio inputs and outputs.

### 1.3.5 - October 2, 2024
  * Add audioTokens and cachedTokens to the usage object of ChatCompletionResponse
  * Add store and metadata field to ChatCompletionRequest

### 1.3.4 - September 14, 2024
  * Add completion_tokens_details and reasoning_tokens to usage
  * Including file search results used by the file search tool, and customizing ranking behavior.
 
### 1.3.3 - August 14, 2024
  * Added support for Structured Outputs

### 1.3.2 - July 20, 2024
  * Added support for Uploads API

### 1.3.1 - June 12, 2024
  * Parallel function calling can be disabled in Chat Completions and the Assistants API by passing parallel_tool_calls=false.
  * Added support for file search customizations.

### 1.3.0 - May 13, 2024
  * Support for Assistants API v2

### 1.2.0 - April 16, 2024
  * Added support for the Batch API
  * Added support for seed in the fine-tuning API
  * Added support for checkpoints in the fine-tuning API
  * Added support for adding Messages when creating a Run in the Assistants API

### 1.1.4 - April 7, 2024
  * Added support for temperature and assistant message creation in the Assistants API

### 1.1.3 - February 10, 2024
  * Added timestamp_granularities parameter to the Audio API

### 1.1.2 - January 26, 2024
  * Added dimensions parameter to the Embeddings API

### 1.1.1 - January 3, 2024
  * Added additional_instructions parameter to run creation in the Assistants API
  * Added logprobs and top_logprobs parameters to the Chat Completions API
  * Changed function parameters argument on a tool call to be optional.

### 1.1.0 - November 30, 2023
  * Refactor all the builders
  * Add ChatCompletionsService for calling Java functions and returning Java models

### 1.0.4 - November 14, 2023
  * More JavaDoc

### 1.0.3 - November 13, 2023
  * Add `name` property to Chat Completions SystemMessage and UserMessage
  * More JavaDoc
  * Replace arrays with List
  
### 1.0.2 - November 12, 2023
  * Bugfix: SubmitToolOutputs not properly JSON decoded
  * Bugfix: waitForProcessing in ThreadsRuns must return the last ThreadRun object

### 1.0.1 - November 12, 2023
  * Bugfix: ChatCompletions UserMessage for GPT Vision

### 1.0.0 - November 11, 2023
  * Initial release



## License
Code released under [the Apache license](http://www.apache.org/licenses/).
