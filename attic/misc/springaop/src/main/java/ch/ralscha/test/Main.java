package ch.ralscha.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
		TestBean tb = ctx.getBean("testBean", TestBean.class);
		System.out.println(tb.echo("test"));
	}

}
