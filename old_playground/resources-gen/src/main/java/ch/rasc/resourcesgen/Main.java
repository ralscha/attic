package ch.rasc.resourcesgen;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.yaml.snakeyaml.Yaml;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArgumentParser parser = ArgumentParsers.newArgumentParser("resources-gen")
				.defaultHelp(true).description("Creates maven packages of resourcejars");
		parser.addArgument("file").nargs(1).required(true)
				.help("Specify the configuration file");
		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
			if (ns == null) {
				parser.printHelp();
				System.exit(1);
				return;
			}
		}
		catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
			return;
		}

		String configFile = ns.<String>getList("file").iterator().next();

		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = null;
		try (InputStream is = Main.class.getResourceAsStream("/pom.mustache");
				InputStreamReader isr = new InputStreamReader(is)) {
			try {
				mustache = mf.compile(isr, "pom.xml");
			}
			catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
				return;
			}
		}
		if (mustache == null) {
			System.exit(1);
			return;
		}

		Map<String, Object> config;
		try (FileReader fr = new FileReader(configFile)) {
			Yaml yaml = new Yaml();
			config = (Map<String, Object>) yaml.load(fr);
		}

		Map<String, String> files = (Map<String, String>) config.get("files");
		Map<String, String> mappings = (Map<String, String>) config.get("mappings");
		if (mappings == null) {
			mappings = Collections.emptyMap();
		}

		String sourcedir = (String) config.get("sourcedir");
		String version = (String) config.get("version");
		String groupId = (String) config.get("groupId");
		String baseartifactId = (String) config.get("baseartifactId");
		String destdir = (String) config.get("destdir");

		Util.delete(Paths.get(destdir));

		Path srcDirPath = Paths.get(sourcedir);

		Map<String, Object> scopes = new HashMap<>();
		scopes.put("version", version);
		scopes.put("groupId", groupId);

		Set<Path> projectDirs = new TreeSet<>();

		for (Map.Entry<String, String> entry : files.entrySet()) {
			String fileName = entry.getKey();
			String artifactId = entry.getValue();
			if (artifactId.equals("core")) {
				artifactId = baseartifactId;
			}
			else {
				artifactId = baseartifactId + "-" + artifactId;
			}

			scopes.put("artifactId", artifactId);

			String artifactIdVersion = artifactId + "-" + version;

			Path destDirPath = Paths.get(destdir, artifactIdVersion,
					"src/main/resources/META-INF/resources", "resources", baseartifactId,
					version);
			Files.createDirectories(destDirPath);

			projectDirs.add(Paths.get(destdir, artifactIdVersion));

			Path pomFile = Paths.get(destdir, artifactIdVersion, "pom.xml");
			if (!Files.exists(pomFile)) {
				try (FileWriter fw = new FileWriter(pomFile.toFile())) {
					mustache.execute(fw, scopes);
				}
			}

			Path srcPath = srcDirPath.resolve(fileName);

			String mappedFileName = mappings.get(fileName);
			if (mappedFileName == null) {
				mappedFileName = fileName;
			}

			Path destPath = destDirPath.resolve(mappedFileName);

			if (!Files.isDirectory(srcPath) && destPath.getNameCount() > 1) {
				Files.createDirectories(destPath.subpath(0, destPath.getNameCount() - 1));
			}
			else if (destPath.getNameCount() > 1) {
				Files.createDirectories(destPath);
			}

			Util.copy(srcPath, destPath);

		}

		StringBuilder sb = new StringBuilder();
		for (Path path : projectDirs) {
			sb.append("cd ");
			sb.append(path.toAbsolutePath().toString());
			sb.append("\n");
			sb.append("call mvn clean deploy");
			sb.append("\n");
			sb.append("cd ");
			sb.append(Paths.get(".").toAbsolutePath());
			sb.append("\n");
			sb.append("\n");
		}

		Path deployPath = Paths.get("deploy.bat");
		Files.deleteIfExists(deployPath);
		Files.write(deployPath, sb.toString().getBytes());

	}

}
