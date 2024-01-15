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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;

import com.fasterxml.jackson.annotation.JsonView;

import ch.rasc.qxrpcspring.annotation.QxrpcMethod;

/**
 * This object stores informations about a method.
 * <li>A reference to the method
 * <li>A list of the parameters
 * <li>The jackson jsonview class
 */
public final class MethodInfo {

	private final Method method;

	private final Class<?> jsonView;

	private final List<ParameterInfo> parameters;

	public MethodInfo(Class<?> clazz, Method method) {
		this.method = method;
		this.parameters = buildParameterList(clazz, method);

		JsonView jsonViewAnnotation = AnnotationUtils.findAnnotation(method,
				JsonView.class);
		if (jsonViewAnnotation != null) {
			Class<?>[] jsonViewClasses = jsonViewAnnotation.value();
			if (jsonViewClasses.length != 1) {
				throw new IllegalArgumentException(
						"@JsonView on a @QxrpcMethod must have exactly one class argument: "
								+ clazz.getName() + "." + method.getName());
			}

			this.jsonView = jsonViewClasses[0];
		}
		else {
			this.jsonView = null;
		}
	}

	private static List<ParameterInfo> buildParameterList(Class<?> clazz, Method method) {
		List<ParameterInfo> params = new ArrayList<>();

		Class<?>[] parameterTypes = method.getParameterTypes();

		Method methodWithAnnotation = findMethodWithAnnotation(method, QxrpcMethod.class);
		if (methodWithAnnotation == null) {
			methodWithAnnotation = method;
		}

		for (int paramIndex = 0; paramIndex < parameterTypes.length; paramIndex++) {
			params.add(new ParameterInfo(clazz, methodWithAnnotation, paramIndex));
		}

		return params;
	}

	/**
	 * Find a method that is annotated with a specific annotation. Starts with the method
	 * and goes up to the superclasses of the class.
	 *
	 * @param method the starting method
	 * @param annotation the annotation to look for
	 * @return the method if there is a annotated method, else null
	 */
	public static Method findMethodWithAnnotation(Method method,
			Class<? extends Annotation> annotation) {
		if (method.isAnnotationPresent(annotation)) {
			return method;
		}

		Class<?> cl = method.getDeclaringClass();
		while (cl != null && cl != Object.class) {
			try {
				Method equivalentMethod = cl.getDeclaredMethod(method.getName(),
						method.getParameterTypes());
				if (equivalentMethod.isAnnotationPresent(annotation)) {
					return equivalentMethod;
				}
			}
			catch (NoSuchMethodException e) {
				// do nothing here
			}
			cl = cl.getSuperclass();
		}

		return null;
	}

	public Method getMethod() {
		return this.method;
	}

	public Class<?> getJsonView() {
		return this.jsonView;
	}

	public List<ParameterInfo> getParameters() {
		return this.parameters;
	}

}
