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

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class SupportedParametersTest {

	@Test
	public void testIsSupported() {
		assertThat(SupportedParameters.values().length).isEqualTo(5);
		assertThat(SupportedParameters.isSupported(String.class)).isFalse();
		assertThat(SupportedParameters.isSupported(null)).isFalse();
		assertThat(SupportedParameters.isSupported(HttpServletResponse.class)).isTrue();
		assertThat(SupportedParameters.isSupported(HttpServletRequest.class)).isTrue();
		assertThat(SupportedParameters.isSupported(HttpSession.class)).isTrue();
		assertThat(SupportedParameters.isSupported(Locale.class)).isTrue();
		assertThat(SupportedParameters.isSupported(Principal.class)).isTrue();
	}

	@Test
	public void testResolveParameter() {
		MockHttpServletRequest request = new MockHttpServletRequest("POST",
				"/action/api-debug.js");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Locale en = Locale.ENGLISH;

		assertThat(
				SupportedParameters.resolveParameter(String.class, request, response, en))
						.isNull();
		assertThat(SupportedParameters.resolveParameter(HttpServletRequest.class, request,
				response, en)).isSameAs(request);
		assertThat(SupportedParameters.resolveParameter(HttpSession.class, request,
				response, en)).isSameAs(request.getSession());
		assertThat(SupportedParameters.resolveParameter(Principal.class, request,
				response, en)).isSameAs(request.getUserPrincipal());
		assertThat(SupportedParameters.resolveParameter(HttpServletResponse.class,
				request, response, en)).isSameAs(response);
		assertThat(
				SupportedParameters.resolveParameter(Locale.class, request, response, en))
						.isSameAs(en);

	}

}
