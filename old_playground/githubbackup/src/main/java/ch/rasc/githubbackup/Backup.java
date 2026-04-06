package ch.rasc.githubbackup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.githubbackup.Config.GitUrl;
import ch.rasc.githubbackup.Config.GogsUrl;

public class Backup {

	public static void main(String... args) throws Exception {

		if (args.length != 1) {
			System.out.println("java -jar githubbackup.jar <settings.yaml>");
			return;
		}

		Path settingsYamlPath = Paths.get(args[0]);
		try (BufferedReader br = Files.newBufferedReader(settingsYamlPath,
				StandardCharsets.UTF_8)) {
			Config config = new Yaml().loadAs(br, Config.class);

			if (config.key != null) {
				CustomJschConfigSessionFactory jschConfigSessionFactory = new CustomJschConfigSessionFactory(
						config.key, config.knownHosts);
				SshSessionFactory.setInstance(jschConfigSessionFactory);
			}

			Path backupDir = Paths.get(config.backupDirectory);
			Files.createDirectories(backupDir);

			RepositoryService service = new RepositoryService();
			if (config.githubUsers != null) {
				for (String githubUser : config.githubUsers) {
					for (Repository repo : service.getRepositories(githubUser)) {
						fetchRepo(backupDir, repo.getName(), repo.getCloneUrl(), null,
								null);
					}
				}
			}

			if (config.gitUrls != null) {
				for (GitUrl gitUrl : config.gitUrls) {
					fetchRepo(backupDir, gitUrl.name, gitUrl.url, gitUrl.username,
							gitUrl.password);
				}
			}

			if (config.gogsUrls != null) {
				ObjectMapper mapper = new ObjectMapper();
				try (CloseableHttpClient client = HttpClients.custom().build()) {

					for (GogsUrl gogsUrl : config.gogsUrls) {

						HttpUriRequest request = RequestBuilder.get()
								.setUri(gogsUrl.url + "/api/v1/user/repos")
								.setHeader(HttpHeaders.AUTHORIZATION,
										"token " + gogsUrl.token)
								.build();

						try (CloseableHttpResponse response = client.execute(request);
								InputStream is = response.getEntity().getContent()) {
							JsonNode root = mapper.readTree(is);
							Iterator<JsonNode> it = root.iterator();
							while (it.hasNext()) {
								JsonNode repo = it.next();
								String cloneUrl = repo.get("clone_url").asText();
								String name = repo.get("full_name").asText();

								fetchRepo(backupDir, gogsUrl.repositoryName + "/" + name,
										cloneUrl, gogsUrl.username, gogsUrl.password);
							}
						}
					}
				}
			}
		}

	}

	private static void fetchRepo(Path backupDir, String name, String url,
			String username, String password) throws IOException, GitAPIException,
			InvalidRemoteException, TransportException {
		Path repoDir = backupDir.resolve(name);
		Files.createDirectories(repoDir);

		Path configFile = repoDir.resolve("config");
		if (Files.exists(configFile)) {
			System.out.println("fetching : " + name);
			if (username != null) {
				UsernamePasswordCredentialsProvider cp = new UsernamePasswordCredentialsProvider(
						username, password);
				Git.open(repoDir.toFile()).fetch().setCredentialsProvider(cp).call();
			}
			else {
				Git.open(repoDir.toFile()).fetch().call();
			}

			Git.open(repoDir.toFile()).gc().call();
		}
		else {
			System.out.println("cloning : " + name);
			if (username != null) {
				UsernamePasswordCredentialsProvider cp = new UsernamePasswordCredentialsProvider(
						username, password);
				Git.cloneRepository().setCredentialsProvider(cp).setBare(true).setURI(url)
						.setDirectory(repoDir.toFile()).call();
			}
			else {
				Git.cloneRepository().setBare(true).setURI(url)
						.setDirectory(repoDir.toFile()).call();
			}

		}
	}

}
