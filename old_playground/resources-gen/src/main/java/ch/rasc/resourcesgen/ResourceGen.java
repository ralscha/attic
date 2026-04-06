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
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import ch.rasc.resourcesgen.config.Artifact;
import ch.rasc.resourcesgen.config.Config;
import ch.rasc.resourcesgen.config.Mapping;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ResourceGen {

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

		String configFile = ns.<String>getList("file").iterator().next();

		Config config = null;
		try (FileReader fr = new FileReader(configFile)) {
			Yaml yaml = new Yaml();
			config = yaml.loadAs(fr, Config.class);
		}

		Map<String, Object> mustacheScope = new HashMap<>();
		mustacheScope.put("groupId", config.groupId);
		mustacheScope.put("version", config.version);

		Path destDir = Paths.get(config.destDir);
		Path srcDir = Paths.get(config.sourceDir);
		String baseArtifactId = config.baseArtifactId;

		Util.delete(destDir);

		Set<Path> projectDirs = new TreeSet<>();

		for (Artifact artifact : config.artifacts) {
			mustacheScope.put("artifactId", artifact.artifactId);

			String artifactIdVersion = artifact.artifactId + "-" + config.version;
			Path destDirPath = Paths.get(config.destDir, artifactIdVersion,
					"src/main/resources/META-INF/resources", "resources", baseArtifactId,
					config.version);
			Files.createDirectories(destDirPath);

			projectDirs.add(Paths.get(config.destDir, artifactIdVersion));

			Path pomFile = Paths.get(config.destDir, artifactIdVersion, "pom.xml");
			if (!Files.exists(pomFile)) {
				try (FileWriter fw = new FileWriter(pomFile.toFile())) {
					mustache.execute(fw, mustacheScope);
				}
			}

			for (Mapping mapping : artifact.mappings) {
				Path srcPath = srcDir.resolve(mapping.from);
				Path destPath = destDirPath.resolve(mapping.to);

				if (!Files.isDirectory(srcPath) && destPath.getNameCount() > 1) {
					Files.createDirectories(
							destPath.subpath(0, destPath.getNameCount() - 1));
				}
				else if (destPath.getNameCount() > 1) {
					Files.createDirectories(destPath);
				}

				Predicate<Path> fileFilter = null;
				if ("locale".equals(mapping.parameter)) {
					fileFilter = p -> !p.getFileName().toString().contains("-debug");
				}
				else if ("localedebug".equals(mapping.parameter)) {
					fileFilter = p -> p.getFileName().toString().contains("-debug");
				}

				Util.copy(srcPath, destPath, fileFilter);

				// remove all occurrences of '-debug'
				if ("fixcssinclude".equals(mapping.parameter)) {
					List<String> newLines = Files.lines(destPath)
							.map(line -> line.replace("-debug", ""))
							.collect(Collectors.toList());

					Files.write(destPath, newLines, StandardOpenOption.WRITE,
							StandardOpenOption.TRUNCATE_EXISTING);
				}

			}
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
