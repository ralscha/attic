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
package ch.rasc.openai4j.vectorstores.filebatches;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.vectorstores.ChunkingStrategy;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VectorStoreFileBatchCreateRequest {
	@JsonProperty("file_ids")
	private final List<String> fileIds;

	@JsonProperty("chunking_strategy")
	private final ChunkingStrategy chunkingStrategy;

	private VectorStoreFileBatchCreateRequest(List<String> fileIds,
			ChunkingStrategy chunkingStrategy) {
		this.fileIds = fileIds;
		this.chunkingStrategy = chunkingStrategy;
	}

	public static VectorStoreFileBatchCreateRequest of(List<String> fileIds) {
		return new VectorStoreFileBatchCreateRequest(List.copyOf(fileIds), null);
	}

	public static VectorStoreFileBatchCreateRequest of(List<String> fileIds,
			ChunkingStrategy chunkingStrategy) {
		return new VectorStoreFileBatchCreateRequest(List.copyOf(fileIds),
				chunkingStrategy);
	}
}
