package ch.rasc.mongodb.author;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class WriteText {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				"ch.rasc.mongodb.author")) {

			Author author = ctx.getBean("author", Author.class);
			String text = author.writeText(1000);

			System.out.println(text);
		}

	}

}
