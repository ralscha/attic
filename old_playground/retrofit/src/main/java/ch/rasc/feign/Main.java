package ch.rasc.feign;

import java.util.List;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;

public class Main {
	interface GitHub {
		@RequestLine("GET /repos/{owner}/{repo}/contributors")
		List<Contributor> contributors(@Param("owner") String owner,
				@Param("repo") String repo);
	}

	static class Contributor {
		String login;
		int contributions;

		public String getLogin() {
			return this.login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public int getContributions() {
			return this.contributions;
		}

		public void setContributions(int contributions) {
			this.contributions = contributions;
		}

	}

	public static void main(String... args) {
		GitHub github = Feign.builder().decoder(new JacksonDecoder()).target(GitHub.class,
				"https://api.github.com");

		// Fetch and print a list of the contributors to this library.
		List<Contributor> contributors = github.contributors("OpenFeign", "feign");
		for (Contributor contributor : contributors) {
			System.out
					.println(contributor.login + " (" + contributor.contributions + ")");
		}
	}
}
