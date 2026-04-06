package ch.rasc.github;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public class Clone {

	public static void main(String[] args)
			throws InvalidRemoteException, TransportException, GitAPIException {
		Git.cloneRepository().setBare(true)
				.setURI("git://github.com/SpringSource/spring-framework.git")
				.setDirectory(new File("springframework")).call();
	}

}
