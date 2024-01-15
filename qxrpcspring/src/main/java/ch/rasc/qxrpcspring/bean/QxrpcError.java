/**
 * Copyright 2014-2014 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.qxrpcspring.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class QxrpcError {

	public static enum ErrorOrigin {
		SERVER(1), METHOD(2);

		private int value;

		ErrorOrigin(int value) {
			this.value = value;
		}

		private int getValue() {
			return this.value;
		}
	}

	public static enum ErrorCode {
		ILLEGAL_SERVICE(1), SERVICE_NOT_FOUND(2), CLASS_NOT_FOUND(3), METHOD_NOT_FOUND(
				4), PARAMETER_MISMATCH(5), PERMISSION_DENIED(6), OTHER(7);

		private int value;

		ErrorCode(int value) {
			this.value = value;
		}

		private int getValue() {
			return this.value;
		}
	}

	private final int origin;
	private final int code;
	private final String message;

	public QxrpcError(ErrorOrigin origin, ErrorCode code) {
		this(origin, code, null);
	}

	public QxrpcError(ErrorOrigin origin, ErrorCode code, String message) {
		this.origin = origin.getValue();
		this.code = code.getValue();
		this.message = message;
	}

	public int getOrigin() {
		return this.origin;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}
