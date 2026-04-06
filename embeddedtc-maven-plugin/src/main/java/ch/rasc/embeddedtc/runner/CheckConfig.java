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

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

public class CheckConfig {

	public static void check(CheckConfigOptions checkConfigOptions) {

		try {
			Path configFile;
			String pathToConfigFile = checkConfigOptions.configFile != null
					&& !checkConfigOptions.configFile.isEmpty()
							? checkConfigOptions.configFile.get(0)
							: null;

			URL url = Runner.class.getProtectionDomain().getCodeSource().getLocation();
			Path myJar = Paths.get(url.toURI());
			Path myJarDir = myJar.getParent();

			if (pathToConfigFile != null) {
				configFile = Paths.get(pathToConfigFile);
				if (!configFile.isAbsolute()) {
					configFile = myJarDir.resolve(pathToConfigFile);
				}
			}
			else {
				configFile = myJarDir.resolve("config.yaml");
			}

			if (Files.exists(configFile)) {
				try (InputStream is = Files.newInputStream(configFile)) {
					Yaml yaml = new Yaml();
					@SuppressWarnings("unused")
					Config config = yaml.loadAs(is, Config.class);

					// loading checks for syntax errors like existing tab
					// characters.
					// todo
					// Add some more checks like printing a warning if there is
					// no connector.
					// Or if a numberfield contains characters
					System.out.printf("Config file %s is OK", configFile);
				}

			}
			else {
				System.out.printf("Config file %s does not exists\n", configFile);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Parameters(commandDescription = "Checks the config file")
	static class CheckConfigOptions {
		@Parameter(required = false, arity = 1, description = "absolutePathToConfigFile")
		List<String> configFile;
	}
}
