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
package ch.rasc.embeddedtc.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.jar.Manifest;
import org.codehaus.plexus.archiver.jar.ManifestException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import ch.rasc.embeddedtc.runner.Bootstrap;
import ch.rasc.embeddedtc.runner.Runner;

@Mojo(name = "package-tcwar", defaultPhase = LifecyclePhase.PACKAGE)
public class PackageTcWarMojo extends AbstractMojo {

	@Component
	private MavenProject project;

	@Component
	private RepositorySystem repoSystem;

	@Parameter(defaultValue = "${repositorySystemSession}")
	private RepositorySystemSession repoSession;

	@Parameter(defaultValue = "${plugin.artifacts}", required = true)
	private List<Artifact> pluginArtifacts;

	@Parameter(defaultValue = "${project.build.directory}", required = true)
	private String buildDirectory;

	@Parameter(defaultValue = "${project.artifactId}-${project.version}-embeddedtc.jar", required = true)
	private String finalName;

	@Parameter(required = false)
	private String includeTcNativeWin32;

	@Parameter(required = false)
	private String includeTcNativeWin64;

	@Parameter(defaultValue = "true", required = true)
	private boolean includeJSPSupport;

	@Parameter(defaultValue = "false", required = true)
	private boolean useBootstrap;

	@Parameter
	private List<Dependency> extraDependencies;

	@Parameter
	private List<Dependency> extraWars;

