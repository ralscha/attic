import ch.rasc.embeddedtc.EmbeddedTomcat;

public class StartCarTracker {
	public static void main(String[] args) throws Exception {
		EmbeddedTomcat.create().setContextFile("./src/main/config/tomcat.xml")
				.startAndWait();
	}
}
