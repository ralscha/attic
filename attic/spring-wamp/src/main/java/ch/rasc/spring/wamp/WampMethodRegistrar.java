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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.web.method.HandlerMethodSelector;

@Service
public class WampMethodRegistrar implements ApplicationListener<ContextRefreshedEvent> {

	private final Map<Key, MethodInfo> cache = new HashMap<>();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ApplicationContext context = (ApplicationContext) event.getSource();

		String[] beanNames = context.getBeanNamesForType(Object.class);

		for (String beanName : beanNames) {

			Class<?> handlerType = context.getType(beanName);
			final Class<?> userType = ClassUtils.getUserClass(handlerType);

			Set<Method> methods = HandlerMethodSelector.selectMethods(userType,
					new MethodFilter() {
						@Override
						public boolean matches(Method method) {
							return AnnotationUtils.findAnnotation(method,
									WampMethod.class) != null;
						}
					});

			for (Method method : methods) {
				WampMethod wampMethodAnnotation = AnnotationUtils.findAnnotation(method,
						WampMethod.class);
				if (wampMethodAnnotation != null) {
					MethodInfo info = new MethodInfo(method);
					cache.put(new Key(beanName, method.getName()), info);
					System.out.println("Register : " + beanName + "." + method.getName());
				}
			}
		}
	}

	public MethodInfo getWampMethod(String beanName, String methodName) {
		return cache.get(new Key(beanName, methodName));
	}

	public final static class Key {

		private final String beanName;

		private final String methodName;

		public Key(String beanName, String methodName) {
			this.beanName = beanName;
			this.methodName = methodName;
		}

		public String getBeanName() {
			return beanName;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Key)) {
				return false;
			}

			Key other = (Key) o;
			return Objects.equals(beanName, other.beanName)
					&& Objects.equals(methodName, other.methodName);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(new Object[] { beanName, methodName });
		}

	}

}
