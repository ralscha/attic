import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.rasc.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(String[] args) {

		Handler fh = new ConsoleHandler();
		fh.setLevel(Level.FINE);

		// Logger logger =
		// Logger.getLogger("org.apache.catalina.util.LifecycleBase");
		// logger.setLevel(Level.FINE);
		// logger.addHandler (fh);

		Logger logger = Logger.getLogger("org.apache");
		logger.setLevel(Level.SEVERE);
		logger.addHandler(fh);

		EmbeddedTomcat.create().startAndWait();
	}
}
