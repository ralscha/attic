package ch.rasc.caching.spring;

import java.math.BigInteger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				Config.class)) {
			Calculator calculator = ctx.getBean(Calculator.class);

			BigInteger result = calculator.factorial(69);
			System.out.println("69! = " + result);
			System.out.println();

			result = calculator.factorial(69);
			System.out.println("69! = " + result);
			System.out.println();

			result = calculator.callFromInside();
			System.out.println("69! = " + result);
			System.out.println();

			System.out.println("clearing cache");
			calculator.clearCache();
			System.out.println();

			result = calculator.factorial(69);
			System.out.println("69! = " + result);
			System.out.println();

			result = calculator.factorial(69, "sr");
			System.out.println("69! = " + result);
			System.out.println();

			result = calculator.factorial(69, "mr");
			System.out.println("69! = " + result);
			System.out.println();

			result = calculator.factorial(2, "mr");
			System.out.println("2! = " + result);
			System.out.println();

			result = calculator.factorialWithACondition(2);
			System.out.println("2! = " + result);
			System.out.println();

			result = calculator.factorialWithACondition(2);
			System.out.println("2! = " + result);
			System.out.println();

			result = calculator.factorialWithACondition(69);
			System.out.println("69! = " + result);
			System.out.println();
		}

	}

}
