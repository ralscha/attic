package ch.rasc.mongodb.geolite;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportFile {
	public static void main(String[] args) throws IOException {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				"ch.rasc.mongodb.geolite")) {
			GeoliteImporter geoliteImporter = ctx.getBean("geoliteImporter",
					GeoliteImporter.class);
			geoliteImporter.importData();
		}
	}
}
