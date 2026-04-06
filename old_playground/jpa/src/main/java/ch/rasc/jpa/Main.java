package ch.rasc.jpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.rasc.jpa.config.AppConfig;
import ch.rasc.jpa.service.EmployeeService;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		EmployeeService service = ctx.getBean(EmployeeService.class);
		service.create();
		service.update();
		// service.remove();
		service.query();

	}

}
