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

import nl.jqno.equalsverifier.EqualsVerifier;

public class MethodInfoCacheTest {

	@Test
	public void verifyEquals() {
		EqualsVerifier.forClass(MethodInfoCache.Key.class).verify();
	}

	@Test
	public void testPutAndGet() throws SecurityException, NoSuchMethodException {
		MethodInfoCache methodInfoCache = new MethodInfoCache();

		Method thisMethod = getClass().getMethod("testPutAndGet", (Class<?>[]) null);

		methodInfoCache.put("methodCacheTest", getClass(), thisMethod);
		assertThat(methodInfoCache.get("methodCacheTest", "testPu")).isNull();
		assertThat(methodInfoCache.get("methodCacheTes", "testPut")).isNull();
		assertThat(methodInfoCache.get("methodCacheTest", "testPutAndGet").getMethod())
				.isEqualTo(thisMethod);
	}

	@Test
	public void testKey() {
		MethodInfoCache.Key key1 = new MethodInfoCache.Key("bean", "method");
		MethodInfoCache.Key key2 = new MethodInfoCache.Key("bean", "otherMethod");
		MethodInfoCache.Key key3 = new MethodInfoCache.Key("otherBean", "otherMethod");

		assertThat(key1.equals(key1)).isTrue();
		assertThat(key2.equals(key2)).isTrue();
		assertThat(key3.equals(key3)).isTrue();

		assertThat(key1.equals(key2)).isFalse();
		assertThat(key1.equals(key3)).isFalse();

		assertThat(key1.equals("test")).isFalse();
	}

}
