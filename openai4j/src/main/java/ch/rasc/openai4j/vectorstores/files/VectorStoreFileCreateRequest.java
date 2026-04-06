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
package ch.rasc.openai4j.vectorstores.files;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.vectorstores.ChunkingStrategy;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VectorStoreFileCreateRequest {
	@JsonProperty("file_id")
	private final String fileId;

	@JsonProperty("chunking_strategy")
	private final ChunkingStrategy chunkingStrategy;

	private VectorStoreFileCreateRequest(String fileId,
			ChunkingStrategy chunkingStrategy) {
		this.fileId = fileId;
		this.chunkingStrategy = chunkingStrategy;
	}

	public static VectorStoreFileCreateRequest of(String fileId) {
		return new VectorStoreFileCreateRequest(fileId, null);
	}

	public static VectorStoreFileCreateRequest of(String fileId,
			ChunkingStrategy chunkingStrategy) {
		return new VectorStoreFileCreateRequest(fileId, chunkingStrategy);
	}

}
