package ch.rasc.pubsub.guava;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@Service
public class EventBusSubscriberRegisterar implements BeanPostProcessor {

	private final EventBus eventBus;

	@Autowired
	public EventBusSubscriberRegisterar(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {

		for (Method method : bean.getClass().getMethods()) {
			if (method.getAnnotation(Subscribe.class) != null) {
				this.eventBus.register(bean);
				break;
			}
		}

		return bean;
	}
}