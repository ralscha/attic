package ch.rasc.sqrl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.rasc.sqrl.auth.AuthStore;
import ch.rasc.sqrl.auth.MapAuthStore;
import ch.rasc.sqrl.hoard.Hoard;
import ch.rasc.sqrl.hoard.MapHoard;
import ch.rasc.sqrl.tree.RandomTree;
import ch.rasc.sqrl.tree.Tree;

@Configuration
public class Config {

	@Bean
	public Tree tree() {
		return new RandomTree(8);
	}

	@Bean
	public Hoard hoard() {
		return new MapHoard();
	}

	@Bean
	public AuthStore authStore() {
		return new MapAuthStore();
	}

}
