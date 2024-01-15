package ch.ralscha.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("ch.ralscha.config");
		ctx.refresh();

		MyService myService = ctx.getBean(MyService.class);
		myService.sayHello();

		String[] allBeans = ctx.getBeanDefinitionNames();
		for (String beanName : allBeans) {
			System.out.println(beanName);
		}
	}
}
