/**
 * Copyright 2010-2012 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.spring.wamp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class MethodInfo {

	private final List<ParameterInfo> parameters;

	private final Method method;

	public MethodInfo(Method method) {
		this.method = method;
		this.parameters = buildParameterList(method);
	}

	private static List<ParameterInfo> buildParameterList(Method method) {
		List<ParameterInfo> params = new ArrayList<>();

		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int paramIndex = 0; paramIndex < parameterTypes.length; paramIndex++) {
			params.add(new ParameterInfo(method, paramIndex));
		}

		return params;
	}

	public Method getMethod() {
		return method;
	}

	public List<ParameterInfo> getParameters() {
		return parameters;
	}

}