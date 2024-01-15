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
public class QxrpcResponse {

	private final int id;
	private final Object result;
	private final QxrpcError error;

	public QxrpcResponse(QxrpcRequest request, Object result) {
		this(request, result, null);
	}

	public QxrpcResponse(QxrpcRequest request, Object result, QxrpcError error) {
		this.id = request.getId();
		this.result = result;
		this.error = error;
	}

	public int getId() {
		return this.id;
	}

	public Object getResult() {
		return this.result;
	}

	public QxrpcError getError() {
		return this.error;
	}

}
