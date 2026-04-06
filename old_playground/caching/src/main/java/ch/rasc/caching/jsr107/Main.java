package ch.rasc.caching.jsr107;

import java.math.BigInteger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	// https://spring.io/blog/2014/04/14/cache-abstraction-jcache-jsr-107-annotations-support

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class)) {
			Calculator calculator = ctx.getBean(Calculator.class);

			BigInteger result = calculator.factorial(69);
			System.out.println("69! = " + result);

			result = calculator.factorial(69);
			System.out.println("69! = " + result);

			calculator.factorialAlways(68, false);
			calculator.factorial(68);

			result = calculator.factorial(3);
			System.out.println("3! = " + result);

			calculator.putSomethingIntoTheCache(3, new BigInteger("6"));
			result = calculator.factorial(3);
			System.out.println("3! = " + result);

			calculator.removeSomethingFromTheCache(3);
			result = calculator.factorial(3);
			System.out.println("3! = " + result);

			calculator.removeEverythingFromTheCache();
			calculator.factorial(68);
		}
	}

}
