import java.io.IOException;
import java.net.ServerSocket;

import org.eclipse.jetty.plus.jndi.EnvEntry;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.h2.jdbcx.JdbcDataSource;

public class JettyWithJndi {
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

		context.setConfigurations(new Configuration[] {
				new org.eclipse.jetty.webapp.WebInfConfiguration(),
				new org.eclipse.jetty.webapp.WebXmlConfiguration(),
				new org.eclipse.jetty.webapp.MetaInfConfiguration(),
				new org.eclipse.jetty.webapp.FragmentConfiguration(),
				new org.eclipse.jetty.plus.webapp.EnvConfiguration(),
				new org.eclipse.jetty.plus.webapp.PlusConfiguration(),
				new org.eclipse.jetty.webapp.JettyWebXmlConfiguration() });

		context.addBean(new EnvEntry("aSimpleEntry", "theValue"));

		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUser("sa");
		dataSource.setURL("jdbc:h2:~/myDb");
		context.addBean(new org.eclipse.jetty.plus.jndi.Resource("jdbc/ds", dataSource));

		Server server = new Server(port);
		server.setHandler(context);
		server.start();
	}
}
