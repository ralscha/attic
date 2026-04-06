package ch.rasc.pubsub.guava;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		try (final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class)) {
			ctx.getBean(Publisher.class).publishMsgEvent("a normal MsgEvent");
			System.out.println("=====");
			ctx.getBean(Publisher.class).publishSpecialMsgEvent("a special MsgEvent",
					"me");
			System.out.println("=====");
			ctx.getBean(Publisher.class).publishTimeEvent();
		}
	}

}
