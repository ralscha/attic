package ch.rasc.proto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.rasc.proto.entity.Role;
import ch.rasc.proto.entity.User;

@Component
class Startup {

	private final DbManager dbManager;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public Startup(DbManager dbManager, PasswordEncoder passwordEncoder) {
		this.dbManager = dbManager;
		this.passwordEncoder = passwordEncoder;
		init();
	}

	private void init() {
		this.dbManager.runInTxWithoutResult(db -> {

			if (DbManager.count(db, User.class) == 0) {
				// admin user
				User adminUser = new User();
				adminUser.setLoginName("admin@proto.com");
				adminUser.setEmail("admin@proto.com");
				adminUser.setFirstName("admin");
				adminUser.setLastName("admin");
				adminUser.setLocale("en");
				adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
				adminUser.setEnabled(true);
				adminUser.setDeleted(false);
				adminUser.setRole(Role.ADMIN.name());
				DbManager.put(db, adminUser);

				// normal user
				User normalUser = new User();
				normalUser.setLoginName("user@proto.com");
				normalUser.setEmail("user@proto.com");
				normalUser.setFirstName("user");
				normalUser.setLastName("user");
				normalUser.setLocale("de");
				normalUser.setPasswordHash(this.passwordEncoder.encode("user"));
				normalUser.setEnabled(true);
				adminUser.setDeleted(false);
				normalUser.setRole(Role.USER.name());
				DbManager.put(db, normalUser);
				db.commit();
			}

		});

	}

}
