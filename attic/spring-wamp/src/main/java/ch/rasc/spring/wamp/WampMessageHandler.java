package ch.rasc.spring.wamp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import ch.rasc.spring.wamp.in.WampCallMessage;
import ch.rasc.spring.wamp.out.WampCallResultMessage;
import ch.rasc.spring.wamp.out.WampWelcomeMessage;

@Component
public class WampMessageHandler implements InitializingBean {

	@Autowired
	private WampMethodRegistrar registrar;

	@Autowired
	private ApplicationContext context;

	@Autowired(required = false)
	private ConversionService conversionService;

	@Autowired(required = false)
	private JsonHandler jsonHandler;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jsonHandler == null) {
			jsonHandler = new JsonHandler();
		}

		if (conversionService == null) {
			conversionService = new DefaultFormattingConversionService();
		}
	}

	public String open(String sessionId) {
		WampWelcomeMessage msg = new WampWelcomeMessage(sessionId);
		return jsonHandler.writeValueAsString(msg.serialize());
	}

	public void close(String sessionId) {
		System.out.println("close");
	}

	public String handle(String inboundMessage) {

		String outboundMessage = null;

		Object[] msgs = jsonHandler.readValue(inboundMessage, Object[].class);
		int messageType = (Integer) msgs[0];
		switch (messageType) {
		case 1:
			// PREFIX
			// ignore
			break;
		case 2:
			// CALL
			WampCallMessage callMessage = new WampCallMessage();
			callMessage.deserialize(msgs);

			String beanName = null;
			String methodName = null;

			int lastSlash = callMessage.getProcURI().lastIndexOf('/');
			if (lastSlash != -1) {
				int hash = callMessage.getProcURI().lastIndexOf('#');
				if (hash != -1) {
					beanName = callMessage.getProcURI().substring(lastSlash + 1, hash);
					methodName = callMessage.getProcURI().substring(hash + 1);
				}
			}
			else {
				int colon = callMessage.getProcURI().lastIndexOf(':');
				if (colon != -1) {
					beanName = callMessage.getProcURI().substring(0, colon);
					methodName = callMessage.getProcURI().substring(colon + 1);
				}
			}

			if (beanName != null && methodName != null) {
				Object bean = context.getBean(beanName);
				MethodInfo methodInfo = registrar.getWampMethod(beanName, methodName);
				Method method = methodInfo.getMethod();
				ReflectionUtils.makeAccessible(method);

				Object result;
				try {
					Object[] parameters = resolveParameters(callMessage.getParameters(),
							methodInfo);
					result = method.invoke(bean, parameters);

					WampCallResultMessage resultMessage = new WampCallResultMessage(
							callMessage.getCallId(), result);
					outboundMessage = jsonHandler.writeValueAsString(resultMessage
							.serialize());
				}
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;
		case 5:
			// SUBSCRIBE
			break;
		case 6:
			// UNSUBSCRIBE
			break;
		case 7:
			// PUBLISH
			break;
		}

		return outboundMessage;
	}

	public Object[] resolveParameters(Object requestParameters, MethodInfo methodInfo)
			throws Exception {

		List<ParameterInfo> methodParameters = methodInfo.getParameters();

		if (requestParameters != null && methodParameters.size() == 1) {
			Object[] parameters = new Object[1];
			parameters[0] = convertValue(requestParameters, methodParameters.get(0));
			return parameters;
		}
		else if (requestParameters != null && requestParameters.getClass().isArray()
				&& methodParameters.size() == ((Object[]) requestParameters).length) {
			Object[] parameters = null;
			Object[] requestParametersArray = (Object[]) requestParameters;
			parameters = new Object[methodParameters.size()];
			for (int paramIndex = 0; paramIndex < methodParameters.size(); paramIndex++) {
				parameters[paramIndex] = convertValue(requestParametersArray[paramIndex],
						methodParameters.get(paramIndex));
			}
			return parameters;
		}
		else if (requestParameters == null && methodParameters.isEmpty()) {
			return null;
		}
		else {
			throw new IllegalArgumentException(
					"Error, parameter mismatch. Please check your remoting method signature.");
		}

	}

	private Object convertValue(Object value, ParameterInfo methodParameter) {
		if (value != null) {
			if (methodParameter.getType().equals(value.getClass())) {
				return value;
			}
			else if (conversionService.canConvert(TypeDescriptor.forObject(value),
					methodParameter.getTypeDescriptor())) {
				return conversionService.convert(value, TypeDescriptor.forObject(value),
						methodParameter.getTypeDescriptor());
			}
			else {
				return jsonHandler.convertValue(value, methodParameter.getType());
			}
		}
		return value;
	}

}