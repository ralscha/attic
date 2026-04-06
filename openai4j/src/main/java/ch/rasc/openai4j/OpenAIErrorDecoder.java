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

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

public class OpenAIErrorDecoder implements ErrorDecoder {

	private final ObjectMapper objectMapper;

	private final ErrorDecoder.Default defaultErrorDecoder;

	public OpenAIErrorDecoder() {
		this.objectMapper = new ObjectMapper();
		this.defaultErrorDecoder = new ErrorDecoder.Default();
	}

	@Override
	public Exception decode(String methodKey, Response response) {

		try (var body = response.body(); var is = body.asInputStream()) {
			Map<String, Object> error = this.objectMapper.readValue(is, Map.class);
			if (error.containsKey("error") && error.get("error") instanceof Map) {
				Map<String, Object> errorMap = (Map<String, Object>) error.get("error");
				String message = (String) errorMap.get("message");
				String type = (String) errorMap.get("type");
				String param = (String) errorMap.get("param");
				String code = (String) errorMap.get("code");

				return new OpenAIApiException(message, type, param, code);
			}
		}
		catch (IOException e) {
			// do nothing fall back to default error decoder
		}

		return this.defaultErrorDecoder.decode(methodKey, response);
	}

}
