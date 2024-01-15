import ch.rasc.embeddedtc.EmbeddedTomcat;

public class ChangelogStartTomcat {
	public static void main(String[] args) throws Exception {
		EmbeddedTomcat.create().setPort(8080).setContextPath("/cl")
				.setContextFile("./src/main/config/tomcat.xml").startAndWait();
	}
}