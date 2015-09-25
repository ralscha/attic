package ch.rasc.springjpa;

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import ch.rasc.springjpa.entity.Role;
import ch.rasc.springjpa.entity.User;
import ch.rasc.springjpa.repository.RoleRepository;
import ch.rasc.springjpa.repository.UserRepository;

@Service
public class InitialDataLoad {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public InitialDataLoad(UserRepository userRepository, RoleRepository roleRepository,
			PlatformTransactionManager transactionManager,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;

		new TransactionTemplate(transactionManager)
				.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(
							TransactionStatus status) {
						init();
					}
				});
	}

	void init() {
		Role adminRole = null;
		Role userRole = null;

		if (this.roleRepository.count() == 0) {
			adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			this.roleRepository.save(adminRole);

			userRole = new Role();
			userRole.setName("ROLE_USER");
			this.roleRepository.save(userRole);
		}

		if (this.userRepository.count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setLocale("en_US");
			adminUser.setCreateDate(new Date());

			if (adminRole == null) {
				adminRole = this.roleRepository.findByName("ROLE_ADMIN");
			}

			if (adminRole != null) {
				adminUser.setRoles(Collections.singleton(adminRole));
			}

			this.userRepository.save(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");
			normalUser.setPasswordHash(this.passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			normalUser.setLocale("de_CH");
			normalUser.setCreateDate(new Date());

			if (userRole == null) {
				userRole = this.roleRepository.findByName("ROLE_USER");
			}

			if (userRole != null) {
				normalUser.setRoles(Collections.singleton(userRole));
			}

			this.userRepository.save(normalUser);
		}

	}

}
