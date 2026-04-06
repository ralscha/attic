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
package ch.rasc.openai4j.threads.messages;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.openai4j.common.SortOrder;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings("hiding")
public class ThreadMessagesListRequest {

	private final Integer limit;
	private final SortOrder order;
	private final String after;
	private final String before;
	private final String runId;

	private ThreadMessagesListRequest(Builder builder) {
		this.limit = builder.limit;
		this.order = builder.order;
		this.after = builder.after;
		this.before = builder.before;
		this.runId = builder.runId;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> queryParameters = new HashMap<>();
		if (this.limit != null) {
			queryParameters.put("limit", this.limit);
		}
		if (this.order != null) {
			queryParameters.put("order", this.order.value());
		}
		if (this.after != null && !this.after.isBlank()) {
			queryParameters.put("after", this.after);
		}
		if (this.before != null && !this.before.isBlank()) {
			queryParameters.put("before", this.before);
		}
		if (this.runId != null && !this.runId.isBlank()) {
			queryParameters.put("run_id", this.runId);
		}
		return queryParameters;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Integer limit;
		private SortOrder order;
		private String after;
		private String before;
		private String runId;

		private Builder() {
		}

		/**
		 * A limit on the number of objects to be returned. Limit can range between 1 and
		 * 100, and the default is 20.
		 */
		public Builder limit(Integer limit) {
			this.limit = limit;
			return this;
		}

		/**
		 * Sort order by the created_at timestamp of the objects. asc for ascending order
		 * and desc for descending order. Defaults to desc
		 */
		public Builder order(SortOrder order) {
			this.order = order;
			return this;
		}

		/**
		 * A cursor for use in pagination. after is an object ID that defines your place
		 * in the list. For instance, if you make a list request and receive 100 objects,
		 * ending with obj_foo, your subsequent call can include after=obj_foo in order to
		 * fetch the next page of the list.
		 */
		public Builder after(String after) {
			this.after = after;
			return this;
		}

		/**
		 * A cursor for use in pagination. before is an object ID that defines your place
		 * in the list. For instance, if you make a list request and receive 100 objects,
		 * ending with obj_foo, your subsequent call can include before=obj_foo in order
		 * to fetch the previous page of the list.
		 */
		public Builder before(String before) {
			this.before = before;
			return this;
		}

		/**
		 * Filter messages by the run ID that generated them.
		 */
		public Builder runId(String runId) {
			this.runId = runId;
			return this;
		}

		public ThreadMessagesListRequest build() {
			return new ThreadMessagesListRequest(this);
		}
	}
}
