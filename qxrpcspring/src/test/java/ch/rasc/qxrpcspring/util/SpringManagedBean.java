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
package ch.rasc.qxrpcspring.util;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonView;

import ch.rasc.qxrpcspring.annotation.QxrpcMethod;

@Service
public class SpringManagedBean {

	public interface ExampleJsonView {
		// nothing here
	}

	public boolean methodA() {
		return true;
	}

	@QxrpcMethod
	@JsonView(ExampleJsonView.class)
	public boolean methodB() {
		return false;
	}

	@QxrpcMethod
	public int sum(int a, int b) {
		return a + b;
	}
}
