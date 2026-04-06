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
package ch.rasc.openai4j.assistants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileSearchTool extends Tool {

	record FileSearch(@JsonProperty("max_num_results") Integer maxNumResults,
			@JsonProperty("ranking_options") RankingOptions rankingOptions) {
	}

	/**
	 * @param ranker The ranker to use for the file search. If not specified will use the
	 * auto ranker.
	 * @param scoreThreshold The score threshold for the file search. All values must be a
	 * floating point number between 0 and 1.
	 */
	record RankingOptions(String ranker,
			@JsonProperty("score_threshold") double scoreThreshold) {
	}

	@JsonProperty("file_search")
	private final FileSearch fileSearch;

	FileSearchTool(FileSearch fileSearch) {
		super("file_search");
		this.fileSearch = fileSearch;
	}

	public static FileSearchTool of() {
		return new FileSearchTool(null);
	}

	/**
	 * @param maxNumResults The maximum number of results the file search tool should
	 * output. The default is 20 for gpt-4* models and 5 for gpt-3.5-turbo. This number
	 * should be between 1 and 50 inclusive.<br>
	 * Note that the file search tool may output fewer than max_num_results results.
	 * Optional.
	 */
	public static FileSearchTool of(Integer maxNumResults) {
		return new FileSearchTool(new FileSearch(maxNumResults, null));
	}

	/**
	 * @param maxNumResults The maximum number of results the file search tool should
	 * output. The default is 20 for gpt-4* models and 5 for gpt-3.5-turbo. This number
	 * should be between 1 and 50 inclusive.<br>
	 * Note that the file search tool may output fewer than max_num_results results.
	 * Optional.
	 * @param rankingOptions The ranking options for the file search. If not specified,
	 * the file search tool will use the auto ranker and a score_threshold of 0.
	 */
	public static FileSearchTool of(Integer maxNumResults,
			RankingOptions rankingOptions) {
		return new FileSearchTool(new FileSearch(maxNumResults, rankingOptions));
	}

}
