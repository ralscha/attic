package ch.rasc.golb.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.POLL;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.conversions.Bson;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.rasc.golb.Application;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.config.security.MongoUserDetails;
import ch.rasc.golb.config.security.RequireAdminAuthority;
import ch.rasc.golb.dto.UserDetailDto;
import ch.rasc.golb.entity.CUser;
import ch.rasc.golb.entity.User;
import ch.rasc.golb.util.TotpAuthUtil;
import ch.rasc.golb.web.CsrfController;

@Service
public class SecurityService {
	public static final String AUTH_USER = "authUser";

	private final MongoDb mongoDb;

	private final PasswordEncoder passwordEncoder;

	private final MailService mailService;

	private final ApplicationEventPublisher applicationEventPublisher;

	public SecurityService(MongoDb mongoDb, PasswordEncoder passwordEncoder,
			MailService mailService,
			ApplicationEventPublisher applicationEventPublisher) {
		this.mongoDb = mongoDb;
		this.passwordEncoder = passwordEncoder;
		this.mailService = mailService;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@ExtDirectMethod
	public UserDetailDto getAuthUser(
			@AuthenticationPrincipal MongoUserDetails userDetails) {

		if (userDetails != null) {
			User user = userDetails.getUser(this.mongoDb);
			UserDetailDto userDetailDto = new UserDetailDto(userDetails, user, null);

			if (!userDetails.isPreAuth()) {
				this.mongoDb.getCollection(User.class).updateOne(
						Filters.eq(CUser.id, userDetails.getUserDbId()),
						Updates.set(CUser.lastAccess, new Date()));
			}

			return userDetailDto;
		}

		return null;
	}

	@ExtDirectMethod(ExtDirectMethodType.FORM_POST)
	@PreAuthorize("hasAuthority('PRE_AUTH')")
	public ExtDirectFormPostResult signin2fa(HttpServletRequest request,
			@AuthenticationPrincipal MongoUserDetails userDetails,
			@RequestParam("code") int code) {

		User user = userDetails.getUser(this.mongoDb);
		if (user != null) {
			if (TotpAuthUtil.verifyCode(user.getSecret(), code, 3)) {

				this.mongoDb.getCollection(User.class).updateOne(
						Filters.eq(CUser.id, userDetails.getUserDbId()),
						Updates.set(CUser.lastAccess, new Date()));

				userDetails.grantAuthorities();

				Authentication newAuth = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(newAuth);

				ExtDirectFormPostResult result = new ExtDirectFormPostResult();
				result.addResultProperty(AUTH_USER, new UserDetailDto(userDetails, user,
						CsrfController.getCsrfToken(request)));
				return result;
			}

			BadCredentialsException excp = new BadCredentialsException(
					"Bad verification code");
			AuthenticationFailureBadCredentialsEvent event = new AuthenticationFailureBadCredentialsEvent(
					SecurityContextHolder.getContext().getAuthentication(), excp);
			this.applicationEventPublisher.publishEvent(event);

			user = userDetails.getUser(this.mongoDb);
			if (user.getLockedOutUntil() != null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					Application.logger.debug("Invalidating session: " + session.getId());
					session.invalidate();
				}
				SecurityContext context = SecurityContextHolder.getContext();
				context.setAuthentication(null);
				SecurityContextHolder.clearContext();
			}
		}

		return new ExtDirectFormPostResult(false);
	}

	@ExtDirectMethod
	@RequireAdminAuthority
	public void enableScreenLock(@AuthenticationPrincipal MongoUserDetails userDetails) {
		userDetails.setScreenLocked(true);
	}

