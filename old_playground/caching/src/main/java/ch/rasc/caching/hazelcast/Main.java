package ch.rasc.caching.hazelcast;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hazelcast.core.HazelcastInstance;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class)) {
			Calculator calculator = ctx.getBean(Calculator.class);

			BigInteger result = calculator.factorial(69);
			System.out.println("69! = " + result);

			result = calculator.factorial(69);
			System.out.println("69! = " + result);

			TimeUnit.MINUTES.sleep(1);

			ctx.getBean(HazelcastInstance.class).shutdown();
		}
	}

}
