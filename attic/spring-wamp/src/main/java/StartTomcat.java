import ch.rasc.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(String[] args) {
		EmbeddedTomcat.create().startAndWait();
	}
}
