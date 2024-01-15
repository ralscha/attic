package ch.rasc.eds.starter.service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.Expressions;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.eds.starter.config.security.JpaUserDetails;
import ch.rasc.eds.starter.entity.AccessLog;
import ch.rasc.eds.starter.entity.QAccessLog;
import ch.rasc.eds.starter.entity.User;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.wampspring.EventMessenger;
import eu.bitwalker.useragentutils.UserAgent;

@Service
public class SecurityService {
	private final JPAQueryFactory jpaQueryFactory;

	private final GeoIPCityService geoIpCityService;

	private final EventMessenger eventMessenger;

	@Autowired
	public SecurityService(JPAQueryFactory jpaQueryFactory,
			GeoIPCityService geoIpCityService, EventMessenger eventMessenger) {
		this.jpaQueryFactory = jpaQueryFactory;
		this.geoIpCityService = geoIpCityService;
		this.eventMessenger = eventMessenger;
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public User getLoggedOnUser(HttpServletRequest request, HttpSession session,
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails) {

		if (jpaUserDetails != null) {
			User user = jpaUserDetails.getUser(this.jpaQueryFactory);
			if (jpaUserDetails.hasRole("ADMIN")) {
				user.setAutoOpenView("Starter.view.loggedin.Grid");
			}
			else if (jpaUserDetails.hasRole("USER")) {
				user.setAutoOpenView("Starter.view.dummy.View");
			}

			insertAccessLog(request, session, user);

			return user;
		}

		return null;
	}

	private void insertAccessLog(HttpServletRequest request, HttpSession session,
			User user) {
		if (this.jpaQueryFactory.select(Expressions.ONE).from(QAccessLog.accessLog)
				.where(QAccessLog.accessLog.sessionId.eq(session.getId()))
				.fetchFirst() == null) {

			AccessLog accessLog = new AccessLog();
			accessLog.setLoginName(user.getLoginName());
			accessLog.setSessionId(session.getId());
			accessLog.setLoginTimestamp(ZonedDateTime.now(ZoneOffset.UTC));

			String ipAddress = request.getRemoteAddr();
			accessLog.setIpAddress(ipAddress);
			accessLog.setLocation(this.geoIpCityService.lookupCity(ipAddress));

			String ua = request.getHeader("User-Agent");
			if (StringUtils.hasText(ua)) {
				accessLog.setUserAgent(ua);
				UserAgent agent = UserAgent.parseUserAgentString(ua);
				accessLog.setUserAgentName(agent.getBrowser().getGroup().getName());
				accessLog
						.setUserAgentVersion(agent.getBrowserVersion().getMajorVersion());
				accessLog.setOperatingSystem(agent.getOperatingSystem().getName());
			}

			this.jpaQueryFactory.getEntityManager().persist(accessLog);

			AccessLogService.CURRENT_LOGGED_IN_USERS.put(accessLog.getId(), accessLog);
			this.eventMessenger.sendToAll("/queue/login", accessLog);
		}
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public boolean switchUser(Long userId) {
		User switchToUser = this.jpaQueryFactory.getEntityManager().find(User.class,
				userId);
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
