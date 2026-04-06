package ch.rasc.github;

import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

public class Egit {

	public static void main(String[] args) throws IOException {
		RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories("ralscha")) {
			System.out.println("Name: " + repo.getName());
			System.out.println("URL: " + repo.getGitUrl());
			System.out.println();
		}

	}

}
