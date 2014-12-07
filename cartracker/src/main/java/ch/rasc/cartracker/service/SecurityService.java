package ch.rasc.cartracker.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.cartracker.config.StaffUserDetails;

import com.google.common.collect.ImmutableMap;

@Service
public class SecurityService {

	@ExtDirectMethod
	public Map<String, Object> checkLogin() {

		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof StaffUserDetails) {
			StaffUserDetails userDetail = (StaffUserDetails) principal;

			ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
			builder.put("firstName", userDetail.getFirstName());
			builder.put("lastName", userDetail.getLastName());
			builder.put("username", userDetail.getUsername());

			Set<String> roles = new HashSet<>();
			for (GrantedAuthority ga : userDetail.getAuthorities()) {
				roles.add(ga.getAuthority());
			}
			builder.put("roles", roles);

			return builder.build();

		}
		return null;
	}

}
