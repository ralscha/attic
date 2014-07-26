package ch.rasc.e4ds.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.e4ds.domain.AccessLog;

@Service
public class SecurityService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public String getLoggedOnUser(HttpSession session) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof MongoDBUserDetails) {
			MongoDBUserDetails userDetail = (MongoDBUserDetails) principal;

			AccessLog accessLog = new AccessLog(userDetail.getUsername(), session.getId());
			mongoTemplate.save(accessLog);

			return userDetail.getFullName();
		}
		return principal.toString();
	}

}
