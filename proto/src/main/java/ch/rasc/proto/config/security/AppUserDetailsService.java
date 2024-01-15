package ch.rasc.proto.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.entity.User;

@Component
public class AppUserDetailsService implements UserDetailsService {

	private final DbManager dbManager;

	@Autowired
	public AppUserDetailsService(DbManager dbManager) {
		this.dbManager = dbManager;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Optional<User> user = this.dbManager.runInTx(db -> {
			return DbManager.getAll(db, User.class).stream()
					.filter(u -> username.equals(u.getLoginName()))
					.filter(u -> !u.isDeleted()).findFirst();
		});

		if (user.isPresent()) {
			return new AppUserDetails(user.get());
		}

		throw new UsernameNotFoundException(username);

	}

}
