package ch.rasc.travellog.controller;

import static ch.rasc.travellog.db.tables.AppSession.APP_SESSION;
import static ch.rasc.travellog.db.tables.AppUser.APP_USER;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.passpol.PasswordPolicy;
import com.codahale.passpol.Status;

import ch.rasc.travellog.config.AppProperties;
import ch.rasc.travellog.config.security.AppUserDetail;
import ch.rasc.travellog.config.security.AuthHeaderFilter;
import ch.rasc.travellog.config.security.SessionCacheInvalidateEvent;
import ch.rasc.travellog.dto.SessionInfo;
import ch.rasc.travellog.service.EmailService;
import ch.rasc.travellog.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@RestController
@Validated
@RequestMapping("/be")
public class ProfileController {

	private final DSLContext dsl;

	private final PasswordEncoder passwordEncoder;

	private final TokenService tokenService;

	private final EmailService emailService;

	private final AppProperties appProperties;

	private final PasswordPolicy passwordPolicy;

	private final BuildProperties buildProperties;

	private final ApplicationEventPublisher publisher;

	public ProfileController(DSLContext dsl, PasswordEncoder passwordEncoder,
			TokenService tokenService, EmailService emailService,
			AppProperties appProperties, PasswordPolicy passwordPolicy,
			BuildProperties buildProperties, ApplicationEventPublisher publisher) {
		this.dsl = dsl;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
		this.emailService = emailService;
		this.appProperties = appProperties;
		this.passwordPolicy = passwordPolicy;
		this.buildProperties = buildProperties;
		this.publisher = publisher;
	}

	enum ChangePasswordResponse {
		INVALID, WEAK_PASSWORD
	}

	@PostMapping("/change-password")
	public ChangePasswordResponse changePassword(
			@AuthenticationPrincipal AppUserDetail user,
			@RequestParam("oldPassword") @NotEmpty String oldPassword,
			@RequestParam("newPassword") @NotEmpty String newPassword) {

		Status status = this.passwordPolicy.check(newPassword);
		if (status != Status.OK) {
			return ChangePasswordResponse.WEAK_PASSWORD;
		}

		return this.dsl.transactionResult(txConf -> {
			var txdsl = DSL.using(txConf);
			if (passwordMatches(txdsl, user.getAppUserId(), oldPassword)) {
				String encodedNewPassword = this.passwordEncoder.encode(newPassword);

				txdsl.update(APP_USER).set(APP_USER.PASSWORD_HASH, encodedNewPassword)
						.where(APP_USER.ID.eq(user.getAppUserId())).execute();

				this.emailService.sendPasswordChangedEmail(user.getEmail());

				this.dsl.delete(APP_SESSION)
						.where(APP_SESSION.APP_USER_ID.eq(user.getAppUserId())).execute();

				return null;
			}

			return ChangePasswordResponse.INVALID;

		});
	}

	@PostMapping("/delete-account")
	public boolean deleteAccount(@AuthenticationPrincipal AppUserDetail user,
			@RequestBody @NotEmpty String password) {

		return this.dsl.transactionResult(txConf -> {
			var txdsl = DSL.using(txConf);
			if (passwordMatches(txdsl, user.getAppUserId(), password)) {

				// delete all sessions
				Set<String> sessionIds = txdsl.select(APP_SESSION.ID).from(APP_SESSION)
						.where(APP_SESSION.APP_USER_ID.eq(user.getAppUserId()))
						.fetchSet(APP_SESSION.ID);
				if (!sessionIds.isEmpty()) {
					this.publisher.publishEvent(
							SessionCacheInvalidateEvent.ofSessionIds(sessionIds));
				}

				txdsl.delete(APP_USER).where(APP_USER.ID.eq(user.getAppUserId()))
						.execute();
				this.publisher.publishEvent(
						SessionCacheInvalidateEvent.ofUserId(user.getAppUserId()));

				return true;
			}

			return false;

		});
	}

	enum ChangeEmailResponse {
		SAME, // No change
		USE, // Email in use by another user
		PASSWORD // Password wrong
	}

