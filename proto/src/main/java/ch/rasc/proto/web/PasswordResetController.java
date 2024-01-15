package ch.rasc.proto.web;

import java.io.IOException;
import java.time.LocalDateTime;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.config.security.AppUserDetails;
import ch.rasc.proto.entity.User;
import ch.rasc.proto.service.MailService;

@Controller
public class PasswordResetController {

	private final MailService mailService;

	private final PasswordEncoder passwordEncoder;

	private final DbManager dbManager;

	@Autowired
	public PasswordResetController(DbManager dbManager, MailService mailService,
			PasswordEncoder passwordEncoder) {
		this.dbManager = dbManager;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping(value = "passwordreset.action", method = RequestMethod.POST)
	public void passwordreset(HttpServletRequest request, HttpServletResponse response,
			String newPassword, String newPasswordRetype, String token)
			throws IOException {

		if (StringUtils.hasText(token) && StringUtils.hasText(newPassword)
				&& StringUtils.hasText(newPasswordRetype)
				&& newPassword.equals(newPasswordRetype)) {

			this.dbManager
					.runInTxWithoutResult(db -> {
						String decodedToken = new String(Base64.getUrlDecoder().decode(
								token));
						User user = DbManager
								.getAll(db, User.class)
								.stream()
								.filter(u -> !u.isDeleted())
								.filter(u -> u.getPasswordResetToken().equals(
										decodedToken)).findFirst().orElse(null);

						if (user != null
								&& user.getPasswordResetTokenValidUntil() != null) {
							if (user.getPasswordResetTokenValidUntil().isAfter(
									LocalDateTime.now())) {
								user.setPasswordHash(this.passwordEncoder
										.encode(newPassword));

								AppUserDetails principal = new AppUserDetails(user);
								UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
										principal, null, principal.getAuthorities());
								SecurityContextHolder.getContext().setAuthentication(
										authToken);
							}
							user.setPasswordResetToken(null);
							user.setPasswordResetTokenValidUntil(null);
							DbManager.put(db, user);
						}
					});

		}

		response.sendRedirect(request.getContextPath());
	}

	@RequestMapping(value = { "/passwordresetEmail" }, method = RequestMethod.POST)
	@ResponseBody
	public void passwordresetEmail(String loginNameOrEmail) {

		this.dbManager.runInTxWithoutResult(db -> {
			List<User> users = DbManager
					.getAll(db, User.class)
					.stream()
					.filter(u -> u.getLoginName().equals(loginNameOrEmail)
							|| u.getEmail().equals(loginNameOrEmail))
					.collect(Collectors.toList());

			if (users.size() > 1) {
				users = users.stream()
						.filter(u -> u.getLoginName().equals(loginNameOrEmail))
						.collect(Collectors.toList());
			}

			if (users.size() == 1) {
				User user = users.iterator().next();

				String token = UUID.randomUUID().toString();
				this.mailService.sendPasswortResetEmail(user, token);

				user.setPasswordResetTokenValidUntil(LocalDateTime.now().plusHours(4));
				user.setPasswordResetToken(token);
				DbManager.put(db, user);
			}

		});

	}

}
