package ch.rasc.pubsub.tail;

import java.nio.file.Paths;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		try (final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class)) {

			ctx.getBean(DirectoryWatcher.class).startWatch(Paths.get("c:/temp"));

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					ctx.stop();
				}
			});

		}
	}

}
