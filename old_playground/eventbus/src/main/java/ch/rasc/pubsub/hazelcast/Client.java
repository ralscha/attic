package ch.rasc.pubsub.hazelcast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Client {

	@Bean
	public ShortMessageSender shortMessageSender() {
		return new ShortMessageSender();
	}

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				Client.class)) {

			for (int i = 0; i < 10; i++) {
				ctx.getBean(ShortMessageSender.class).send("msg:" + i + ":" + new Date());
				try {
					TimeUnit.SECONDS.sleep(10);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