	@Override
	public void execute() throws MojoExecutionException {

		Path warExecFile = Paths.get(buildDirectory, finalName);
		try {
			Files.deleteIfExists(warExecFile);
			Files.createDirectories(warExecFile.getParent());

			try (OutputStream os = Files.newOutputStream(warExecFile);
					ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(
							ArchiveStreamFactory.JAR, os)) {

				// If project is a war project add the war to the project
				if ("war".equalsIgnoreCase(project.getPackaging())) {
					File projectArtifact = project.getArtifact().getFile();
					if (projectArtifact != null && Files.exists(projectArtifact.toPath())) {
						aos.putArchiveEntry(new JarArchiveEntry(projectArtifact.getName()));
						try (InputStream is = Files.newInputStream(projectArtifact.toPath())) {
							IOUtils.copy(is, aos);
						}
						aos.closeArchiveEntry();
					}
				}

				// Add additional wars into the jar
				if (extraWars != null) {
					for (Dependency extraWarDependency : extraWars) {
						ArtifactRequest request = new ArtifactRequest();
						request.setArtifact(new DefaultArtifact(extraWarDependency.getGroupId(), extraWarDependency
								.getArtifactId(), extraWarDependency.getType(), extraWarDependency.getVersion()));

						ArtifactResult result;
						try {
							result = repoSystem.resolveArtifact(repoSession, request);
						} catch (ArtifactResolutionException e) {
							throw new MojoExecutionException(e.getMessage(), e);
						}

						File extraWarFile = result.getArtifact().getFile();
						aos.putArchiveEntry(new JarArchiveEntry(extraWarFile.getName()));
						try (InputStream is = Files.newInputStream(extraWarFile.toPath())) {
							IOUtils.copy(is, aos);
						}
						aos.closeArchiveEntry();

					}
				}

				Set<String> includeArtifacts = new HashSet<>();
				includeArtifacts.add("org.apache.tomcat:tomcat-jdbc");
				includeArtifacts.add("org.apache.tomcat.embed:tomcat-embed-core");
				includeArtifacts.add("org.apache.tomcat.embed:tomcat-embed-logging-juli");
				includeArtifacts.add("org.yaml:snakeyaml");
				includeArtifacts.add("com.beust:jcommander");

				if (includeJSPSupport) {
					includeArtifacts.add("org.apache.tomcat.embed:tomcat-embed-jasper");
					includeArtifacts.add("ecj:ecj");
				}

				for (Artifact pluginArtifact : pluginArtifacts) {
					String artifactName = pluginArtifact.getGroupId() + ":" + pluginArtifact.getArtifactId();
					if (includeArtifacts.contains(artifactName)) {
						try (JarFile jarFile = new JarFile(pluginArtifact.getFile())) {
							extractJarToArchive(jarFile, aos);
						}
					}
				}

				if (extraDependencies != null) {
					for (Dependency dependency : extraDependencies) {

						ArtifactRequest request = new ArtifactRequest();
						request.setArtifact(new DefaultArtifact(dependency.getGroupId(), dependency.getArtifactId(),
								dependency.getType(), dependency.getVersion()));

						ArtifactResult result;
						try {
							result = repoSystem.resolveArtifact(repoSession, request);
						} catch (ArtifactResolutionException e) {
							throw new MojoExecutionException(e.getMessage(), e);
						}

						try (JarFile jarFile = new JarFile(result.getArtifact().getFile())) {
							extractJarToArchive(jarFile, aos);
						}
					}
				}

				if (includeJSPSupport) {
					addFile(aos, "/conf/web.xml", "conf/web.xml");
				} else {
					addFile(aos, "/conf/web_wo_jsp.xml", "conf/web.xml");
				}
				addFile(aos, "/conf/logging.properties", "conf/logging.properties");

				if (includeTcNativeWin32 != null) {
					aos.putArchiveEntry(new JarArchiveEntry("tcnative-1.dll.32"));
					Files.copy(Paths.get(includeTcNativeWin32), aos);
					aos.closeArchiveEntry();
				}

				if (includeTcNativeWin64 != null) {
					aos.putArchiveEntry(new JarArchiveEntry("tcnative-1.dll.64"));
					Files.copy(Paths.get(includeTcNativeWin64), aos);
					aos.closeArchiveEntry();
				}

				String[] runnerClasses = { "ch.rasc.embeddedtc.runner.CheckConfig$CheckConfigOptions",
						"ch.rasc.embeddedtc.runner.CheckConfig", "ch.rasc.embeddedtc.runner.Config",
						"ch.rasc.embeddedtc.runner.Context", "ch.rasc.embeddedtc.runner.DeleteDirectory",
						"ch.rasc.embeddedtc.runner.ObfuscateUtil$ObfuscateOptions",
						"ch.rasc.embeddedtc.runner.ObfuscateUtil", "ch.rasc.embeddedtc.runner.Runner$1",
						"ch.rasc.embeddedtc.runner.Runner$2", "ch.rasc.embeddedtc.runner.Runner$StartOptions",
						"ch.rasc.embeddedtc.runner.Runner" };

				for (String rc : runnerClasses) {
					String classAsPath = rc.replace('.', '/') + ".class";

					try (InputStream is = getClass().getResourceAsStream("/" + classAsPath)) {
						aos.putArchiveEntry(new JarArchiveEntry(classAsPath));
						IOUtils.copy(is, aos);
						aos.closeArchiveEntry();
					}
				}

				if (!useBootstrap) {
					Manifest manifest = new Manifest();

					Manifest.Attribute mainClassAtt = new Manifest.Attribute();
					mainClassAtt.setName("Main-Class");
					mainClassAtt.setValue(Runner.class.getName());
					manifest.addConfiguredAttribute(mainClassAtt);

					aos.putArchiveEntry(new JarArchiveEntry("META-INF/MANIFEST.MF"));
					manifest.write(aos);
					aos.closeArchiveEntry();
				}
				
				aos.putArchiveEntry(new JarArchiveEntry(Runner.TIMESTAMP_FILENAME));
				aos.write(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
				aos.closeArchiveEntry();

			}

			if (useBootstrap) {
				Path runnerJar = warExecFile.resolveSibling("runner.jar");
				Files.deleteIfExists(runnerJar);
				Files.move(warExecFile, runnerJar);

				Path bootstrapJar = Paths.get(buildDirectory, finalName);

				try (OutputStream os = Files.newOutputStream(bootstrapJar);
						ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(
								ArchiveStreamFactory.JAR, os)) {

					String classAsPath = Bootstrap.class.getName().replace('.', '/') + ".class";

					try (InputStream is = getClass().getResourceAsStream("/" + classAsPath)) {
						aos.putArchiveEntry(new JarArchiveEntry(classAsPath));
						IOUtils.copy(is, aos);
						aos.closeArchiveEntry();
					}

					Manifest manifest = new Manifest();

					Manifest.Attribute mainClassAtt = new Manifest.Attribute();
					mainClassAtt.setName("Main-Class");
					mainClassAtt.setValue(Bootstrap.class.getName());
					manifest.addConfiguredAttribute(mainClassAtt);

					aos.putArchiveEntry(new JarArchiveEntry("META-INF/MANIFEST.MF"));
					manifest.write(aos);
					aos.closeArchiveEntry();

					aos.putArchiveEntry(new JarArchiveEntry(runnerJar.getFileName().toString()));
					try (InputStream is = Files.newInputStream(runnerJar)) {
						IOUtils.copy(is, aos);
					}
					aos.closeArchiveEntry();
				}

			}

		} catch (IOException | ArchiveException | ManifestException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void addFile(ArchiveOutputStream aos, String from, String to) throws IOException {
		aos.putArchiveEntry(new JarArchiveEntry(to));
		IOUtils.copy(getClass().getResourceAsStream(from), aos);
		aos.closeArchiveEntry();
	}

	private static void extractJarToArchive(JarFile file, ArchiveOutputStream aos) throws IOException {
		Enumeration<? extends JarEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
			JarEntry j = entries.nextElement();
			if (!"META-INF/MANIFEST.MF".equals(j.getName())) {
				aos.putArchiveEntry(new JarArchiveEntry(j.getName()));
				IOUtils.copy(file.getInputStream(j), aos);
				aos.closeArchiveEntry();
			}
		}
	}
}