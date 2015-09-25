package ch.rasc.webapp.config;

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.rasc.webapp.entity.Role;
import ch.rasc.webapp.entity.User;
import ch.rasc.webapp.repository.RoleRepository;
import ch.rasc.webapp.repository.UserRepository;

@Component
public class InitialDataLoad {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public InitialDataLoad(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		init();
	}

	private void init() {
		if (this.userRepository.count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setLocale("en");
			adminUser.setCreateDate(new Date());

			Role adminRole = this.roleRepository.findByName("ADMIN");
			adminUser.setRoles(Collections.singleton(adminRole));

			this.userRepository.save(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");

			normalUser.setPasswordHash(this.passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			normalUser.setLocale("de");
			normalUser.setCreateDate(new Date());

			Role userRole = this.roleRepository.findByName("USER");
			normalUser.setRoles(Collections.singleton(userRole));

			this.userRepository.save(normalUser);
		}
	}

}
