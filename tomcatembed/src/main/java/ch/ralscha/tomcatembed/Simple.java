package ch.ralscha.tomcatembed;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Simple {

	public static void main(String[] args) throws ServletException, LifecycleException {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		tomcat.setBaseDir(".");

		Context ctx = tomcat.addWebapp("/examples", "examples");
		Tomcat.addServlet(ctx, "helloWorldServlet",
				"ch.ralscha.tomcatembed.HelloWorldServlet");

		ctx.addServletMapping("/helloworld", "helloWorldServlet");

		tomcat.start();
		tomcat.getServer().await();

	}

}