	@ExtDirectMethod(ExtDirectMethodType.FORM_POST)
	@RequireAdminAuthority
	public ExtDirectFormPostResult disableScreenLock(
			@AuthenticationPrincipal MongoUserDetails userDetails,
			@RequestParam("password") String password) {

		User user = this.mongoDb.getCollection(User.class)
				.find(Filters.eq(CUser.id, userDetails.getUserDbId()))
				.projection(Projections.include(CUser.passwordHash)).first();

		boolean matches = this.passwordEncoder.matches(password, user.getPasswordHash());
		userDetails.setScreenLocked(!matches);
		ExtDirectFormPostResult result = new ExtDirectFormPostResult(matches);

		return result;
	}

	@ExtDirectMethod(ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult resetRequest(
			@RequestParam("email") String emailOrLoginName) {

		String token = UUID.randomUUID().toString();

		User user = this.mongoDb.getCollection(User.class).findOneAndUpdate(
				Filters.and(
						Filters.or(Filters.eq(CUser.email, emailOrLoginName),
								Filters.eq(CUser.loginName, emailOrLoginName)),
						Filters.eq(CUser.deleted, false)),
				Updates.combine(
						Updates.set(CUser.passwordResetTokenValidUntil,
								Date.from(ZonedDateTime.now(ZoneOffset.UTC).plusHours(4)
										.toInstant())),
						Updates.set(CUser.passwordResetToken, token)),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
						.upsert(false));

		if (user != null) {
			this.mailService.sendPasswortResetEmail(user);
		}

		return new ExtDirectFormPostResult();
	}

	@ExtDirectMethod(ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult reset(@RequestParam("newPassword") String newPassword,
			@RequestParam("newPasswordRetype") String newPasswordRetype,
			@RequestParam("token") String token) {

		if (StringUtils.hasText(token) && StringUtils.hasText(newPassword)
				&& StringUtils.hasText(newPasswordRetype)
				&& newPassword.equals(newPasswordRetype)) {
			String decodedToken = new String(Base64.getUrlDecoder().decode(token));
			User user = this.mongoDb.getCollection(User.class)
					.find(Filters.and(Filters.eq(CUser.passwordResetToken, decodedToken),
							Filters.eq(CUser.deleted, false),
							Filters.eq(CUser.enabled, true)))
					.first();

			if (user != null && user.getPasswordResetTokenValidUntil() != null) {

				ExtDirectFormPostResult result;
				List<Bson> updates = new ArrayList<>();

				if (user.getPasswordResetTokenValidUntil().after(new Date())) {
					user.setPasswordHash(this.passwordEncoder.encode(newPassword));
					user.setSecret(null);
					updates.add(Updates.unset(CUser.secret));
					updates.add(Updates.set(CUser.passwordHash, user.getPasswordHash()));

					MongoUserDetails principal = new MongoUserDetails(user);
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							principal, null, principal.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);

					result = new ExtDirectFormPostResult();
					result.addResultProperty(AUTH_USER,
							new UserDetailDto(principal, user, null));
				}
				else {
					result = new ExtDirectFormPostResult(false);
				}
				user.setPasswordResetToken(null);
				user.setPasswordResetTokenValidUntil(null);
				updates.add(Updates.unset(CUser.passwordResetToken));
				updates.add(Updates.unset(CUser.passwordResetTokenValidUntil));

				this.mongoDb.getCollection(User.class).updateOne(
						Filters.eq(CUser.id, user.getId()), Updates.combine(updates));

				return result;
			}
		}

		return new ExtDirectFormPostResult(false);
	}

	@ExtDirectMethod
	@RequireAdminAuthority
	public UserDetailDto switchUser(String userId) {
		User switchToUser = this.mongoDb.getCollection(User.class)
				.find(Filters.eq(CUser.id, userId)).first();
		if (switchToUser != null) {

			MongoUserDetails principal = new MongoUserDetails(switchToUser);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					principal, null, principal.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(token);

			return new UserDetailDto(principal, switchToUser, null);
		}

		return null;
	}

	@ExtDirectMethod(value = POLL, event = "heartbeat")
	@RequireAdminAuthority
	public void heartbeat() {
		// nothing here
	}

}
