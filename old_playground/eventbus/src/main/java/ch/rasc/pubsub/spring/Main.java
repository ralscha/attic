package ch.rasc.pubsub.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		try (final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class)) {
			System.out.println("MAIN: " + Thread.currentThread().getName());
			ctx.getBean(EventPublisher.class).publishEvent("hello world");
		}

	}

}
