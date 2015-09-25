#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ${package}.entity.AccessLog;
import ${package}.entity.User;
import ${package}.repository.AccessLogRepository;
import ${package}.repository.UserRepository;
import ${package}.security.JpaUserDetails;

@Service
public class SecurityService {
	private final static UserAgentStringParser UAPARSER = UADetectorServiceFactory
			.getResourceModuleParser();

	private final UserRepository userRepository;

	private final AccessLogRepository accessLogRepository;

	@Autowired
	public SecurityService(UserRepository userRepository,
			AccessLogRepository accessLogRepository) {
		this.userRepository = userRepository;
		this.accessLogRepository = accessLogRepository;
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public User getLoggedOnUser(HttpServletRequest request, HttpSession session) {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {

			User user = userRepository
					.findOne(((JpaUserDetails) principal).getUserDbId());

			AccessLog accessLog = new AccessLog();
			accessLog.setUserName(user.getUserName());
			accessLog.setSessionId(session.getId());
			accessLog.setLogIn(LocalDateTime.now());
			String ua = request.getHeader("User-Agent");
			if (StringUtils.hasText(ua)) {
				accessLog.setUserAgent(ua);
				ReadableUserAgent agent = UAPARSER.parse(ua);
				accessLog.setUserAgentName(agent.getName());
				accessLog.setUserAgentVersion(agent.getVersionNumber().getMajor());
				accessLog.setOperatingSystem(agent.getOperatingSystem().getFamilyName());
			}

			accessLogRepository.save(accessLog);

			return user;

		}
		return null;
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public boolean switchUser(Long userId) {
		User switchToUser = userRepository.findOne(userId);
		if (switchToUser != null) {

			JpaUserDetails principal = new JpaUserDetails(switchToUser);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					principal, null, principal.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(token);

			return true;
		}

		return false;
	}

}
