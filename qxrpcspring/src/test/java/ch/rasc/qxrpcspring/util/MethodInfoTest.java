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

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ReflectionUtils;

import ch.rasc.qxrpcspring.QxrpcSpring;
import ch.rasc.qxrpcspring.annotation.QxrpcMethod;
import ch.rasc.qxrpcspring.util.SpringManagedBean.ExampleJsonView;

public class MethodInfoTest {

	@Test
	public void testMethodInfoCreation() {
		Method methodA = ReflectionUtils.findMethod(SpringManagedBean.class, "methodA");
		Method methodB = ReflectionUtils.findMethod(SpringManagedBean.class, "methodB");
		Method methodSum = ReflectionUtils.findMethod(SpringManagedBean.class, "sum",
				Integer.TYPE, Integer.TYPE);

		MethodInfo methodInfoA = new MethodInfo(SpringManagedBean.class, methodA);
		MethodInfo methodInfoB = new MethodInfo(SpringManagedBean.class, methodB);
		MethodInfo methodInfoSum = new MethodInfo(SpringManagedBean.class, methodSum);

		assertThat(methodInfoA.getMethod()).isEqualTo(methodA);
		assertThat(methodInfoA.getJsonView()).isNull();
		assertThat(methodInfoA.getParameters()).isEmpty();

		assertThat(methodInfoB.getMethod()).isEqualTo(methodB);
		Object jsonView = methodInfoB.getJsonView();
		assertThat(jsonView).isEqualTo(ExampleJsonView.class);
		assertThat(methodInfoB.getParameters()).isEmpty();

		assertThat(methodInfoSum.getMethod()).isEqualTo(methodSum);
		assertThat(methodInfoSum.getJsonView()).isNull();
		assertThat(methodInfoSum.getParameters()).hasSize(2);
	}

	@Test
	@SuppressWarnings("resource")
	public void testFindMethodWithAnnotation() {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				QxrpcSpring.class.getPackage().getName());
		MethodInfoCache methodInfoCache = context.getBean(MethodInfoCache.class);

		MethodInfo methodBInfo = methodInfoCache.get("springManagedBean", "methodB");
		Method methodBWithAnnotation = MethodInfo
				.findMethodWithAnnotation(methodBInfo.getMethod(), QxrpcMethod.class);
		assertThat(methodBWithAnnotation).isEqualTo(methodBInfo.getMethod());

		MethodInfo methodSubBInfo = methodInfoCache.get("springManagedSubBean",
				"methodB");
		methodBWithAnnotation = MethodInfo
				.findMethodWithAnnotation(methodSubBInfo.getMethod(), QxrpcMethod.class);
		assertThat(methodSubBInfo.getMethod().equals(methodBWithAnnotation)).isFalse();
		assertThat(methodBInfo.getMethod().equals(methodBWithAnnotation)).isTrue();
	}

}
