/**
 * Copyright 2013-2017 the original author or authors.
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
package ch.rasc.embeddedtc.runner;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bootstrap {
	public static void main(String... args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException, ClassNotFoundException, URISyntaxException {

		URL bootstrapJarLocationURL = Bootstrap.class.getProtectionDomain().getCodeSource().getLocation();
		Path bootstrapJar = Paths.get(bootstrapJarLocationURL.toURI());
		Path runnerJarPath = bootstrapJar.resolveSibling("runner.jar");

		Files.deleteIfExists(runnerJarPath);
		
		try (InputStream is = Bootstrap.class.getResourceAsStream("/runner.jar")) {
			Files.copy(is, runnerJarPath);
		}
		
		try (URLClassLoader classLoader = new URLClassLoader(new URL[] { runnerJarPath.toUri().toURL() },
				ClassLoader.getSystemClassLoader().getParent())) {

			Thread.currentThread().setContextClassLoader(classLoader);
			
			Class<?> runnerClass = classLoader.loadClass("ch.rasc.embeddedtc.runner.Runner");
			Method mainMethod = runnerClass.getMethod("main", new Class[] { args.getClass() });

			mainMethod.invoke(null, new Object[] { args });
		}

	}

}