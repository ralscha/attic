#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import ${package}.entity.Role;
import ${package}.entity.User;
import ${package}.repository.RoleRepository;
import ${package}.repository.UserRepository;

@Service
public class InitialDataLoad {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public InitialDataLoad(UserRepository userRepository, RoleRepository roleRepository,
			PlatformTransactionManager transactionManager, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;

		new TransactionTemplate(transactionManager)
				.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						init();
					}
				});
	}

	void init() {
		Role adminRole = null;
		Role userRole = null;

		if (roleRepository.count() == 0) {
			adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);

			userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);
		}

		if (userRepository.count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setPasswordHash(passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setLocale("en_US");
			adminUser.setCreateDate(new Date());

			if (adminRole == null) {
				adminRole = roleRepository.findByName("ROLE_ADMIN");
			}

			if (adminRole != null) {
				adminUser.setRoles(Collections.singleton(adminRole));
			}

			userRepository.save(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");
			normalUser.setPasswordHash(passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			normalUser.setLocale("de_CH");
			normalUser.setCreateDate(new Date());

			if (userRole == null) {
				userRole = roleRepository.findByName("ROLE_USER");
			}

			if (userRole != null) {
				normalUser.setRoles(Collections.singleton(userRole));
			}

			userRepository.save(normalUser);
		}

	}

}
