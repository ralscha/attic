package ch.rasc.eds.starter.web;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.rasc.eds.starter.config.security.JpaUserDetails;
import ch.rasc.eds.starter.entity.QUser;
import ch.rasc.eds.starter.entity.User;
import ch.rasc.eds.starter.service.MailService;
import ch.rasc.edsutil.JPAQueryFactory;

@Controller
public class PasswordResetController {

	private final MailService mailService;

	private final PasswordEncoder passwordEncoder;

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public PasswordResetController(JPAQueryFactory jpaQueryFactory,
			MailService mailService, PasswordEncoder passwordEncoder) {
		this.jpaQueryFactory = jpaQueryFactory;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping(value = "passwordreset.action", method = RequestMethod.POST)
	@Transactional
	public void passwordreset(HttpServletRequest request, HttpServletResponse response,
			String newPassword, String newPasswordRetype, String token)
					throws IOException {

		if (StringUtils.hasText(token) && StringUtils.hasText(newPassword)
				&& StringUtils.hasText(newPasswordRetype)
				&& newPassword.equals(newPasswordRetype)) {
			String decodedToken = new String(Base64.getUrlDecoder().decode(token));
			User user = this.jpaQueryFactory.selectFrom(QUser.user)
					.where(QUser.user.passwordResetToken.eq(decodedToken),
							QUser.user.deleted.isFalse())
					.fetchFirst();
			if (user != null && user.getPasswordResetTokenValidUntil() != null) {
				if (user.getPasswordResetTokenValidUntil()
						.isAfter(ZonedDateTime.now(ZoneOffset.UTC))) {
					user.setPasswordHash(this.passwordEncoder.encode(newPassword));

					JpaUserDetails principal = new JpaUserDetails(user);
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							principal, null, principal.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				user.setPasswordResetToken(null);
				user.setPasswordResetTokenValidUntil(null);
				this.jpaQueryFactory.getEntityManager().merge(user);
			}
		}

		response.sendRedirect(request.getContextPath());
	}

	@RequestMapping(value = { "/passwordresetEmail" }, method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public void passwordresetEmail(String loginNameOrEmail) {
		List<User> users = this.jpaQueryFactory.selectFrom(QUser.user)
				.where(QUser.user.loginName.eq(loginNameOrEmail)
						.or(QUser.user.email.eq(loginNameOrEmail))
						.and(QUser.user.deleted.isFalse()))
				.fetch();

		if (users.size() > 1) {
			users = users.stream().filter(u -> u.getLoginName().equals(loginNameOrEmail))
					.collect(Collectors.toList());
		}

		if (users.size() == 1) {
			User user = users.iterator().next();

			String token = UUID.randomUUID().toString();
			this.mailService.sendPasswortResetEmail(user, token);

			user.setPasswordResetTokenValidUntil(
					ZonedDateTime.now(ZoneOffset.UTC).plusHours(4));
			user.setPasswordResetToken(token);
		}

	}

}
