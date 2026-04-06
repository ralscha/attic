package ch.rasc.caching.guava;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				Config.class)) {
			Calculator calculator = ctx.getBean(Calculator.class);

			System.out.println("output: " + calculator.doSomething("1"));
			System.out.println("output: " + calculator.doSomething(null));
			System.out.println("output: " + calculator.doSomething(null));

			for (int i = 0; i < 20; i++) {
				System.out.println("Calling with : " + i);
				calculator.factorialMaxCache(i);
			}

			for (int i = 19; i >= 0; i--) {
				System.out.println("Calling with : " + i);
				calculator.factorialMaxCache(i);
			}

			System.out.println();
			System.out.println("Calling time cache method");
			System.out.println("1st call");
			calculator.factorialTimedCache(10);
			System.out.println("2nd call");
			calculator.factorialTimedCache(10);
			try {
				System.out.println("Waiting 70 seconds");
				TimeUnit.SECONDS.sleep(70);
			}
			catch (InterruptedException e) {
				// do nothing
			}
			System.out.println("3rd call");
			calculator.factorialTimedCache(10);
			System.out.println("4th call");
			calculator.factorialTimedCache(10);

			ctx.getBean(CacheCleanup.class).shutdown();
		}
	}

}
