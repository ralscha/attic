import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.FileDirContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StartTomcat {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		int port = 8080;

		try (ServerSocket srv = new ServerSocket(port)) {
			srv.close();
		}
		catch (IOException e) {
			System.out.println("PORT " + port + " ALREADY IN USE");
			return;
		}

		Tomcat tomcat = new Tomcat();

		tomcat.setPort(8080);

		tomcat.setBaseDir(".");
		File currentDir = new File(".");
		Context ctx = tomcat.addWebapp("/",
				currentDir.getAbsolutePath() + "/src/main/webapp");
		ctx.setResources(new FileDirContext());

		List<Artifact> includeOnlyArtifact = new ArrayList<>();
		includeOnlyArtifact.add(new Artifact("resources", "demo"));

		List<File> resourceUrls = findResourceUrls(includeOnlyArtifact);

		for (File resFile : resourceUrls) {
			ctx.addResourceJarUrl(new URL("jar:" + resFile.toURI() + "!/"));
		}

		tomcat.start();

		System.out.println(
				"Tomcat Startup Time: " + (System.currentTimeMillis() - start) + " ms");
		System.out.println("Tomcat running on " + port);

		tomcat.getServer().await();

	}

	private static List<File> findResourceUrls(List<Artifact> includeOnlyArtifacts)
			throws ParserConfigurationException, SAXException, IOException {

		File homeDir = new File(System.getProperty("user.home"));

		List<File> jars = new ArrayList<>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("./pom.xml"));

		Map<String, String> properties = new HashMap<>();
		NodeList propertiesNodeList = doc.getElementsByTagName("properties");
		if (propertiesNodeList != null && propertiesNodeList.item(0) != null) {
			NodeList propertiesChildren = propertiesNodeList.item(0).getChildNodes();
			for (int i = 0; i < propertiesChildren.getLength(); i++) {
				Node node = propertiesChildren.item(i);
				if (node instanceof Element) {
					properties.put("${" + node.getNodeName() + "}",
							stripWhitespace(node.getTextContent()));
				}
			}
		}

		NodeList nodeList = doc.getElementsByTagName("dependency");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element node = (Element) nodeList.item(i);
			String groupId = node.getElementsByTagName("groupId").item(0)
					.getTextContent();
			String artifact = node.getElementsByTagName("artifactId").item(0)
					.getTextContent();
			String version = node.getElementsByTagName("version").item(0)
					.getTextContent();

			groupId = stripWhitespace(groupId);
			artifact = stripWhitespace(artifact);
			version = stripWhitespace(version);

			groupId = resolveProperty(groupId, properties);
			artifact = resolveProperty(artifact, properties);
			version = resolveProperty(version, properties);

			if (isIncluded(includeOnlyArtifacts, groupId, artifact)) {

				String scope = null;
				NodeList scopeNode = node.getElementsByTagName("scope");
				if (scopeNode != null && scopeNode.item(0) != null) {
					scope = stripWhitespace(scopeNode.item(0).getTextContent());
				}

				if (scope == null || !scope.equals("provided")) {
					groupId = groupId.replace(".", "/");
					String artifactFileName = groupId + "/" + artifact + "/" + version
							+ "/" + artifact + "-" + version + ".jar";

					jars.add(new File(homeDir, ".m2/repository/" + artifactFileName));
				}

			}
		}

		return jars;
	}

	private static boolean isIncluded(List<Artifact> includeOnlyArtifacts, String groupId,
			final String artifactId) {
		if (includeOnlyArtifacts != null) {
			for (Artifact artifact : includeOnlyArtifacts) {
				if (artifact.is(groupId, artifactId)) {
					return true;
				}
			}
			return false;
		}

		return true;
	}

	private static String stripWhitespace(String orig) {
		if (orig != null) {
			return orig.replace("\r", "").replace("\n", "").replace("\t", "").trim();
		}
		return orig;
	}

	private static String resolveProperty(String orig, Map<String, String> properties) {
		String property = properties.get(orig);
		if (property != null) {
			return property;
		}
		return orig;
	}

	private static class Artifact {
		private final String groupId;

		private final String artifact;

		public Artifact(String groupId, String artifact) {
			this.groupId = groupId;
			this.artifact = artifact;
		}

		public boolean is(String group, String arti) {
			return this.groupId.equals(group) && this.artifact.equals(arti);
		}
	}

}