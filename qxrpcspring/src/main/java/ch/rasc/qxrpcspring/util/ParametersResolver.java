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

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import ch.rasc.qxrpcspring.bean.QxrpcRequest;

/**
 * Resolver of ExtDirectRequest parameters.
 */
public final class ParametersResolver {

	private final UrlPathHelper urlPathHelper = new UrlPathHelper();

	private final ConversionService conversionService;

	private final ObjectMapper objectMapper;

	private final Expression getPrincipalExpression = new SpelExpressionParser()
			.parseExpression(
					"T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication()?.getPrincipal()");

	public ParametersResolver(ConversionService conversionService,
			ObjectMapper objectMapper) {
		this.conversionService = conversionService;
		this.objectMapper = objectMapper;
	}

	public Object[] resolveParameters(HttpServletRequest request,
			HttpServletResponse response, Locale locale, QxrpcRequest qxrpcRequest,
			MethodInfo methodInfo) throws Exception {

		int jsonParamIndex = 0;
		List<ParameterInfo> methodParameters = methodInfo.getParameters();
		Object[] parameters = null;

		if (!methodParameters.isEmpty()) {
			parameters = new Object[methodParameters.size()];

			for (int paramIndex = 0; paramIndex < methodParameters.size(); paramIndex++) {
				ParameterInfo methodParameter = methodParameters.get(paramIndex);

				if (methodParameter.isSupportedParameter()) {
					parameters[paramIndex] = SupportedParameters.resolveParameter(
							methodParameter.getType(), request, response, locale);
				}
				else if (methodParameter.hasRequestHeaderAnnotation()) {
					parameters[paramIndex] = resolveRequestHeader(request,
							methodParameter);
				}
				else if (methodParameter.hasCookieValueAnnotation()) {
					parameters[paramIndex] = resolveCookieValue(request, methodParameter);
				}
				else if (methodParameter.hasAuthenticationPrincipalAnnotation()) {
					parameters[paramIndex] = resolveAuthenticationPrincipal(
							methodParameter);
				}
				else if (qxrpcRequest.getParams() != null
						&& qxrpcRequest.getParams().length > jsonParamIndex) {
					parameters[paramIndex] = convertValue(
							qxrpcRequest.getParams()[jsonParamIndex], methodParameter);
					jsonParamIndex++;
				}
				else {
					throw new IllegalArgumentException(
							"Error, parameter mismatch. Please check your remoting method signature to ensure all supported parameters types are used.");
				}
			}
		}

		return parameters;
	}

	private Object resolveRequestHeader(HttpServletRequest request,
			ParameterInfo parameterInfo) {
		String value = request.getHeader(parameterInfo.getName());

		if (value == null) {
			value = parameterInfo.getDefaultValue();
		}

		if (value != null) {
			return convertValue(value, parameterInfo);
		}

		// value is null and the parameter is java.util.Optional then return an empty
		// Optional
		if (parameterInfo.isJavaUtilOptional()) {
			return Optional.empty();
		}

		if (parameterInfo.isRequired()) {
			throw new IllegalStateException("Missing header '" + parameterInfo.getName()
					+ "' of type [" + parameterInfo.getTypeDescriptor().getType() + "]");
		}

		return null;
	}

	private Object resolveCookieValue(HttpServletRequest request,
			ParameterInfo parameterInfo) {

		Cookie cookieValue = WebUtils.getCookie(request, parameterInfo.getName());
		String value = null;

		if (cookieValue != null) {
			value = this.urlPathHelper.decodeRequestString(request,
					cookieValue.getValue());
		}
		else {
			value = parameterInfo.getDefaultValue();
		}

		if (value != null) {
			return convertValue(value, parameterInfo);
		}

		// value is null and the parameter is java.util.Optional then return an empty
		// Optional
		if (parameterInfo.isJavaUtilOptional()) {
			return Optional.empty();
		}

		if (parameterInfo.isRequired()) {
			throw new IllegalStateException("Missing cookie '" + parameterInfo.getName()
					+ "' of type [" + parameterInfo.getTypeDescriptor().getType() + "]");
		}

		return null;
	}

	private Object resolveAuthenticationPrincipal(ParameterInfo parameterInfo) {
		Object principal = this.getPrincipalExpression.getValue();

		if (principal != null
				&& !parameterInfo.getType().isAssignableFrom(principal.getClass())) {
			if (parameterInfo.authenticationPrincipalAnnotationErrorOnInvalidType()) {
				throw new ClassCastException(
						principal + " is not assignable to " + parameterInfo.getType());
			}
			return null;
		}
		return principal;
	}

	private Object convertValue(Object value, ParameterInfo methodParameter) {
		if (value != null) {
			if (methodParameter.getType().isAssignableFrom(value.getClass())) {
				return value;
			}
			else if (this.conversionService.canConvert(TypeDescriptor.forObject(value),
					methodParameter.getTypeDescriptor())) {

				try {
					return this.conversionService.convert(value,
							TypeDescriptor.forObject(value),
							methodParameter.getTypeDescriptor());
				}
				catch (ConversionFailedException e) {
					// ignore this exception for collections and arrays.
					// try to convert the value with jackson
					TypeFactory typeFactory = this.objectMapper.getTypeFactory();
					if (methodParameter.getTypeDescriptor().isCollection()) {
						JavaType type = CollectionType
								.construct(methodParameter.getType(),
										typeFactory.constructType(methodParameter
												.getTypeDescriptor()
												.getElementTypeDescriptor().getType()));
						return this.objectMapper.convertValue(value, type);
					}
					else if (methodParameter.getTypeDescriptor().isArray()) {
						JavaType type = typeFactory
								.constructArrayType(methodParameter.getTypeDescriptor()
										.getElementTypeDescriptor().getType());
						return this.objectMapper.convertValue(value, type);
					}

					throw e;
				}
			}
			else {
				return this.objectMapper.convertValue(value, methodParameter.getType());
			}

		}
		else if (methodParameter.isJavaUtilOptional()) {
			return Optional.empty();
		}

		return null;
	}

}
