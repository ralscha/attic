package ch.rasc.golb.config;

import java.util.Arrays;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;

import ch.rasc.golb.entity.Authority;
import ch.rasc.golb.entity.User;

@Component
class Startup {

	private final MongoDb mongoDb;

	private final PasswordEncoder passwordEncoder;

	public Startup(MongoDb mongoDb, PasswordEncoder passwordEncoder) {
		this.mongoDb = mongoDb;
		this.passwordEncoder = passwordEncoder;
		init();
	}

	private void init() {

		MongoCollection<User> userCollection = this.mongoDb.getCollection(User.class);
		if (userCollection.count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setLoginName("admin");
			adminUser.setEmail("admin@golb.com");
			adminUser.setFirstName("admin");
			adminUser.setLastName("admin");
			adminUser.setLocale("en");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setDeleted(false);
			adminUser.setAuthorities(Arrays.asList(Authority.ADMIN.name()));
			userCollection.insertOne(adminUser);
		}

	}

}