	@PostMapping("/change-email")
	public ChangeEmailResponse changeEmail(@AuthenticationPrincipal AppUserDetail user,
			@RequestParam("password") @NotEmpty String password,
			@RequestParam("newEmail") @NotEmpty @Email String newEmail) {

		return this.dsl.transactionResult(txConf -> {
			var txdsl = DSL.using(txConf);

			// is new email same as old email
			int count = this.dsl.selectCount().from(APP_USER)
					.where(APP_USER.EMAIL.equalIgnoreCase(newEmail)
							.and(APP_USER.ID.eq(user.getAppUserId())))
					.fetchOne(0, int.class);
			if (count > 0) {
				return ChangeEmailResponse.SAME;
			}

			// is new email already used by another user
			count = this.dsl.selectCount().from(APP_USER)
					.where(APP_USER.EMAIL.equalIgnoreCase(newEmail))
					.fetchOne(0, int.class);
			if (count > 0) {
				return ChangeEmailResponse.USE;
			}

			if (passwordMatches(txdsl, user.getAppUserId(), password)) {

				String confirmationToken = this.tokenService.createToken();
				txdsl.update(APP_USER)
						.set(APP_USER.CONFIRMATION_TOKEN_CREATED, LocalDateTime.now())
						.set(APP_USER.CONFIRMATION_TOKEN, confirmationToken)
						.set(APP_USER.EMAIL_NEW, newEmail)
						.where(APP_USER.ID.eq(user.getAppUserId())).execute();

				this.emailService.sendEmailChangeConfirmationEmail(newEmail,
						confirmationToken);

				return null;
			}

			return ChangeEmailResponse.PASSWORD;

		});
	}

	@PostMapping("/confirm-email-change")
	public boolean confirmEmailChange(@RequestBody @NotEmpty String token) {

		var record = this.dsl.select(APP_USER.ID, APP_USER.CONFIRMATION_TOKEN_CREATED)
				.from(APP_USER).where(APP_USER.CONFIRMATION_TOKEN.equal(token))
				.fetchOne();

		if (record != null) {
			long userId = record.get(APP_USER.ID);
			LocalDateTime tokenCreated = record.get(APP_USER.CONFIRMATION_TOKEN_CREATED);

			if (tokenCreated != null && tokenCreated.isAfter(LocalDateTime.now()
					.minus(this.appProperties.getSignupNotConfirmedUserMaxAge()))) {

				this.dsl.update(APP_USER).setNull(APP_USER.CONFIRMATION_TOKEN)
						.setNull(APP_USER.CONFIRMATION_TOKEN_CREATED)
						.set(APP_USER.EMAIL, APP_USER.EMAIL_NEW)
						.setNull(APP_USER.EMAIL_NEW).where(APP_USER.ID.equal(userId))
						.execute();
				this.publisher.publishEvent(SessionCacheInvalidateEvent.ofUserId(userId));
				return true;
			}

			this.dsl.update(APP_USER).setNull(APP_USER.CONFIRMATION_TOKEN)
					.setNull(APP_USER.CONFIRMATION_TOKEN_CREATED)
					.setNull(APP_USER.EMAIL_NEW).where(APP_USER.ID.equal(userId))
					.execute();
		}

		return false;

	}

	@GetMapping("/sessions")
	public List<SessionInfo> sessions(@AuthenticationPrincipal AppUserDetail user,
			HttpServletRequest request) {

		String sessionId = request.getHeader(AuthHeaderFilter.HEADER_NAME);

		return this.dsl.selectFrom(APP_SESSION)
				.where(APP_SESSION.APP_USER_ID.eq(user.getAppUserId())).fetch().stream()
				.map(record -> new SessionInfo(record.getId(),
						record.getId().equals(sessionId), record.getLastAccess(),
						record.getIp(), record.getUserAgent()))
				.collect(Collectors.toList());
	}

	@PostMapping("/delete-session")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSession(@RequestBody @NotEmpty String sessionId,
			@AuthenticationPrincipal AppUserDetail user) {
		int count = this.dsl.delete(APP_SESSION).where(APP_SESSION.ID.eq(sessionId)
				.and(APP_SESSION.APP_USER_ID.eq(user.getAppUserId()))).execute();
		if (count == 1) {
			this.publisher
					.publishEvent(SessionCacheInvalidateEvent.ofSessionId(sessionId));
		}
	}

	private boolean passwordMatches(DSLContext d, Long appUserId, String password) {
		String passwordFromDb = d.select(APP_USER.PASSWORD_HASH).from(APP_USER)
				.where(APP_USER.ID.eq(appUserId)).fetchOne().get(APP_USER.PASSWORD_HASH);

		return this.passwordEncoder.matches(password, passwordFromDb);
	}

	@GetMapping("/build-info")
	public BuildProperties buildInfo() {
		return this.buildProperties;
	}

}
