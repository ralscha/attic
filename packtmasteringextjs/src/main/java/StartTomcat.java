import ch.rasc.embeddedtc.EmbeddedTomcat;

public class StartTomcat {
	public static void main(String[] args) throws Exception {
		/*
		 * To activate development mode from tomcat context file add this statement:
		 * <Environment name="spring.profiles.active" value="development"
		 * type="java.lang.String" override="false"/>
		 */

		// Comment out the following line to activate production profile
		System.setProperty("spring.profiles.active", "development");

		// String compressableMimeTypes =
		// "text/html,text/plain,text/xml,application/xml,application/xhtml+xml,text/javascript,text/css,application/x-javascript,application/json,application/javascript";
		// enableCompression(2048, compressableMimeTypes).
		EmbeddedTomcat.create().setContextFile("./src/main/config/tomcat.xml")
				.startAndWait();
	}
}
