package ch.rasc.extclassgenerator;

import static java.lang.Thread.currentThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Pattern;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.SelectorUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This goal generate code based on Java classes.
 * 
 * @goal generate
 * @phase generate-sources
 */
public class ExtModelGeneratorMojo extends AbstractMojo {

	private final static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
	}

	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * File to generate ext model files. If not defined assumes all classes must be included
	 * 
	 * @parameter
	 */
	private String[] includeJavaClasses;

	/**
	 * File to exclude from ext model generation. If not defined, assumes no exclusions
	 * 
	 * @parameter
	 */
	private String[] excludeJavaClasses;

	/**
	 * @parameter default-value="${project.build.directory}/src/main/webapp"
	 */
	private File outputDirectory;

	@Override
	public void execute() throws MojoExecutionException {
		setUp();

		OutputConfig outputConfig = new OutputConfig();

		// outputConfig.setDebug(!"false".equals(processingEnv.getOptions().get(OPTION_DEBUG)));
		boolean createBaseAndSubclass = false;// "true".equals(processingEnv.getOptions().get(OPTION_CREATEBASEANDSUBCLASS));
		//
		// String outputFormatString =
		// processingEnv.getOptions().get(OPTION_OUTPUTFORMAT);
		// outputConfig.setOutputFormat(OutputFormat.EXTJS4);
		// if (StringUtils.hasText(outputFormatString)) {
		// if (OutputFormat.TOUCH2.name().equalsIgnoreCase(outputFormatString))
		// {
		// outputConfig.setOutputFormat(OutputFormat.TOUCH2);
		// }
		// }
		//
		// String includeValidationString =
		// processingEnv.getOptions().get(OPTION_INCLUDEVALIDATION);
		// outputConfig.setIncludeValidation(IncludeValidation.NONE);
		// if (StringUtils.hasText(includeValidationString)) {
		// if
		// (IncludeValidation.ALL.name().equalsIgnoreCase(includeValidationString))
		// {
		// outputConfig.setIncludeValidation(IncludeValidation.ALL);
		// } else if
		// (IncludeValidation.BUILTIN.name().equalsIgnoreCase(includeValidationString))
		// {
		// outputConfig.setIncludeValidation(IncludeValidation.BUILTIN);
		// }
		// }
		//
		// outputConfig.setUseSingleQuotes("true".equals(processingEnv.getOptions().get(OPTION_USESINGLEQUOTES)));
		// outputConfig.setSurroundApiWithQuotes("true".equals(processingEnv.getOptions()
		// .get(OPTION_SURROUNDAPIWITHQUOTES)));

		ClassLoader cl = currentThread().getContextClassLoader();

		try {
			ClassLoader theClassLoader = this.initializeClassLoader();
			currentThread().setContextClassLoader(theClassLoader);

			for (Map.Entry<String, File> classFile : getFilesToGenerator().entrySet()) {
				String className = classFile.getKey();
				try {
					Class<?> classToGenerate = theClassLoader.loadClass(className);
					Model modelAnnotation = classToGenerate.getAnnotation(Model.class);
					if (modelAnnotation != null) {

						String code = ModelGenerator.generateJavascript(classToGenerate, outputConfig);

						String modelName = modelAnnotation.value();
						String fileName;
						String packageName = "";
						if (StringUtils.hasText(modelName)) {
							int lastDot = modelName.lastIndexOf('.');
							if (lastDot != -1) {
								fileName = modelName.substring(lastDot + 1);
								int firstDot = modelName.indexOf('.');
								if (firstDot < lastDot) {
									packageName = modelName.substring(firstDot + 1, lastDot);
								}
							} else {
								fileName = modelName;
							}
						} else {
							fileName = classToGenerate.getName();// typeElement.getSimpleName().toString();
						}

						if (createBaseAndSubclass) {
							code = code.replaceFirst("(Ext.define\\([\"'].+?)([\"'],)", "$1Base$2");
							File out = createFile(packageName, fileName + "Base.js");
							try (OutputStream os = new FileOutputStream(out)) {
								os.write(code.getBytes(ModelGenerator.UTF8_CHARSET));
							}

							out = createFile(packageName, fileName + ".js");
							if (!out.exists()) {
								String subClassCode = generateSubclassCode(classToGenerate, outputConfig);
								out = createFile(packageName, fileName + ".js");
								try (OutputStream os = new FileOutputStream(out)) {
									os.write(subClassCode.getBytes(ModelGenerator.UTF8_CHARSET));
								}

							}

						} else {
							File out = createFile(packageName, fileName + ".js");
							try (OutputStream os = new FileOutputStream(out)) {
								os.write(code.getBytes(ModelGenerator.UTF8_CHARSET));
							}
						}

					}

				} catch (Exception e) {
					// e.printStackTrace();
					// request.getLogger().error( getStackTrace( e ) );
					// throw new GenerationException( "Fail to generate class ["
					// + className + "]", e );
				}
			}

		}

		finally {
			currentThread().setContextClassLoader(cl);
		}
	}

	private static String generateSubclassCode(Class<?> clazz, OutputConfig outputConfig) {
		Model modelAnnotation = clazz.getAnnotation(Model.class);

		String name;
		if (modelAnnotation != null && StringUtils.hasText(modelAnnotation.value())) {
			name = modelAnnotation.value();
		} else {
			name = clazz.getName();
		}

		Map<String, Object> modelObject = new LinkedHashMap<String, Object>();
		modelObject.put("extend", name + "Base");

		StringBuilder sb = new StringBuilder(100);
		sb.append("Ext.define(\"").append(name).append("\",");
		if (outputConfig.isDebug()) {
			sb.append("\n");
		}

		String configObjectString;
		try {
			if (outputConfig.isDebug()) {
				configObjectString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelObject);
			} else {
				configObjectString = mapper.writeValueAsString(modelObject);
			}

		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		sb.append(configObjectString);
		sb.append(");");

		if (outputConfig.isUseSingleQuotes()) {
			return sb.toString().replace('"', '\'');
		}

		return sb.toString();

	}

	private File createFile(String packageName, String fileName) {
		if (packageName != null && !packageName.trim().isEmpty()) {
			String[] parts = packageName.split(Pattern.quote("."));

			File out = outputDirectory;
			for (String part : parts) {
				out = new File(out, part);
				if (!out.exists()) {
					out.mkdir();
				}
			}
			return new File(out, fileName);
		}

		return new File(outputDirectory, fileName);
	}

	private void setUp() {
		if (includeJavaClasses == null) {
			includeJavaClasses = new String[] { "*" };
		}

		if (!outputDirectory.exists()) {
			outputDirectory.mkdirs();
		}
	}

	private final Map<String, File> getFilesToGenerator() throws MojoExecutionException {
		List<String> classpaths = getDirectDependencies();
		Map<String, File> classes = new HashMap<String, File>();

		for (String fileName : classpaths) {
			File file = new File(fileName).getAbsoluteFile();

			if (file.isDirectory()) {
				DirectoryScanner ds = new DirectoryScanner();
				ds.setBasedir(file);
				ds.setIncludes(new String[] { "**/*.class" });
				ds.scan();

				for (String classFileName : ds.getIncludedFiles()) {
					String className = classFileName.replace(File.separatorChar, '.');
					className = className.substring(0, className.length() - 6);

					if (matchWildCard(className, includeJavaClasses) && !matchWildCard(className, excludeJavaClasses)) {
						classes.put(className, new File(file, classFileName));
					}
				}
			} else {

				try {
					JarInputStream jar = new JarInputStream(new FileInputStream(file));

					JarEntry jarEntry;
					while (true) {
						jarEntry = jar.getNextJarEntry();

						if (jarEntry == null) {
							break;
						}

						String className = jarEntry.getName();

						if (jarEntry.isDirectory() || !className.endsWith(".class")) {
							continue;
						}

						className = className.replace('/', '.');
						className = className.substring(0, className.length() - 6);

						if (matchWildCard(className, includeJavaClasses)
								&& !matchWildCard(className, excludeJavaClasses)) {
							classes.put(className, file);
						}
					}
				} catch (IOException e) {
					throw new MojoExecutionException("Error on classes resolve", e);
				}
			}
		}

		return classes;
	}

	private boolean matchWildCard(String className, String... wildCards) {
		if (wildCards == null) {
			return false;
		}

		for (String wildCard : wildCards) {
			if (className.equals(wildCard)) {
				return true;
			}

			if (SelectorUtils.matchPath(wildCard, className)) {
				return true;
			}
		}

		return false;
	}

	private ClassLoader initializeClassLoader() throws MojoExecutionException {
		List<String> classpaths = getClasspath();

		try {
			List<URL> classpathsUrls = new ArrayList<URL>();

			// add all the jars to the new child realm
			for (String path : classpaths) {
				URL url = new File(path).toURI().toURL();
				classpathsUrls.add(url);
			}

			return new URLClassLoader(classpathsUrls.toArray(new URL[0]), currentThread().getContextClassLoader());
		} catch (MalformedURLException e) {
			throw new MojoExecutionException("Unable to get dependency URL", e);
		}
	}

	private List<String> getClasspath() throws MojoExecutionException {
		List<String> classpaths;
		try {
			classpaths = project.getCompileClasspathElements();
			classpaths.remove(project.getBuild().getOutputDirectory());
		} catch (DependencyResolutionRequiredException e) {
			throw new MojoExecutionException("Failed to find dependencies", e);
		}
		return classpaths;
	}

	private List<String> getDirectDependencies() throws MojoExecutionException {
		List<String> classpaths = new ArrayList<String>();
		Set<Artifact> artifacts = project.getDependencyArtifacts();
		for (Artifact artifact : artifacts) {
			if ("jar".equals(artifact.getType()) || "maven-plugin".equals(artifact.getType())) {
				classpaths.add(artifact.getFile().getAbsolutePath());
			}
		}

		try {
			for (String cce : project.getCompileClasspathElements()) {
				classpaths.add(cce);
			}
		} catch (DependencyResolutionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return classpaths;
	}

}