package ch.rasc.github;

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class RestTemplateMap {

	private static final String GITHUB_REPOS_URI = "https://api.github.com/users/{user}/repos";

	public static void main(String[] args) {

		RestTemplate restTemplate = new RestTemplate();

		List<Map<String, Object>> repos = restTemplate.getForObject(GITHUB_REPOS_URI,
				List.class, "ralscha");

		for (Map<String, Object> repo : repos) {
			System.out.println("Name: " + repo.get("name"));
			System.out.println("URL: " + repo.get("git_url"));
			System.out.println();
		}

	}

}
