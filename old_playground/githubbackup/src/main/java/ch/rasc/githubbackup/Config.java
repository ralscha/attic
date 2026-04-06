package ch.rasc.githubbackup;

import java.util.List;

public class Config {
	public String backupDirectory;

	public List<String> githubUsers;

	public List<GitUrl> gitUrls;

	public List<GogsUrl> gogsUrls;

	public String key;

	public String knownHosts;

	public final static class GogsUrl {
		public String repositoryName;
		public String url;
		public String token;
		public String username;
		public String password;
	}

	public final static class GitUrl {
		public String name;
		public String url;
		public String username;
		public String password;
	}
}
