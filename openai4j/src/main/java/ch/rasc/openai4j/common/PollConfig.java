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

import java.util.concurrent.TimeUnit;

@SuppressWarnings("hiding")
public class PollConfig {
	private final long pollInterval;
	private final TimeUnit pollIntervalTimeUnit;
	private final long maxWait;
	private final TimeUnit maxWaitTimeUnit;

	private PollConfig(Builder builder) {
		if (builder.maxWaitTimeUnit == null) {
			builder.maxWaitTimeUnit = TimeUnit.MINUTES;
			builder.maxWait = 2;
		}
		if (builder.pollIntervalTimeUnit == null) {
			builder.pollIntervalTimeUnit = TimeUnit.SECONDS;
			builder.pollInterval = 1;
		}

		this.pollInterval = builder.pollInterval;
		this.pollIntervalTimeUnit = builder.pollIntervalTimeUnit;
		this.maxWait = builder.maxWait;
		this.maxWaitTimeUnit = builder.maxWaitTimeUnit;
	}

	public long pollInterval() {
		return this.pollInterval;
	}

	public TimeUnit pollIntervalTimeUnit() {
		return this.pollIntervalTimeUnit;
	}

	public long maxWait() {
		return this.maxWait;
	}

	public TimeUnit maxWaitTimeUnit() {
		return this.maxWaitTimeUnit;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private long pollInterval;
		private TimeUnit pollIntervalTimeUnit;
		private long maxWait;
		private TimeUnit maxWaitTimeUnit;

		public Builder pollInterval(long pollInterval, TimeUnit pollIntervalTimeUnit) {
			this.pollInterval = pollInterval;
			this.pollIntervalTimeUnit = pollIntervalTimeUnit;
			return this;
		}

		public Builder maxWait(long maxWait, TimeUnit maxWaitTimeUnit) {
			this.maxWait = maxWait;
			this.maxWaitTimeUnit = maxWaitTimeUnit;
			return this;
		}

		public PollConfig build() {
			return new PollConfig(this);
		}
	}
}
