package ch.rasc.e4ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ch.rasc.e4ds.domain.User;

@Component
public class MongoDBUserDetailsService implements UserDetailsService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(username)), User.class);
		if (user != null) {
			return new MongoDBUserDetails(user);
		}

		throw new UsernameNotFoundException(username);
	}

}
