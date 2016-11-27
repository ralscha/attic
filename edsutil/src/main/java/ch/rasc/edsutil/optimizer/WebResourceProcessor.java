/**
 * Copyright 2013-2016 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.edsutil.optimizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.ServletContext;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DescriptiveResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import ch.rasc.edsutil.optimizer.config.VariableConfig;
import ch.rasc.edsutil.optimizer.config.WebResourceConfig;
import ch.rasc.edsutil.optimizer.graph.CircularReferenceException;
import ch.rasc.edsutil.optimizer.graph.Graph;
import ch.rasc.edsutil.optimizer.graph.Node;

public class WebResourceProcessor {

	private final static Logger log = LoggerFactory.getLogger("ch.rasc.edsutil");

	private static final String CSS_EXTENSION = "_css";

	private static final String JS_EXTENSION = "_js";

	private final static Pattern DEV_CODE_PATTERN = Pattern
			.compile("/\\* <debug> \\*/.*?/\\* </debug> \\*/", Pattern.DOTALL);

	private final static Pattern CSS_URL_PATTERN = Pattern.compile(
			"(.*?url.*?\\(\\s*'?)(.*?)(\\?.*?)??('?\\s*\\))", Pattern.CASE_INSENSITIVE);

	private final static String REQUIRES_PATTERN = "(?s)\\brequires\\s*?:\\s*?\\[.*?\\]\\s*?,";

	private final static String USES_PATTERN = "(?s)\\buses\\s*?:\\s*?\\[.*?\\]\\s*?,";

	private final static String JAVASCRIPT_TAG = "<script src=\"%s\"></script>";

	private final static String CSSLINK_TAG = "<link rel=\"stylesheet\" href=\"%s\">";

	private String webResourcesConfigName = "/webresources.yml";

	private String versionPropertiesName = "/version.properties";

	private final boolean production;

	private int cacheInSeconds = 31536000;

	private int cssLinebreakPos = 120;

	private int jsLinebreakPos = 120;

	private boolean jsCompressorMunge = false;

	private boolean jsCompressorVerbose = false;

	private boolean jsCompressorPreserveAllSemiColons = true;

	private boolean jsCompressordisableOptimizations = true;

	private String resourceServletPath = null;

	private final Set<Resource> ignoreJsResourceFromReordering = new HashSet<>();

	private final ErrorReporter errorReporter = new JavaScriptCompressorErrorReporter();

	private final ServletContext servletContext;

	private final Map<String, String> versionNumbers;

	public WebResourceProcessor(final ServletContext servletContext,
			final boolean production) {
		this.servletContext = servletContext;
		this.production = production;
		this.versionNumbers = readVersionNumber();
	}

	public void setResourceServletPath(String path) {
		if (path != null) {
			this.resourceServletPath = path.trim();
		}
		else {
			this.resourceServletPath = null;
		}
	}

	public void ignoreJsResourceFromReordering(Resource resource) {
		this.ignoreJsResourceFromReordering.add(resource);
	}

	public void setCacheInSeconds(int cacheInSeconds) {
		this.cacheInSeconds = cacheInSeconds;
	}

	public void setWebResourcesConfigName(final String webResourcesConfigName) {
		this.webResourcesConfigName = webResourcesConfigName;
	}

	public void setVersionPropertiesName(final String versionPropertiesName) {
		this.versionPropertiesName = versionPropertiesName;
	}

	public void setCssLinebreakPos(final int cssLinebreakPos) {
		this.cssLinebreakPos = cssLinebreakPos;
	}

	public void setJsLinebreakPos(final int jsLinebreakPos) {
		this.jsLinebreakPos = jsLinebreakPos;
	}

	public void setJsCompressorMunge(final boolean jsCompressorMunge) {
		this.jsCompressorMunge = jsCompressorMunge;
	}

	public void setJsCompressorVerbose(final boolean jsCompressorVerbose) {
		this.jsCompressorVerbose = jsCompressorVerbose;
	}

	public void setJsCompressorPreserveAllSemiColons(
			final boolean jsCompressorPreserveAllSemiColons) {
		this.jsCompressorPreserveAllSemiColons = jsCompressorPreserveAllSemiColons;
	}

	public void setJsCompressordisableOptimizations(
			final boolean jsCompressordisableOptimizations) {
		this.jsCompressordisableOptimizations = jsCompressordisableOptimizations;
	}

	public void process() throws IOException {

		Map<String, List<WebResource>> varResources = readVariableResources();
		Map<String, List<String>> linksAndScripts = minify(varResources, true);

		for (String var : linksAndScripts.keySet()) {
			StringBuilder sb = new StringBuilder();
			if (var.endsWith(JS_EXTENSION)) {
				for (String res : linksAndScripts.get(var)) {
					sb.append(String.format(JAVASCRIPT_TAG,
							this.servletContext.getContextPath() + res));
				}
			}
			else {
				for (String res : linksAndScripts.get(var)) {
					sb.append(String.format(CSSLINK_TAG,
							this.servletContext.getContextPath() + res));
				}
			}
			this.servletContext.setAttribute(var, sb.toString());
		}

	}

	public List<String> getJsAndCssResources() throws IOException {
		Map<String, List<WebResource>> varResources = readVariableResources();
		Map<String, List<String>> linksAndScripts = minify(varResources, false);

		List<String> jsResources = new ArrayList<>();
		List<String> cssResources = new ArrayList<>();

		for (String var : linksAndScripts.keySet()) {
			if (var.endsWith(JS_EXTENSION)) {
				jsResources.addAll(linksAndScripts.get(var));
			}
			else {
				cssResources.addAll(linksAndScripts.get(var));

			}
		}

		cssResources.addAll(jsResources);
		return cssResources;
	}

	private Map<String, List<String>> minify(Map<String, List<WebResource>> varResources,
			boolean addServlet) {

		Map<String, List<String>> linksAndScripts = new LinkedHashMap<>();

		for (String var : varResources.keySet()) {
			List<String> resources = new ArrayList<>();

			StringBuilder minifiedSource = new StringBuilder();

			boolean jsProcessing = var.endsWith(JS_EXTENSION);
			for (WebResource resource : varResources.get(var)) {
				if (resource.isMinify()) {

					try (InputStream lis = resource.getResource().getInputStream()) {
						String sourcecode = inputStream2String(lis,
								StandardCharsets.UTF_8);
						if (jsProcessing) {
							minifiedSource.append(minifyJs(cleanCode(sourcecode)))
									.append('\n');
						}
						else {
							minifiedSource.append(compressCss(
									changeImageUrls(this.servletContext.getContextPath(),
											sourcecode, resource.getPath())));
						}
					}
					catch (IOException ioe) {
						log.error("web resource processing: "
								+ resource.getResource().getDescription(), ioe);
					}

				}
				else {
					resources.add(resource.getResource().getDescription());
				}
			}

			if (minifiedSource.length() > 0) {
				byte[] content = minifiedSource.toString()
						.getBytes(StandardCharsets.UTF_8);

				if (jsProcessing) {
					String root = var.substring(0, var.length() - JS_EXTENSION.length());

					String crc = computeMD5andEncodeWithURLSafeBase64(content);
					String jsFileName = root + crc + ".js";
					String servletPath = constructServletPath(jsFileName);

					if (addServlet) {
						this.servletContext
								.addServlet(jsFileName, new ResourceServlet(content, crc,
										this.cacheInSeconds, "application/javascript"))
								.addMapping(servletPath);
					}

					resources.add(servletPath);

				}
				else {
					String root = var.substring(0, var.length() - CSS_EXTENSION.length());
					String crc = computeMD5andEncodeWithURLSafeBase64(content);
					String cssFileName = root + crc + ".css";
					String servletPath = constructServletPath(cssFileName);

					if (addServlet) {
						this.servletContext
								.addServlet(cssFileName,
										new ResourceServlet(content, crc,
												this.cacheInSeconds, "text/css"))
								.addMapping(servletPath);
					}

					resources.add(servletPath);
				}
			}

			if (!resources.isEmpty()) {
				linksAndScripts.put(var, resources);
			}
		}

		return linksAndScripts;

	}

	private Map<String, List<WebResource>> readVariableResources() throws IOException {

		try (InputStream is = new ClassPathResource(this.webResourcesConfigName)
				.getInputStream()) {
			Constructor constructor = new Constructor(VariableConfig.class);
			Yaml yaml = new Yaml(constructor);
			return StreamSupport.stream(yaml.loadAll(is).spliterator(), false)
					.map(e -> (VariableConfig) e).map(this::createWebResources)
					.flatMap(wr -> wr.stream())
					.collect(Collectors.groupingBy(WebResource::getVarName));
		}
	}

	private List<WebResource> createWebResources(VariableConfig variableConfig) {

		List<WebResource> webResources = new ArrayList<>();
		String varName = variableConfig.variable;

		for (WebResourceConfig config : variableConfig.resources) {
			String path = replaceVariables(config.path);

			if (!this.production && config.isDevScriptOrLink()) {
				DescriptiveResource resource = new DescriptiveResource(path);
				webResources.add(new WebResource(varName, path, resource, false));
			}
			else if (this.production && config.isProd()) {
				if (config.isProdScriptOrLink()) {
					DescriptiveResource resource = new DescriptiveResource(path);
					webResources.add(new WebResource(varName, path, resource, false));
				}
				else {
					try {
						boolean jsProcessing = varName.endsWith(JS_EXTENSION);
						List<Resource> enumeratedResources;
						String suffix = jsProcessing ? ".js" : ".css";
						if (StringUtils.hasText(config.classpath)) {
							enumeratedResources = enumerateResourcesFromClasspath(
									config.classpath, path, suffix);
						}
						else {
							enumeratedResources = enumerateResourcesFromWebapp(path,
									suffix);
						}
						if (jsProcessing && enumeratedResources.size() > 1) {
							enumeratedResources = reorder(enumeratedResources);
						}

						for (Resource resource : enumeratedResources) {
							String resourcePath = resource.getURL().toString();

							int pathIx = resourcePath.indexOf(path);
							if (pathIx != -1) {
								resourcePath = resourcePath.substring(pathIx);
							}

							webResources.add(new WebResource(varName, resourcePath,
									resource, true));
						}
					}
					catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		return webResources;
	}

	private String constructServletPath(String path) {
		if (StringUtils.hasText(this.resourceServletPath)) {
			if (!this.resourceServletPath.endsWith("/")) {
				return this.resourceServletPath + "/" + path;
			}
			return this.resourceServletPath + path;
		}

		return "/" + path;
	}

	private static List<Resource> enumerateResourcesFromClasspath(final String classpath,
			final String path, final String suffix) throws IOException {
		if (path.endsWith("/")) {
			PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
			String location = classpath + path + "**/*" + suffix;

			Resource[] resources = resourceResolver.getResources(location);
			return Arrays.asList(resources);
		}

		if (path.endsWith(suffix)) {
			return Collections.singletonList(new ClassPathResource(classpath + path));
		}

		return Collections.emptyList();
	}

	private List<Resource> enumerateResourcesFromWebapp(final String line,
			final String suffix) throws IOException {
		if (line.endsWith("/")) {
			ServletContextResourcePatternResolver resourceResolver = new ServletContextResourcePatternResolver(
					this.servletContext);
			String location = line + "**/*" + suffix;
			Resource[] resources = resourceResolver.getResources(location);
			return Arrays.asList(resources);
		}

		if (line.endsWith(suffix)) {
			return Collections
					.singletonList(new ServletContextResource(this.servletContext, line));
		}

		return Collections.emptyList();
	}

	private final static Pattern definePattern = Pattern
			.compile("Ext\\.define\\s*?\\(\\s*?['\"](.*?)['\"]");

	private final static Pattern extendPattern = Pattern
			.compile("extend\\s*?:\\s*?['\"](.*?)['\"]");

	private final static Pattern controllerPattern = Pattern
			.compile("controller\\s*?:\\s*?['\"](.*?)['\"]");

	private final static Pattern modelPattern = Pattern
			.compile("model\\s*?:\\s*?['\"](.*?)['\"]");

	private final static Pattern requiresPattern = Pattern
			.compile("(?s)requires\\s*?:\\s*?\\[(.*?)\\]");

	private final static Pattern usesPattern = Pattern
			.compile("(?s)uses\\s*?:\\s*?\\[(.*?)\\]");

	private final static Pattern requireUsePattern = Pattern
			.compile("(?s)['\"](.*?)['\"]");

	private List<Resource> reorder(List<Resource> resources) throws IOException {
		if (resources.isEmpty() || resources.size() == 1) {
			return resources;
		}

		Map<String, Resource> classToFileMap = new HashMap<>();
		Map<Resource, Set<String>> resourceRequires = new HashMap<>();
		Graph g = new Graph();

		for (Resource resource : resources) {

			if (this.ignoreJsResourceFromReordering.contains(resource)) {
				continue;
			}

			g.createNode(resource);

			try (InputStream lis = resource.getInputStream()) {

				Set<String> requires = new HashSet<>();

				String sourcecode = inputStream2String(lis, StandardCharsets.UTF_8);

				Matcher matcher = definePattern.matcher(sourcecode);
				if (matcher.find()) {
					classToFileMap.put(matcher.group(1), resource);
				}

				matcher = extendPattern.matcher(sourcecode);
				if (matcher.find()) {
					requires.add(matcher.group(1));
				}

				matcher = controllerPattern.matcher(sourcecode);
				if (matcher.find()) {
					requires.add(matcher.group(1));
				}

				matcher = modelPattern.matcher(sourcecode);
				if (matcher.find()) {
					requires.add(matcher.group(1));
				}

				matcher = requiresPattern.matcher(sourcecode);
				if (matcher.find()) {
					String all = matcher.group(1);
					matcher = requireUsePattern.matcher(all);
					while (matcher.find()) {
						requires.add(matcher.group(1));
					}
				}

				matcher = usesPattern.matcher(sourcecode);
				if (matcher.find()) {
					String all = matcher.group(1);
					matcher = requireUsePattern.matcher(all);
					while (matcher.find()) {
						requires.add(matcher.group(1));
					}
				}

				resourceRequires.put(resource, requires);

			}
			catch (IOException ioe) {
				log.error("web resource processing: " + resource, ioe);
			}
		}

		for (Resource key : resourceRequires.keySet()) {
			Node node = g.createNode(key);
			for (String r : resourceRequires.get(key)) {
				Resource rr = classToFileMap.get(r);
				if (rr != null) {
					node.addEdge(g.createNode(rr));
				}
			}
		}

		try {
			List<Node> resolved = g.resolveDependencies();
			for (Resource ignoredRes : this.ignoreJsResourceFromReordering) {
				resolved.add(0, new Node(ignoredRes));
			}

			return resolved.stream()
					.sorted((o1, o2) -> o1.getEdges().size() == 0 ? -1 : 0)
					.map(Node::getResource).collect(Collectors.toList());

		}
		catch (CircularReferenceException e) {
			log.error("circular reference", e);
			return null;
		}

	}

	private static String cleanCode(String sourcecode) {
		Matcher matcher = DEV_CODE_PATTERN.matcher(sourcecode);
		StringBuffer cleanCode = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(cleanCode, "");
		}
		matcher.appendTail(cleanCode);

		return cleanCode.toString().replaceAll(REQUIRES_PATTERN, "")
				.replaceAll(USES_PATTERN, "");
	}

	// private List<String> readAllLinesFromWebResourceConfigFile() {
	// try (InputStream is = new ClassPathResource(webResourcesConfigName)
	// .getInputStream()) {
	// return readAllLines(is, StandardCharsets.UTF_8);
	//
	// private static List<String> readAllLines(InputStream is, Charset cs)
	// throws IOException {
	// try (Reader inputStreamReader = new InputStreamReader(is, cs.newDecoder());
	// BufferedReader reader = new BufferedReader(inputStreamReader)) {
	// List<String> result = new ArrayList<>();
	// for (;;) {
	// String line = reader.readLine();
	// if (line == null) {
	// break;
	// }
	// result.add(line);
	// }
	// return result;
	// }
	// }
	// }
	// catch (IOException ioe) {
	// log.error("read lines from web resource config '" + webResourcesConfigName
	// + "'", ioe);
	// }
	// return Collections.emptyList();
	// }

	private static String inputStream2String(InputStream is, Charset cs)
			throws IOException {
		StringBuilder to = new StringBuilder();
		try (Reader from = new InputStreamReader(is, cs.newDecoder())) {
			CharBuffer buf = CharBuffer.allocate(0x800);
			while (from.read(buf) != -1) {
				buf.flip();
				to.append(buf);
				buf.clear();
			}
			return to.toString();
		}
	}

	private static String changeImageUrls(String contextPath, String cssSourceCode,
			String cssPath) {
		Matcher matcher = CSS_URL_PATTERN.matcher(cssSourceCode);
		StringBuffer sb = new StringBuffer();

		Path basePath = Paths.get(contextPath + cssPath);

		while (matcher.find()) {
			String url = matcher.group(2);
			url = url.trim();
			if (url.equals("#default#VML") || url.startsWith("data:")) {
				continue;
			}
			Path pa = basePath.resolveSibling(url).normalize();
			matcher.appendReplacement(sb,
					"$1" + pa.toString().replace("\\", "/") + "$3$4");
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String minifyJs(final String jsSourceCode)
			throws EvaluatorException, IOException {
		JavaScriptCompressor jsc = new JavaScriptCompressor(
				new StringReader(jsSourceCode), this.errorReporter);
		StringWriter sw = new StringWriter();
		jsc.compress(sw, this.jsLinebreakPos, this.jsCompressorMunge,
				this.jsCompressorVerbose, this.jsCompressorPreserveAllSemiColons,
				this.jsCompressordisableOptimizations);
		return sw.toString();

	}

	private String compressCss(final String css) throws EvaluatorException, IOException {
		CssCompressor cc = new CssCompressor(new StringReader(css));
		StringWriter sw = new StringWriter();
		cc.compress(sw, this.cssLinebreakPos);
		return sw.toString();
	}

	private String replaceVariables(String inputLine) {
		String processedLine = inputLine;
		for (Entry<String, String> entry : this.versionNumbers.entrySet()) {
			String var = "{" + entry.getKey() + "}";
			processedLine = processedLine.replace(var, entry.getValue());
		}
		return processedLine;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> readVersionNumber() {
		if (this.versionPropertiesName != null) {
			try (InputStream is = new ClassPathResource(this.versionPropertiesName)
					.getInputStream()) {
				Properties properties = new Properties();
				properties.load(is);
				return (Map) properties;
			}
			catch (IOException ioe) {
				log.error("read variables from property '" + this.versionPropertiesName
						+ "'", ioe);
			}
		}
		return Collections.emptyMap();
	}

	private static String computeMD5andEncodeWithURLSafeBase64(final byte[] content) {
		try {
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			md5Digest.update(content);
			byte[] md5 = md5Digest.digest();

			return Base64.getUrlEncoder().encodeToString(md5);

		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private final static class JavaScriptCompressorErrorReporter
			implements ErrorReporter {
		@Override
		public void warning(String message, String sourceName, int line,
				String lineSource, int lineOffset) {
			if (line < 0) {
				log.warn("JavaScriptCompressor warning: {}", message);
			}
			else {
				log.warn("JavaScriptCompressor warning: {}:{}:{}", line, lineOffset,
						message);
			}
		}

		@Override
		public void error(String message, String sourceName, int line, String lineSource,
				int lineOffset) {
			if (line < 0) {
				log.error("JavaScriptCompressor error: {}", message);
			}
			else {
				log.error("JavaScriptCompressor error: {}:{}:{}", line, lineOffset,
						message);
			}
		}

		@Override
		public EvaluatorException runtimeError(String message, String sourceName,
				int line, String lineSource, int lineOffset) {
			error(message, sourceName, line, lineSource, lineOffset);
			return new EvaluatorException(message);
		}
	}

}
