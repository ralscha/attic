/**
 * Copyright 2010-2012 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.Tomcat;

public class WCTomcat {

	public static void main(String[] args) {

		Tomcat tomcat = new Tomcat();

		String tempDirectory = new File(".", "/temp/tomcat.8080").getAbsolutePath();
		tomcat.setBaseDir(tempDirectory);

		String contextDir = new File(".").getAbsolutePath() + "/web";

		final Context ctx;
		try {
			ctx = tomcat.addWebapp("", contextDir);
		} catch (ServletException e) {
			throw new RuntimeException(e);
		}

		tomcat.enableNaming();

		ContextEnvironment env = new ContextEnvironment();
		env.setName("app/indexDir");
		env.setValue("e:/temp/cal");
		env.setType("java.lang.String");
		env.setOverride(false);
		ctx.getNamingResources().addEnvironment(env);

		ContextResource res = new ContextResource();
		res.setType("javax.sql.DataSource");
		res.setAuth("Container");
		res.setName("jdbc/cal");
		res.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");

		res.setProperty("driverClassName", "net.sourceforge.jtds.jdbc.Driver");
		res.setProperty("url", "jdbc:jtds:sqlserver://localhost/cal");
		res.setProperty("username", "sa");
		res.setProperty("password", "papa8gei.");
		res.setProperty("initialSize", "2");
		res.setProperty("minIdle", "2");
		res.setProperty("maxIdle", "4");
		res.setProperty("maxActive", "10");
		res.setProperty("maxWait", "10000");
		res.setProperty("defaultAutoCommit", "false");

		ctx.getNamingResources().addResource(res);

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			throw new RuntimeException(e);
		}

		((StandardManager) ctx.getManager()).setPathname("");

		tomcat.getServer().await();

	}
}
