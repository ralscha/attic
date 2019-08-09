package ch.rasc.golb.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.Filters;

import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.entity.CUser;
import ch.rasc.golb.entity.User;

@Component
public class MongoUserDetailsService implements UserDetailsService {

	private final MongoDb mongoDb;

	public MongoUserDetailsService(MongoDb mongoDb) {
		this.mongoDb = mongoDb;
	}

	@Override
	public UserDetails loadUserByUsername(String loginName)
			throws UsernameNotFoundException {
		User user = this.mongoDb.getCollection(User.class)
				.find(Filters.and(Filters.eq(CUser.loginName, loginName),
						Filters.eq(CUser.deleted, false)))
				.first();
		if (user != null) {
			return new MongoUserDetails(user);
		}

		throw new UsernameNotFoundException(loginName);
	}

}
