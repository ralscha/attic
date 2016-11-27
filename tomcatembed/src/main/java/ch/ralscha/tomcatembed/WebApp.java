package ch.ralscha.tomcatembed;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class WebApp {

	public static void main(String[] args) throws LifecycleException, ServletException {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		tomcat.setBaseDir(".");

		tomcat.addWebapp("/test", "webapp");

		tomcat.start();
		tomcat.getServer().await();
	}

}
