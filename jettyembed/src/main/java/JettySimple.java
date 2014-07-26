import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class JettySimple {
	public static void main(String[] args) throws Exception {
		WebAppContext context = new WebAppContext("./src/main/webapp", "/");

		context.setConfigurations(new Configuration[] { new AnnotationConfiguration(),
				new WebXmlConfiguration(), new WebInfConfiguration(),
				new MetaInfConfiguration(), new FragmentConfiguration(), });

		Server server = new Server(8080);
		server.setHandler(context);
		server.start();
	}
}
