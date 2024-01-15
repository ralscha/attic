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
package ch.rasc.qxrpcspring.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethodSelector;

import ch.rasc.qxrpcspring.annotation.QxrpcMethod;
import ch.rasc.qxrpcspring.annotation.QxrpcService;
import ch.rasc.qxrpcspring.util.MethodInfoCache;

/**
 * Spring application listener that listens for ContextRefreshedEvent events. If such an
 * event is received the listener will scan for {@link QxrpcService} annotated types or
 * {@link QxrpcMethod} annotated methods in the current ApplicationContext. Every public
 * method of a {@link QxrpcService} annotated type and every {@link QxrpcMethod} annotated
 * method will be cached in the {@link MethodInfoCache} . The class also reports warnings
 * and errors of misconfigured methods.
 */
@Service
public class MethodRegistrar
		implements ApplicationListener<ContextRefreshedEvent>, Ordered {

	private static final Log log = LogFactory.getLog(MethodRegistrar.class);

	private final MethodInfoCache methodInfoCache;

	@Autowired
	public MethodRegistrar(MethodInfoCache methodInfoCache) {
		this.methodInfoCache = methodInfoCache;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ApplicationContext context = (ApplicationContext) event.getSource();

		String[] beanNames = context.getBeanNamesForType(Object.class);

		for (String beanName : beanNames) {

			Class<?> handlerType = context.getType(beanName);
			final Class<?> userType = ClassUtils.getUserClass(handlerType);

			Set<Method> methods;
			if (AnnotationUtils.findAnnotation(userType, QxrpcService.class) != null) {
				// register every public method in this class
				methods = HandlerMethodSelector.selectMethods(userType,
						method -> Modifier.isPublic(method.getModifiers()));
			}
			else {
				// only methods that are annotated with @QxrpcMethod
				methods = HandlerMethodSelector.selectMethods(userType,
						method -> AnnotationUtils.findAnnotation(method,
								QxrpcMethod.class) != null);
			}

			for (Method method : methods) {

				this.methodInfoCache.put(beanName, handlerType, method);

				if (log.isDebugEnabled()) {
					String beanAndMethodName = beanName + "." + method.getName();
					log.debug("Register qxrpcMethod: " + beanAndMethodName);
				}
			}

		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 1000;
	}

}
