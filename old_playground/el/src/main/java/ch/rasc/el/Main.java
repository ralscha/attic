package ch.rasc.el;

import javax.el.ELProcessor;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Main {

	public static void main(String[] args) {

		// Set the "spring.expression.compiler.mode" property to "immediate" or "mixed"
		// System.setProperty("spring.expression.compiler.mode", "immediate");

		User u = new User();
		u.setAge(23);
		u.setUserName("admin");
		u.setPassword("mypassword");
		ELProcessor el = new ELProcessor();
		el.defineBean("user", u);
		long start = System.currentTimeMillis();
		String result = null;
		for (int i = 0; i < 100000; i++) {
			result = (String) el.getValue("user.userName", String.class);
		}
		System.out.println(result);
		System.out.println(System.currentTimeMillis() - start + " ms");

		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			result = u.getUserName();
		}
		System.out.println(result);
		System.out.println(System.currentTimeMillis() - start + " ms");

		SpelParserConfiguration config = new SpelParserConfiguration(
				SpelCompilerMode.IMMEDIATE, null, false, false, Integer.MAX_VALUE);
		ExpressionParser parser = new SpelExpressionParser(config);
		Expression exp = parser.parseExpression("userName");
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			result = (String) exp.getValue(u);
		}
		System.out.println(result);
		System.out.println(System.currentTimeMillis() - start + " ms");

		// Long x = (Long)el.eval("a = [1, 2, 3]; a[1]");
		// System.out.println(x);

	}

}
