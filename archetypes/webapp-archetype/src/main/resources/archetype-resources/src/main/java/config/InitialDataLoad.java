#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ${package}.entity.Role;
import ${package}.entity.User;
import ${package}.repository.RoleRepository;
import ${package}.repository.UserRepository;

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
		if (userRepository.count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setPasswordHash(passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setLocale("en");
			adminUser.setCreateDate(new Date());

			Role adminRole = roleRepository.findByName("ADMIN");
			adminUser.setRoles(Collections.singleton(adminRole));

			userRepository.save(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");

			normalUser.setPasswordHash(passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			normalUser.setLocale("de");
			normalUser.setCreateDate(new Date());

			Role userRole = roleRepository.findByName("USER");
			normalUser.setRoles(Collections.singleton(userRole));

			userRepository.save(normalUser);
		}
	}

}
