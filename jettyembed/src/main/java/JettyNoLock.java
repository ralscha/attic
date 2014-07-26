import java.io.IOException;
import java.net.ServerSocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyNoLock {
	public static void main(String[] args) throws Exception {

		int port = 8080;

		try (ServerSocket srv = new ServerSocket(port)) {
			srv.close();
		}
		catch (IOException e) {
			System.out.println("PORT " + port + " ALREADY IN USE");
			return;
		}

		WebAppContext context = new WebAppContext("./src/main/webapp", "/");
		context.setDefaultsDescriptor("./src/main/config/webdefault.xml");

		Server server = new Server(port);
		// ((SelectChannelConnector)
		// server.getConnectors()[0]).setReuseAddress(false);
		server.setHandler(context);
		server.start();
	}
}
