package ch.rasc.github;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;

public class Backup {

	public static void main(String... args) throws Exception {

		if (args.length != 2) {
			System.out.println("Backup <github_user> <backup_directory>");
			return;
		}

		Path backupDir = Paths.get(args[1]);
		Files.createDirectories(backupDir);

		RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories(args[0])) {

			Path repoDir = backupDir.resolve(repo.getName());
			Files.createDirectories(repoDir);

			Path configFile = repoDir.resolve("config");
			if (Files.exists(configFile)) {
				System.out.println("fetching : " + repo.getName());
				Git.open(repoDir.toFile()).fetch().call();
			}
			else {
				System.out.println("cloning : " + repo.getName());
				Git.cloneRepository().setBare(true).setURI(repo.getGitUrl())
						.setDirectory(repoDir.toFile()).call();
			}

		}

	}

}
