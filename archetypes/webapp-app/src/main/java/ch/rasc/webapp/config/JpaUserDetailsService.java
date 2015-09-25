package ch.rasc.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ch.rasc.webapp.entity.User;
import ch.rasc.webapp.repository.UserRepository;

@Component
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public JpaUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = this.userRepository.findByUserName(username);
		if (user != null) {
			return new JpaUserDetails(user);
		}

		throw new UsernameNotFoundException(username);
	}

}
