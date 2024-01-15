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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.qxrpcspring.bean.QxrpcError;
import ch.rasc.qxrpcspring.bean.QxrpcError.ErrorCode;
import ch.rasc.qxrpcspring.bean.QxrpcError.ErrorOrigin;
import ch.rasc.qxrpcspring.bean.QxrpcRequest;
import ch.rasc.qxrpcspring.bean.QxrpcResponse;
import ch.rasc.qxrpcspring.util.MethodInfo;
import ch.rasc.qxrpcspring.util.MethodInfoCache;
import ch.rasc.qxrpcspring.util.ParametersResolver;

@RestController
public class QxrpcController {

	private static final Log log = LogFactory.getLog(QxrpcController.class);

	private final ApplicationContext applicationContext;

	private final ObjectMapper objectMapper;

	private final ParametersResolver parametersResolver;

	private final MethodInfoCache methodInfoCache;

	@Autowired
	public QxrpcController(ApplicationContext applicationContext,
			MethodInfoCache methodInfoCache) {
		this.applicationContext = applicationContext;
		this.methodInfoCache = methodInfoCache;
		this.objectMapper = new ObjectMapper();

		Map<String, ConversionService> conversionServices = applicationContext
				.getBeansOfType(ConversionService.class);

		ConversionService conversionService = null;

		if (conversionServices.isEmpty()) {
			conversionService = new DefaultFormattingConversionService();
		}
		else if (conversionServices.size() == 1) {
			conversionService = conversionServices.values().iterator().next();
		}
		else {
			if (conversionServices.containsKey("mvcConversionService")) {
				conversionService = conversionServices.get("mvcConversionService");
			}
			else {
				for (ConversionService cs : conversionServices.values()) {
					if (cs instanceof FormattingConversionService) {
						conversionService = cs;
						break;
					}
				}
				if (conversionService == null) {
					conversionService = conversionServices.values().iterator().next();
				}
			}
		}

		this.parametersResolver = new ParametersResolver(conversionService,
				this.objectMapper);
	}

	@RequestMapping(value = "/.qxrpc", method = RequestMethod.GET)
	public ResponseEntity<String> init(HttpServletRequest request) {

		StringBuilder sb = new StringBuilder(220);

		sb.append("(function(){");
		sb.append("if (!window.qx) window.qx = {};");
		sb.append("window.qx.core={");
		sb.append("ServerSettings:{");
		String requestUrl = request.getRequestURL().toString();
		int pos = requestUrl.indexOf("/.qxrpc");
		sb.append("serverPathPrefix:'").append(requestUrl.substring(0, pos)).append("',");
		sb.append("serverPathSuffix:'',");

		int sessionTimeoutInSeconds = request.getSession().getMaxInactiveInterval();
		if (sessionTimeoutInSeconds <= 0) {
			sessionTimeoutInSeconds = Integer.MAX_VALUE;
		}

		sb.append("sessionTimeoutInSeconds:").append(sessionTimeoutInSeconds).append(",");

		sb.append("lastSessionRefresh:(new Date()).getTime()");
		sb.append("}};");
		sb.append("})();");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("text/javascript"));

		return new ResponseEntity<>(sb.toString(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/.qxrpc", method = RequestMethod.POST)
	public MappingJacksonValue qxrpc(@RequestBody QxrpcRequest qxrpcRequest,
			HttpServletRequest request, HttpServletResponse response, Locale locale) {

		if (qxrpcRequest.isRefreshSession()) {
			return createRefreshSessionResponse(qxrpcRequest, request);
		}

		MethodInfo methodInfo = this.methodInfoCache.get(qxrpcRequest.getService(),
				qxrpcRequest.getMethod());

		if (methodInfo != null) {
			try {
				Object[] parameters = this.parametersResolver.resolveParameters(request,
						response, locale, qxrpcRequest, methodInfo);

				Object returnValue = invoke(this.applicationContext,
						qxrpcRequest.getService(), methodInfo, parameters);

				// If the QxrpcMethod returns an instance of MappingJacksonValue, wrap the
				// value in a QxrpcResponse and ignore a possible @JsonView annotation.
				// Any other return value wrap it in a QxrpcResponse and
				// MappingJacksonValue instance.
				MappingJacksonValue result;
				if (returnValue instanceof MappingJacksonValue) {
					result = (MappingJacksonValue) returnValue;
					result.setValue(new QxrpcResponse(qxrpcRequest, result.getValue()));
				}
				else {
					result = new MappingJacksonValue(
							new QxrpcResponse(qxrpcRequest, returnValue));
					if (methodInfo.getJsonView() != null) {
						result.setSerializationView(methodInfo.getJsonView());
					}
				}

				return result;
			}
			catch (Exception e) {
				log.error(
						"Error calling method: " + qxrpcRequest.getService() + "."
								+ qxrpcRequest.getMethod(),
						e.getCause() != null ? e.getCause() : e);

				QxrpcResponse qxrpcResponse = new QxrpcResponse(qxrpcRequest, null,
						new QxrpcError(ErrorOrigin.METHOD,
								e instanceof IllegalArgumentException
										? ErrorCode.PARAMETER_MISMATCH
										: ErrorCode.OTHER));
				return new MappingJacksonValue(qxrpcResponse);
			}
		}

		log.error("Error invoking method '" + qxrpcRequest.getService() + "."
				+ qxrpcRequest.getMethod() + "'. Method or Bean not found");
		QxrpcResponse qxrpcResponse = new QxrpcResponse(qxrpcRequest, null,
				new QxrpcError(ErrorOrigin.SERVER, ErrorCode.SERVICE_NOT_FOUND));
		return new MappingJacksonValue(qxrpcResponse);
	}

	private static MappingJacksonValue createRefreshSessionResponse(
			QxrpcRequest qxrpcRequest, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("function() {");
		sb.append("qx.core.ServerSettings.serverPathSuffix = \";jsessionid="
				+ request.getSession().getId() + "\";");
		sb.append("qx.core.ServerSettings.sessionTimeoutInSeconds = "
				+ request.getSession().getMaxInactiveInterval() + ";");
		sb.append("qx.core.ServerSettings.lastSessionRefresh = (new Date()).getTime();");
		sb.append("return true;}()");
		System.out.println(sb.length());
		return new MappingJacksonValue(new QxrpcResponse(qxrpcRequest, sb.toString()));
	}

	public static Object invoke(ApplicationContext context, String beanName,
			MethodInfo methodInfo, final Object[] params) throws IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {

		Object bean = context.getBean(beanName);
		Method handlerMethod = methodInfo.getMethod();
		ReflectionUtils.makeAccessible(handlerMethod);
		return handlerMethod.invoke(bean, params);
	}

}
