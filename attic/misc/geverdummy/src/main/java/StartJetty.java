import java.io.IOException;
import java.net.ServerSocket;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartJetty {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		System.setProperty("spring.profiles.active", "development");

		int port = 8081;

		try {
			ServerSocket srv = new ServerSocket(port);
			srv.close();
		} catch (IOException e) {
			System.out.println("PORT " + port + " ALREADY IN USE");
			return;
		}

		WebAppContext context = new WebAppContext("./src/main/webapp", "/");

		context.setConfigurations(new Configuration[] { new org.eclipse.jetty.webapp.WebInfConfiguration(),
				new org.eclipse.jetty.webapp.WebXmlConfiguration(),
				new org.eclipse.jetty.webapp.MetaInfConfiguration(),
				new org.eclipse.jetty.webapp.FragmentConfiguration(),
				/*
				new org.eclipse.jetty.plus.webapp.EnvConfiguration(),
				new org.eclipse.jetty.plus.webapp.PlusConfiguration(),
				*/
				new org.eclipse.jetty.webapp.JettyWebXmlConfiguration() });

		Server server = new Server(port);
		server.setHandler(context);
		server.start();

		System.out.println("Jetty Startup Time: " + (System.currentTimeMillis() - start) + " ms");
		System.out.println("Jetty running on " + port);
	}

}
